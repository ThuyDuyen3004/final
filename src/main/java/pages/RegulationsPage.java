package pages;

import models.Setting.RegulationCondition;
import models.Setting.RegulationDetail;
import models.Setting.RegulationItem;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RegulationsPage extends BasePage {

    public RegulationsPage(WebDriver driver) {
        super(driver);
    }

    /* ================= NAVIGATION ================= */

    public void goToSettingPage() {
        clickMenu("Cài đặt");
    }

    public void goToRegulationsPage() {
        goToSettingPage();
        clickMenu("Quản lý quy chế");
    }

    /* ================= LOCATORS ================= */

    private final By addButtonLocator = By.xpath("//button[contains(text(),'Thêm')]");
    private final By regulationName = By.xpath("//input[@name='name']");
    private final By minCreditsTotal = By.xpath("//input[@name='minTotalCredits']");
    private final By minRequiredCredits = By.xpath("//input[@name='minRequiredCredits']");
    private final By minElectiveCredits = By.xpath("//input[@name='minElectiveCredits']");
    private final By minGPA = By.xpath("//input[@name='minGpa']");

    private final By applyCourseDropdown =
            By.xpath("//span[contains(text(),'Chọn khoá áp dụng')]");
    private final By applyMajorDropdown =
            By.xpath("//span[contains(text(),'Chọn chuyên ngành áp dụng')]");

    private final By saveButton = By.xpath("//button[contains(text(),'Lưu')]");
    private final By messageLocator = By.xpath("//p[contains(@class,'text-xs text-red')]");
    private final By searchBarLocator = By.xpath("//input[@data-slot='input']");
    private final By noDataMessage = By.xpath("//div[contains(@class,'flex')]/span");
    private final By tableRows = By.xpath("//tbody/tr");

    private final By formContainer = By.xpath("//div[@role='dialog']");
    private final By deleteButton =
            By.xpath("//div[.=' Xóa']");

    private final By updateIcon =
            By.xpath("//div[.=' Sửa']");

    private final By yesOption =
            By.xpath("//button[.='Có']");

    /* ================= ADD ================= */

    public void addRegulation(String name,
                              String requiredCredits,
                              String electiveCredits,
                              String gpa,
                              String course,
                              String major) throws InterruptedException {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(ExpectedConditions.elementToBeClickable(addButtonLocator)).click();
        Thread.sleep(1000);

        wait.until(ExpectedConditions.visibilityOfElementLocated(regulationName)).sendKeys(name);
        driver.findElement(minRequiredCredits).sendKeys(requiredCredits);
        driver.findElement(minElectiveCredits).sendKeys(electiveCredits);
        driver.findElement(minGPA).sendKeys(gpa);

        Thread.sleep(1000);

        // ===== COURSE =====
        driver.findElement(applyCourseDropdown).click();
        Thread.sleep(1000);

        By courseOption = By.xpath(String.format("//div[.='%s']", course));
        wait.until(ExpectedConditions.visibilityOfElementLocated(courseOption));
        wait.until(ExpectedConditions.elementToBeClickable(courseOption)).click();

        Thread.sleep(500);
        driver.findElement(formContainer).click(); // đóng dropdown
        Thread.sleep(500);

        // ===== MAJOR =====
        driver.findElement(applyMajorDropdown).click();
        Thread.sleep(1000);

        By majorOption = By.xpath(String.format("//div[.='%s']", major));
        wait.until(ExpectedConditions.visibilityOfElementLocated(majorOption));
        wait.until(ExpectedConditions.elementToBeClickable(majorOption)).click();

        Thread.sleep(500);
        driver.findElement(formContainer).click();
        Thread.sleep(500);

        wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
        Thread.sleep(1500);
    }

    /* ================= GET ONE ================= */

    public RegulationItem getRegulationByName(String name) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(tableRows));

        List<WebElement> rows = driver.findElements(tableRows);

        for (WebElement row : rows) {

            // 👉 FIX: td[1] = STT → bỏ
            String rowName = row.findElement(By.xpath("./td[2]")).getText().trim();

            if (rowName.toLowerCase().contains(name.trim().toLowerCase())) {

                String course = row.findElement(By.xpath("./td[3]")).getText().trim();
                String major = row.findElement(By.xpath("./td[4]")).getText().trim();

                return new RegulationItem(rowName, course, major);
            }
        }

        throw new RuntimeException("❌ Không tìm thấy regulation: " + name);
    }

    /* ================= SEARCH ================= */

    public void searchRegulation(String keyword) {
        WebElement searchBox = wait.until(
                ExpectedConditions.visibilityOfElementLocated(searchBarLocator)
        );
        searchBox.clear();
        searchBox.sendKeys(keyword);
    }

    public boolean verifySearchResultContainsKeyword(String keyword) {

        for (int i = 1; i <= getTotalRegulations(); i++) {

            String name = getCell(i, getColumnIndex("TÊN QUY CHẾ"))
                    .getText().toLowerCase();

            if (name.contains(keyword.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public boolean verifyNoDataMessageDisplayed() {
        WebElement msg = wait.until(
                ExpectedConditions.visibilityOfElementLocated(noDataMessage)
        );
        return msg.getText().trim().equals("Không có dữ liệu");
    }

    public boolean isErrorMessageDisplayed() {
        return driver.findElements(messageLocator).size() > 0;
    }

    public String getMessage() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(messageLocator)
        ).getText().trim();
    }

    /* ================= TABLE UTILS ================= */

    private static final List<String> COLUMN_NAMES = List.of(
            "STT",
            "TÊN QUY CHẾ",
            "KHÓA ÁP DỤNG",
            "CHUYÊN NGÀNH ÁP DỤNG"
    );

    private int getColumnIndex(String columnName) {
        return COLUMN_NAMES.indexOf(columnName) + 1;
    }

    private WebElement getCell(int row, int column) {
        String xpath = String.format("//table/tbody/tr[%d]/td[%d]", row, column);
        return driver.findElement(By.xpath(xpath));
    }

    public int getTotalRegulations() {
        return driver.findElements(tableRows).size();
    }

    /* ================= GET ALL ================= */

    public ArrayList<RegulationItem> getAllRegulations() {

        ArrayList<RegulationItem> list = new ArrayList<>();

        for (int i = 1; i <= getTotalRegulations(); i++) {

            String name = getCell(i, getColumnIndex("TÊN QUY CHẾ")).getText().trim();
            String course = getCell(i, getColumnIndex("KHÓA ÁP DỤNG")).getText().trim();
            String major = getCell(i, getColumnIndex("CHUYÊN NGÀNH ÁP DỤNG")).getText().trim();

            list.add(new RegulationItem(name, course, major));
        }

        return list;
    }
    public void fillToGpaAndSave(String name,
                                 String requiredCredits,
                                 String electiveCredits,
                                 String gpa) throws InterruptedException {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(ExpectedConditions.elementToBeClickable(addButtonLocator)).click();
        Thread.sleep(1000);

        wait.until(ExpectedConditions.visibilityOfElementLocated(regulationName)).sendKeys(name);
        driver.findElement(minRequiredCredits).sendKeys(requiredCredits);
        driver.findElement(minElectiveCredits).sendKeys(electiveCredits);
        driver.findElement(minGPA).sendKeys(gpa);

        Thread.sleep(1000);

        wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
    }
    public String deleteRegulationByName(String regulationName) {

        List<WebElement> rows = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(tableRows)
        );

        WebElement targetRow = null;

        for (WebElement row : rows) {

            String name = row.findElement(
                    By.xpath(".//td[" + getColumnIndex("TÊN QUY CHẾ") + "]")
            ).getText().trim();

            if (name.equalsIgnoreCase(regulationName)) {
                targetRow = row;
                break;
            }
        }


        WebElement dropdownIcon = targetRow.findElement(
                By.xpath(".//button[@data-slot='dropdown-menu-trigger']")
        );

        new Actions(driver).moveToElement(dropdownIcon).perform();
        dropdownIcon.click();

        wait.until(ExpectedConditions.elementToBeClickable(deleteButton)).click();

        wait.until(ExpectedConditions.elementToBeClickable(yesOption)).click();

        return regulationName;
    }

    public void openEditFormByName(String regulationName) {

        List<WebElement> rows = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(tableRows)
        );

        WebElement targetRow = null;

        for (WebElement row : rows) {

            String name = row.findElement(
                    By.xpath(".//td[" + getColumnIndex("TÊN QUY CHẾ") + "]")
            ).getText().trim();

            if (name.equalsIgnoreCase(regulationName)) {
                targetRow = row;
                break;
            }
        }

        if (targetRow == null) {
            throw new RuntimeException("Không tìm thấy regulation: " + regulationName);
        }

        WebElement dropdownIcon = targetRow.findElement(
                By.xpath(".//button[@data-slot='dropdown-menu-trigger']")
        );

        new Actions(driver).moveToElement(dropdownIcon).perform();
        dropdownIcon.click();

        wait.until(ExpectedConditions.elementToBeClickable(updateIcon)).click();
    }
    public void editRegulationName(String newName) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // input tên quy chế trong form edit
        WebElement nameInput = wait.until(
                ExpectedConditions.visibilityOfElementLocated(regulationName)
        );

        // clear an toàn hơn clear()
        nameInput.sendKeys(Keys.CONTROL + "a");
        nameInput.sendKeys(Keys.DELETE);

        // nhập tên mới
        nameInput.sendKeys(newName);

        // click save
        wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
    }
    public RegulationItem getRandomRegulation() {

        List<WebElement> rows = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(tableRows)
        );

        WebElement row = rows.get(new Random().nextInt(rows.size()));

        String name = row.findElement(
                By.xpath(".//td[" + getColumnIndex("TÊN QUY CHẾ") + "]")
        ).getText().trim();

        String course = row.findElement(
                By.xpath(".//td[" + getColumnIndex("KHÓA ÁP DỤNG") + "]")
        ).getText().trim();

        String major = row.findElement(
                By.xpath(".//td[" + getColumnIndex("CHUYÊN NGÀNH ÁP DỤNG") + "]")
        ).getText().trim();

        return new RegulationItem(name, course, major);
    }
    public String generateNonExistingKeyword() {
        return "AUTO_NOT_EXIST_" + System.currentTimeMillis();
    }
    public boolean isRegulationTableEmpty() {
        List<WebElement> rows = driver.findElements(tableRows);
        return rows.isEmpty();
    }
    public void openRegulationByName(String name) {
        WebElement row = driver.findElement(By.xpath("//button[contains(text(),'" + name + "')]"));
        row.click();
    }
    public String getValueByCondition(String conditionName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // tìm row chứa condition
        WebElement row = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//table//tbody//tr[td[2][contains(text(),'" + conditionName + "')]]")
        ));

        // lấy cột GIÁ TRỊ (td thứ 4)
        String value = row.findElements(By.tagName("td")).get(3).getText().trim();

        return value;
    }

    private String getValue(By locator) {
        WebElement element = driver.findElement(locator);

        // handle cả input và text
        String value = element.getAttribute("value");
        return value != null ? value.trim() : element.getText().trim();
    }

    public List<RegulationCondition> getRegulationConditions() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//table//tbody//tr")
        ));

        List<RegulationCondition> list = new ArrayList<>();
        List<WebElement> rows = driver.findElements(By.xpath("//table//tbody//tr"));

        for (WebElement row : rows) {
            List<WebElement> cols = row.findElements(By.tagName("td"));

            String condition = cols.get(1).getText();
            String operator = cols.get(2).getText();
            String value = cols.get(3).getText();

            list.add(new RegulationCondition(condition, operator, value));
        }

        return list;
    }

    public List<String> getConditionValues() {
        List<String> values = new ArrayList<>();

        List<WebElement> rows = driver.findElements(By.xpath("//table//tbody//tr"));

        for (WebElement row : rows) {
            List<WebElement> cols = row.findElements(By.tagName("td"));
            values.add(cols.get(3).getText().trim()); // cột GIÁ TRỊ
        }

        return values;
    }
    public void refreshPage() {
        driver.navigate().refresh();
    }
    public void editRequiredCredits(String value) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(minRequiredCredits));
        input.sendKeys(Keys.CONTROL + "a");
        input.sendKeys(Keys.DELETE);
        input.sendKeys(value);
        // click save
        wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
    }
    public void editElectiveCredits(String value) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(minElectiveCredits));
        input.sendKeys(Keys.CONTROL + "a");
        input.sendKeys(Keys.DELETE);
        input.sendKeys(value);
        // click save
        wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
    }
    public void editGPA(String value) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(minGPA));
        input.sendKeys(Keys.CONTROL + "a");
        input.sendKeys(Keys.DELETE);
        input.sendKeys(value);
        // click save
        wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
    }
}