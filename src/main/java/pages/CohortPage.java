package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CohortPage extends BasePage {

    public CohortPage(WebDriver driver) {
        super(driver);
    }

    /* ================= LOCATORS ================= */

    private final By addButton = By.xpath("//button[contains(text(),'Thêm')]");
    private final By cohortCode = By.xpath("//input[@placeholder='Nhập mã khoá']");
    private final By startYear = By.xpath("//input[@placeholder='Nhập năm bắt đầu']");
    private final By endYear = By.xpath("//input[@placeholder='Nhập năm kết thúc']");
    private final By saveButton = By.xpath("//button[contains(text(),'Lưu')]");

    private final By tableRows = By.xpath("//tbody/tr");

    private final By searchInput = By.xpath("//input[@placeholder='Nhập mã khóa...']");

    private static final List<String> COLUMN_NAMES = List.of(
            "STT",
            "KHÓA",
            "NĂM BẮT ĐẦU",
            "NĂM KẾT THÚC"
    );

    /* ================= NAVIGATE ================= */


    public void goToCohortPage() {
        clickMenu("Cài đặt");
        clickMenu("Quản lý khoá");
    }

    /* ================= ADD COHORT ================= */

    public void openAddForm() {
        wait.until(ExpectedConditions.elementToBeClickable(addButton)).click();
    }

    private void enterCode(String code) {
        driver.findElement(cohortCode).sendKeys(code);
    }

    private void enterStartYear(String year) {
        driver.findElement(startYear).sendKeys(year);
    }

    private void enterEndYear(String year) {
        driver.findElement(endYear).sendKeys(year);
    }

    public void addCohort(String code, String start, String end) {
        openAddForm();
        enterCode(code);
        enterStartYear(start);
        enterEndYear(end);
        driver.findElement(saveButton).click();
    }

    /* ================= TABLE ================= */

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

    public int getTotalCohorts() {
        return driver.findElements(tableRows).size();
    }

    public List<String> getAllCohortCodes() {
        List<String> codes = new ArrayList<>();
        int total = getTotalCohorts();

        for (int i = 1; i <= total; i++) {
            codes.add(
                    getCell(i, getColumnIndex("KHÓA")).getText().trim()
            );
        }
        return codes;
    }

    /* ================= SEARCH ================= */

    public void searchCohort(String keyword) {
        WebElement input = wait.until(
                ExpectedConditions.elementToBeClickable(searchInput)
        );

        input.clear();
        input.sendKeys(keyword);
        input.sendKeys(Keys.ENTER);
    }

    public boolean verifySearchResult(String keyword) {
        int total = getTotalCohorts();

        for (int i = 1; i <= total; i++) {
            String code = getCell(i, getColumnIndex("KHÓA"))
                    .getText().trim();

            if (!code.contains(keyword)) {
                return false;
            }
        }
        return true;
    }

    /* ================= RANDOM DATA ================= */

    public String generateUniqueCohortCode() {

        List<String> existing = getAllCohortCodes();
        Random random = new Random();

        String code;

        do {
            code = String.valueOf(random.nextInt(90) + 10);
        } while (existing.contains(code));

        return code;
    }

    public String generateStartYear() {
        return String.valueOf(2020 + new Random().nextInt(10));
    }

    public String generateEndYear(String startYear) {
        return String.valueOf(Integer.parseInt(startYear) + 4);
    }
}