package pages;

import models.CertificationManagementItem;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CertificationManagementPage extends BasePage {

    public CertificationManagementPage(WebDriver driver) {
        super(driver);
    }

    /* ================= NAVIGATION ================= */

    public void goToSettingPage() {
        clickMenu("Cài đặt");
    }

    public void goToCertificationManagementPage() {
        goToSettingPage();
        clickMenu("Quản lý chứng chỉ");
    }
    /* ================= LOCATORS ================= */

    private final By addButtonLocator = By.xpath("//button[contains(.,'Thêm')]");
    private final By certificationNameInput = By.xpath("//input[@placeholder='Nhập tên chứng chỉ']");
    private final By applyCourseDropdown = By.xpath("//button[contains(@class,'w-full') and contains(@class,'border') and contains(@class,'rounded-md')]");
    private final By statusDropdown = By.xpath("//span[contains(text(),'Chọn trạng thái')]");
    private final By saveButton = By.xpath("//button[contains(text(),'Lưu')]");
    private final By formContainer = By.xpath("(//h2[contains(text(),'Thêm chứng chỉ')])[1]");
    private final By tableRows = By.xpath("//tbody/tr");

    /* ================= ADD ================= */

    public void addCertification(String name, String course) {

        wait.until(ExpectedConditions.elementToBeClickable(addButtonLocator)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(formContainer));

        WebElement nameInput = wait.until(
                ExpectedConditions.visibilityOfElementLocated(certificationNameInput)
        );
        nameInput.clear();
        nameInput.sendKeys(name);

        wait.until(ExpectedConditions.elementToBeClickable(applyCourseDropdown)).click();

        By courseOption = By.xpath(String.format("//div[normalize-space()='%s']", course));
        wait.until(ExpectedConditions.elementToBeClickable(courseOption)).click();

        driver.findElement(formContainer).click();

        wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();

        // 🔥 FIX BẮT BUỘC

        // 1. đợi form đóng (quan trọng nhất)
        wait.until(ExpectedConditions.invisibilityOfElementLocated(formContainer));

        // 2. đợi table load lại
        waitForTableLoaded();
    }

    /* ================= TABLE ================= */

    private static final List<String> COLUMN_NAMES = List.of(
            "STT", "TÊN CHỨNG CHỈ", "KHÓA ÁP DỤNG", "TRẠNG THÁI"
    );

    private int getColumnIndex(String name) {
        return COLUMN_NAMES.indexOf(name) + 1;
    }

    private WebElement getCell(int row, int col) {
        return driver.findElement(By.xpath("//tbody/tr[" + row + "]/td[" + col + "]"));
    }

    public int getTotalRows() {
        return driver.findElements(tableRows).size();
    }

    public int findRowByCertificationName(String certificationName) {

        for (int i = 1; i <= getTotalRows(); i++) {

            String name = getCell(i, getColumnIndex("TÊN CHỨNG CHỈ"))
                    .getText().trim();

            if (name.equalsIgnoreCase(certificationName.trim())) {
                return i;
            }
        }

        throw new RuntimeException("Không tìm thấy chứng chỉ: " + certificationName);
    }

    /* ================= GET ALL DATA ================= */

    public ArrayList<CertificationManagementItem> getAllCertifications() {

        ArrayList<CertificationManagementItem> list = new ArrayList<>();

        int rows = getTotalRows();

        for (int i = 1; i <= rows; i++) {

            String name = getCell(i, getColumnIndex("TÊN CHỨNG CHỈ"))
                    .getText().trim();

            String course = getCell(i, getColumnIndex("KHÓA ÁP DỤNG"))
                    .getText().trim();

            String status = getCell(i, getColumnIndex("TRẠNG THÁI"))
                    .getText().trim();

            list.add(new CertificationManagementItem(
                    name,
                    course
            ));
        }

        return list;
    }

    /* ================= EDIT ================= */

    public void editCertificationName(String oldName, String newName) {

        int row = findRowByCertificationName(oldName);

        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//table//button[@data-slot='dropdown-menu-trigger']")
        )).click();

        By editBtn = By.xpath("//div[normalize-space()='Sửa']");
        wait.until(ExpectedConditions.elementToBeClickable(editBtn)).click();

        WebElement input = wait.until(
                ExpectedConditions.visibilityOfElementLocated(certificationNameInput)
        );
        input.clear();
        input.sendKeys(newName);
            wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();

    }

    public void editCertificationCourse(String name, String newCourse) {

        int row = findRowByCertificationName(name);

       driver.findElement(By.xpath("//table//button[@data-slot='dropdown-menu-trigger']")).click();

        By editBtn = By.xpath("//div[normalize-space()='Sửa']");
        wait.until(ExpectedConditions.elementToBeClickable(editBtn)).click();

        wait.until(ExpectedConditions.elementToBeClickable(applyCourseDropdown)).click();

        By newCourseOption = By.xpath("//div[normalize-space()='" + newCourse + "']");
        wait.until(ExpectedConditions.elementToBeClickable(newCourseOption)).click();

        wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
    }


    // Đợi số row tăng (dùng cho add)
    public void waitForRowIncrease(int oldCount) {
        wait.until(driver -> getTotalRows() > oldCount);
    }

    // Đợi certification biến mất
    public void waitForCertificationDisappear(String name) {
        By cell = By.xpath("//tbody//td[normalize-space()='" + name + "']");
        wait.until(ExpectedConditions.invisibilityOfElementLocated(cell));
    }

    // Đợi text xuất hiện trong table
    public void waitForTextInTable(String text) {
        By table = By.xpath("//tbody");
        wait.until(ExpectedConditions.textToBePresentInElementLocated(table, text));
    }

    // ================= WAIT =================

    public void waitForTableLoaded() {

        By tableBody = By.xpath("//tbody");

        // 1. Table xuất hiện
        wait.until(ExpectedConditions.presenceOfElementLocated(tableBody));

        // 2. Table visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(tableBody));

        // 3. Đợi DOM ổn định (KHÔNG ép có row)
        wait.until(driver -> {
            int size1 = driver.findElements(By.xpath("//tbody/tr")).size();
            try { Thread.sleep(500); } catch (Exception ignored) {}
            int size2 = driver.findElements(By.xpath("//tbody/tr")).size();
            return size1 == size2;
        });
    }
    public void waitForCertificationAppear(String name) {

        By cell = By.xpath("//tbody//td[contains(normalize-space(),'" + name + "')]");

        wait.until(driver -> driver.findElements(cell).size() > 0);
    }
    // search
    public void searchCertification(String keyword) {

        By searchInput = By.xpath("//input[@placeholder='Nhập tên chứng chỉ...']");
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(searchInput));

        input.clear();
        input.sendKeys(keyword);
    }
    public CertificationManagementItem getRandomCertification() {

        waitForTableLoaded();

        ArrayList<CertificationManagementItem> list = getAllCertifications();

        return list.get(new Random().nextInt(list.size()));
    }
    public boolean verifySearchResultContainsName(String name) {

        ArrayList<CertificationManagementItem> list = getAllCertifications();

        return list.stream().anyMatch(item ->
                item.getCertificationName().equalsIgnoreCase(name.trim())
        );
    }
    public boolean isCertificationTableEmpty() {

        List<WebElement> rows = driver.findElements(By.xpath("//tbody/tr"));

        return rows.isEmpty();
    }
    public String generateNonExistingKeyword() {
        return "AUTO_NOT_EXIST_" + System.currentTimeMillis();
    }

}