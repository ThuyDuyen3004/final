package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MajorPage extends BasePage{
    public MajorPage(WebDriver driver) {
        super(driver);
    }

    /* ================= NAVIGATION ================= */

    public void goToSettingPage() {
        clickMenu("Cài đặt");
    }

    public void goToUserManagePage() {
        goToSettingPage();
        clickMenu("Quản lý chuyên ngành");
    }

    /* ================= LOCATORS ================= */

    private final By addButton = By.xpath("//button[contains(text(),'Thêm')]");
    private final By majorID = By.id("code");
    private final By majorName = By.id("name");
    private final By applyCourseDropdown = By.xpath("//div[@class='relative w-full']");
    private final By saveButton = By.xpath("//button[contains(text(),'Lưu')]");
    private static final String OPTION_XPATH =
            "//div[@role='option' and normalize-space(.)='%s']";
    private final By tableRows = By.xpath("//tbody/tr");
    private final By searchInput = By.xpath("//input[@data-slot='input']");
    private final By deleteButton = By.xpath("//div[.='Xóa']");
    private final By confirmYesButton = By.xpath("//div[.='Xóa']");
    private static final List<String> COLUMN_NAMES = List.of(
            "STT",
            "MÃ CHUYÊN NGÀNH",
            "TÊN CHUYÊN NGÀNH",
            "KHÓA ÁP DỤNG"
    );

    /* ================= ADD CLASS ================= */

    public void openAddMajorForm() {
        wait.until(ExpectedConditions.elementToBeClickable(addButton)).click();
    }

    private void selectApplyCourse() {

        wait.until(ExpectedConditions.elementToBeClickable(applyCourseDropdown))
                .click();

        WebElement checkbox48K = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//label[.//text()[contains(.,'48K')]]//input[@type='checkbox']")
                )
        );
        checkbox48K.click();

        wait.until(ExpectedConditions.elementToBeClickable(By.tagName("body"))).click();
    }

    private void enterMajorID(String id) {
        driver.findElement(majorID).sendKeys(id);
    }

    private void enterMajorName(String name) {
        driver.findElement(majorName).sendKeys(name);
    }

    public void addMajor(String id, String name) {
        openAddMajorForm();
        enterMajorID(id);
        enterMajorName(name);
        selectApplyCourse();
        driver.findElement(saveButton).click();
    }
    /* ================= TABLE UTILS ================= */

    private int getColumnIndex(String columnName) {
        for (int i = 0; i < COLUMN_NAMES.size(); i++) {
            if (COLUMN_NAMES.get(i).equalsIgnoreCase(columnName.trim())) {
                return i + 1;
            }
        }
        throw new RuntimeException("Column not found: " + columnName);
    }

    private WebElement getCell(int row, int column) {
        String xpath = String.format("//table/tbody/tr[%d]/td[%d]", row, column);
        return driver.findElement(By.xpath(xpath));
    }

    public int getTotalMajors() {
        return driver.findElements(tableRows).size();
    }

    public ArrayList<String> getAllMajorNames() {
        ArrayList<String> majorNames = new ArrayList<>();
        int totalRows = getTotalMajors();

        for (int i = 1; i <= totalRows; i++) {
            majorNames.add(
                    getCell(i, getColumnIndex("MÃ CHUYÊN NGÀNH")).getText().trim()
            );
        }
        return majorNames;
    }

    /* ================= SEARCH ================= */

    public void searchMajorName(String className) {
        WebElement searchBox = wait.until(
                ExpectedConditions.visibilityOfElementLocated(searchInput)
        );
        searchBox.clear();
        searchBox.sendKeys(className);
    }

    public boolean verifySearchResultContainsKeyword(String keyword) {
        int totalRows = getTotalMajors();

        for (int i = 1; i <= totalRows; i++) {
            String className = getCell(i, getColumnIndex("TÊN LỚP"))
                    .getText().trim();
            if (!className.contains(keyword)) {
                return false;
            }
        }
        return true;
    }
    /* ================= DELETE ================= */

    public String randomClickIconAndDelete() {
        List<WebElement> rows = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(tableRows)
        );

        WebElement row = rows.get(new Random().nextInt(rows.size()));

        String deletedMajorName = row.findElement(
                By.xpath(".//td[" + getColumnIndex("MÃ CHUYÊN NGÀNH") + "]")
        ).getText().trim();

        WebElement dropdownIcon = row.findElement(
                By.xpath("//td//button[@data-slot='dropdown-menu-trigger']")
        );

        new Actions(driver).moveToElement(dropdownIcon).click().perform();
        wait.until(ExpectedConditions.elementToBeClickable(deleteButton)).click();
        wait.until(ExpectedConditions.elementToBeClickable(confirmYesButton)).click();

        return deletedMajorName;
    }
}
