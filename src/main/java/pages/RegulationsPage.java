package pages;

import models.RegulationItem;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.*;

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

    private final By applyCourseDropdown = By.xpath("//span[contains(text(),'Chọn khoá áp dụng')]");
    private final By applyMajorDropdown = By.xpath("//span[contains(text(),'Chọn chuyên ngành áp dụng')]");

    private final By saveButton = By.xpath("//button[contains(text(),'Lưu')]");
    private final By messageLocator = By.xpath("//p[contains(@class,'text-xs text-red')]");
    private final By searchBarLocator = By.xpath("//input[@placeholder='Nhập tên quy chế...']");
    private final By noDataMessage = By.xpath("//div[contains(@class,'flex')]/span");
    private final By tableRows = By.xpath("//tbody/tr");

    private final By formContainer = By.xpath("//div[@role='dialog']");
    private final By deleteButton = By.xpath("//div[.=' Xóa']");
    private final By updateIcon = By.xpath("//div[.=' Sửa']");
    private final By yesOption = By.xpath("//button[.='Có']");
    private final By confirmYesButton = By.xpath("//button[normalize-space()='Có']");

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

        driver.findElement(applyCourseDropdown).click();
        Thread.sleep(1000);

        By courseOption = By.xpath(String.format("//div[.='%s']", course));
        wait.until(ExpectedConditions.visibilityOfElementLocated(courseOption));
        wait.until(ExpectedConditions.elementToBeClickable(courseOption)).click();

        Thread.sleep(500);
        driver.findElement(formContainer).click();
        Thread.sleep(500);

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
    public void addRegulationWithoutCourse(String name,
                                           String requiredCredits,
                                           String electiveCredits,
                                           String gpa,
                                           String major) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(ExpectedConditions.elementToBeClickable(addButtonLocator)).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(regulationName)).sendKeys(name);
        driver.findElement(minRequiredCredits).sendKeys(requiredCredits);
        driver.findElement(minElectiveCredits).sendKeys(electiveCredits);
        driver.findElement(minGPA).sendKeys(gpa);

        wait.until(ExpectedConditions.elementToBeClickable(applyMajorDropdown)).click();

        By majorOption = By.xpath(String.format("//div[.='%s']", major));
        wait.until(ExpectedConditions.elementToBeClickable(majorOption)).click();

        wait.until(ExpectedConditions.elementToBeClickable(formContainer)).click();

        wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
    }
    public void addRegulationWithoutMajor(String name,
                                          String requiredCredits,
                                          String electiveCredits,
                                          String gpa,
                                          String course) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(ExpectedConditions.elementToBeClickable(addButtonLocator)).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(regulationName)).sendKeys(name);
        driver.findElement(minRequiredCredits).sendKeys(requiredCredits);
        driver.findElement(minElectiveCredits).sendKeys(electiveCredits);
        driver.findElement(minGPA).sendKeys(gpa);
        wait.until(ExpectedConditions.elementToBeClickable(applyCourseDropdown)).click();

        By courseOption = By.xpath(String.format("//div[.='%s']", course));
        wait.until(ExpectedConditions.elementToBeClickable(courseOption)).click();

        wait.until(ExpectedConditions.elementToBeClickable(formContainer)).click();

        wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
    }
    public String getErrorMsg() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//p[contains(@class,'text-red-500') and contains(@class,'text-xs')]")
        )).getText().trim();
    }

    /* ================= GET ONE ================= */

    public RegulationItem getRegulationByName(String name) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        List<WebElement> headers = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(
                        By.xpath("//table/thead/tr/th")
                )
        );

        Map<String, Integer> colIndex = new HashMap<>();

        for (int i = 0; i < headers.size(); i++) {
            String headerName = headers.get(i).getText().trim().toUpperCase();
            colIndex.put(headerName, i + 1);
        }

        List<WebElement> rows = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(tableRows)
        );

        int nameIdx = colIndex.get("TÊN QUY CHẾ");
        int courseIdx = colIndex.get("KHÓA ÁP DỤNG");
        int majorIdx = colIndex.get("CHUYÊN NGÀNH ÁP DỤNG");

        for (WebElement row : rows) {

            String rowName = wait.until(d ->
                    row.findElement(By.xpath("./td[" + nameIdx + "]"))
            ).getText().trim();

            if (rowName.toLowerCase().contains(name.trim().toLowerCase())) {

                WebElement clickableCell = row.findElement(
                        By.xpath("./td[" + nameIdx + "]")
                );

                wait.until(ExpectedConditions.elementToBeClickable(clickableCell));
                clickableCell.click();

                String course = row.findElement(By.xpath("./td[" + courseIdx + "]"))
                        .getText().trim();

                String major = row.findElement(By.xpath("./td[" + majorIdx + "]"))
                        .getText().trim();

                return new RegulationItem(rowName, course, major);
            }
        }

        throw new RuntimeException("Không tìm thấy regulation: " + name);
    }

    /* ================= SEARCH ================= */

    public void searchRegulation(String keyword) {
        WebElement searchBox = wait.until(
                ExpectedConditions.visibilityOfElementLocated(searchBarLocator)
        );
        searchBox.clear();
        searchBox.sendKeys(keyword);
    }

    public void waitForSearchResult() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(noDataMessage),
                ExpectedConditions.visibilityOfAllElementsLocatedBy(tableRows)
        ));
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

    public boolean isNoDataMessageDisplayed() {
        return wait.until(
                        ExpectedConditions.visibilityOfElementLocated(noDataMessage)
                )
                .getText()
                .trim()
                .contains("Không có quy chế");
    }

    public String getMessage() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(messageLocator)
        ).getText().trim();
    }

    public boolean isErrorMessageDisplayed() {
        return driver.findElements(messageLocator).size() > 0;
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

    public RegulationItem getRandomRegulation() {

        List<WebElement> rows = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(tableRows)
        );

        WebElement row = rows.get(new Random().nextInt(rows.size()));

        String name = row.findElement(By.xpath(".//td[" + getColumnIndex("TÊN QUY CHẾ") + "]"))
                .getText().trim();

        String course = row.findElement(By.xpath(".//td[" + getColumnIndex("KHÓA ÁP DỤNG") + "]"))
                .getText().trim();

        String major = row.findElement(By.xpath(".//td[" + getColumnIndex("CHUYÊN NGÀNH ÁP DỤNG") + "]"))
                .getText().trim();

        return new RegulationItem(name, course, major);
    }

    /* ================= DELETE ================= */

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
                By.xpath("//table//button[@data-slot='dropdown-menu-trigger']")
        );

        new Actions(driver).moveToElement(dropdownIcon).perform();
        dropdownIcon.click();

        wait.until(ExpectedConditions.elementToBeClickable(deleteButton)).click();
        wait.until(ExpectedConditions.elementToBeClickable(yesOption)).click();

        return regulationName;
    }

    /* ================= EDIT ================= */

    public void openEditFormByName(String regulationName) {
        driver.findElement(By.xpath("//table//button[@data-slot='dropdown-menu-trigger']")).click();
        wait.until(ExpectedConditions.elementToBeClickable(updateIcon)).click();
    }

    public void editRegulationName(String newName) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement nameInput = wait.until(
                ExpectedConditions.visibilityOfElementLocated(regulationName)
        );

        nameInput.sendKeys(Keys.CONTROL + "a");
        nameInput.sendKeys(Keys.DELETE);
        nameInput.sendKeys(newName);

        wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
    }

    public void editRequiredCredits(String value) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(minRequiredCredits));
        input.sendKeys(Keys.CONTROL + "a");
        input.sendKeys(Keys.DELETE);
        input.sendKeys(value);
        wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
    }

    public void editElectiveCredits(String value) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(minElectiveCredits));
        input.sendKeys(Keys.CONTROL + "a");
        input.sendKeys(Keys.DELETE);
        input.sendKeys(value);
        wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
    }

    public void editGPA(String value) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(minGPA));
        input.sendKeys(Keys.CONTROL + "a");
        input.sendKeys(Keys.DELETE);
        input.sendKeys(value);
        wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
    }

    /* ================= UTIL ================= */

    public void refreshPage() {
        driver.navigate().refresh();
    }

    public String generateNonExistingKeyword() {
        return "AUTO_NOT_EXIST_" + System.currentTimeMillis();
    }
    public String getMsg() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(@class,'text-gray-500') and contains(@class,'text-sm')]")
        )).getText().trim();
    }
    public boolean isRegulationTableEmpty() {
        return driver.findElements(tableRows).isEmpty();
    }
    public void deleteRegulation(String regulationName) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        By rowLocator = By.xpath("//tr[td[contains(.,'" + regulationName + "')]]");

        WebElement row = wait.until(
                ExpectedConditions.presenceOfElementLocated(rowLocator)
        );

        WebElement actionBtn = row.findElement(
                By.xpath(".//button[@data-slot='dropdown-menu-trigger']")
        );

        wait.until(ExpectedConditions.elementToBeClickable(actionBtn)).click();

        WebElement deleteBtn = wait.until(
                ExpectedConditions.elementToBeClickable(deleteButton)
        );
        deleteBtn.click();

        WebElement confirmBtn = wait.until(
                ExpectedConditions.elementToBeClickable(confirmYesButton)
        );
        confirmBtn.click();
    }
    public String getRandomRegulationName() {

        waitForTableLoaded();

        List<WebElement> rows = driver.findElements(By.xpath("//tbody/tr"));

        WebElement row = rows.get(new Random().nextInt(rows.size()));

        return row.findElements(By.tagName("td")).get(0).getText();
    }

}