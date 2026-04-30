package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
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
    private final By updateButton = By.xpath("//button[contains(text(),'Cập nhật') or contains(text(),'Lưu')]");

    private final By searchInput = By.xpath("//input[@placeholder='Nhập mã khóa...']");
    private final By tableRows = By.xpath("//tbody/tr");
    private final By noDataMsg = By.xpath("//td//div[contains(text(),'Không có')]");

    private final By deleteButton = By.xpath("//div[.='Xóa']");
    private final By confirmYesButton = By.xpath("//button[normalize-space()='Có']");

    private static final List<String> COLUMN_NAMES = List.of(
            "STT",
            "KHÓA",
            "NĂM BẮT ĐẦU",
            "NĂM KẾT THÚC"
    );

    /* ================= NAVIGATE ================= */

    public void goToCohortPage() {
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//span[contains(text(),'Cài đặt')]")
        ));
        clickMenu("Cài đặt");
        clickMenu("Quản lý khoá");
    }

    /* ================= ADD ================= */

    public void openAddCohortForm() {
        wait.until(ExpectedConditions.elementToBeClickable(addButton)).click();
    }

    public void addCohort(String code, String start, String end) {

        wait.until(ExpectedConditions.visibilityOfElementLocated(cohortCode)).sendKeys(code);
        driver.findElement(startYear).sendKeys(start);
        driver.findElement(endYear).sendKeys(end);

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

    private WebElement getCell(int row, int col) {
        return driver.findElement(By.xpath("//table/tbody/tr[" + row + "]/td[" + col + "]"));
    }

    public int getTotalCohorts() {
        wait.until(ExpectedConditions.presenceOfElementLocated(tableRows));
        return driver.findElements(tableRows).size();
    }

    public List<String> getAllCohortCodes() {
        List<String> list = new ArrayList<>();
        int total = getTotalCohorts();

        for (int i = 1; i <= total; i++) {
            list.add(getCell(i, getColumnIndex("KHÓA")).getText().trim());
        }
        return list;
    }

    /* ================= SEARCH ================= */

    public void searchCohort(String keyword) {
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(searchInput));
        input.clear();
        input.sendKeys(keyword);
    }

    public void waitForCohortAppear(String code) {
        wait.until(driver ->
                driver.findElements(By.xpath("//td[contains(text(),'" + code + "')]")).size() > 0
        );
    }

    public void waitForTableLoad() {
        wait.until(ExpectedConditions.presenceOfElementLocated(tableRows));
    }

    public String getNoDataMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(noDataMsg))
                .getText().trim();
    }

    /* ================= EDIT ================= */

    public void openEditCohortForm(String code) {
        By actionBtn = By.xpath("//tr[td[normalize-space()='" + code + "']]//button");

        wait.until(ExpectedConditions.elementToBeClickable(actionBtn)).click();

        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[normalize-space()='Sửa']")
        )).click();
    }

    public void editStartYear(String newYear) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(startYear));
        input.clear();
        input.sendKeys(newYear);
        driver.findElement(updateButton).click();
    }

    public void editEndYear(String newYear) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(endYear));
        input.clear();
        input.sendKeys(newYear);
        driver.findElement(updateButton).click();
    }

    /* ================= DELETE ================= */

    public void deleteCohort(String code) {
        By actionBtn = By.xpath("//tr[td[normalize-space()='" + code + "']]//button");

        wait.until(ExpectedConditions.elementToBeClickable(actionBtn)).click();
        wait.until(ExpectedConditions.elementToBeClickable(deleteButton)).click();
        wait.until(ExpectedConditions.elementToBeClickable(confirmYesButton)).click();
    }

    /* ================= VERIFY ================= */

    public boolean verifyCohortContainsCode(String code) {
        int total = getTotalCohorts();

        for (int i = 1; i <= total; i++) {
            String actual = getCell(i, getColumnIndex("KHÓA")).getText().trim();

            if (actual.equalsIgnoreCase(code.trim())) {
                return true;
            }
        }
        return false;
    }

    /* ================= DATA ================= */

    public String generateUniqueCohortCode() {
        List<String> existing = getAllCohortCodes();
        Random r = new Random();

        for (int i = 0; i < 30; i++) {
            String code = String.valueOf(r.nextInt(90) + 10);
            if (!existing.contains(code)) {
                return code;
            }
        }

        throw new RuntimeException("Cannot generate unique cohort code");
    }

    /* ================= YEAR GENERATORS ================= */

    public String generateStartYear(String endYear) {

        if (endYear == null) {
            throw new RuntimeException("endYear is null");
        }

        List<String> existingYears = driver.findElements(
                        By.xpath("//table//tbody//tr//td[" + getColumnIndex("NĂM BẮT ĐẦU") + "]")
                ).stream()
                .map(e -> e.getText().trim())
                .filter(s -> !s.isEmpty())
                .toList();

        int end = Integer.parseInt(endYear);

        int min = 2000;
        int max = end - 1;

        if (max <= min) {
            throw new RuntimeException("Invalid range: startYear must be < endYear");
        }

        Random r = new Random();

        for (int i = 0; i < 50; i++) {

            String start = String.valueOf(min + r.nextInt(max - min + 1));

            if (!existingYears.contains(start)) {
                return start;
            }
        }

        throw new RuntimeException("Cannot generate start year");
    }

    public String generateEndYear(String startYear) {

        if (startYear == null) {
            throw new RuntimeException("startYear is null");
        }

        List<String> existingYears = driver.findElements(
                        By.xpath("//table//tbody//tr//td[" + getColumnIndex("NĂM KẾT THÚC") + "]")
                ).stream()
                .map(e -> e.getText().trim())
                .filter(s -> !s.isEmpty())
                .toList();

        int start = Integer.parseInt(startYear);

        int min = start + 1;
        int max = start + 10;

        Random r = new Random();

        for (int i = 0; i < 50; i++) {

            String end = String.valueOf(min + r.nextInt(max - min + 1));

            if (!existingYears.contains(end)) {
                return end;
            }
        }

        throw new RuntimeException("Cannot generate end year");
    }

    public String getRandomCohortCode() {

        wait.until(driver -> getTotalCohorts() > 0);

        By locator = By.xpath("//table//tbody//tr//td[" + getColumnIndex("KHÓA") + "]");

        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));

        List<WebElement> list = driver.findElements(locator);

        return list.get(new Random().nextInt(list.size())).getText().trim();
    }
    public String getStartYearByCode(String code) {

        By cell = By.xpath(
                "//tr[td[normalize-space()='" + code + "']]/td[" + getColumnIndex("NĂM BẮT ĐẦU") + "]"
        );

        wait.until(ExpectedConditions.presenceOfElementLocated(cell));

        return driver.findElement(cell).getText().trim();
    }
    public String getEndYearByCode(String code) {

        By cell = By.xpath(
                "//tr[td[normalize-space()='" + code + "']]/td[" + getColumnIndex("NĂM KẾT THÚC") + "]"
        );

        wait.until(ExpectedConditions.presenceOfElementLocated(cell));

        return driver.findElement(cell).getText().trim();
    }
    public String getRandomExistingStartYear() {

        List<WebElement> elements = driver.findElements(
                By.xpath("//table//tbody//tr//td[" + getColumnIndex("NĂM BẮT ĐẦU") + "]")
        );

        List<String> years = elements.stream()
                .map(e -> e.getText().trim())
                .filter(s -> !s.isEmpty())
                .distinct()
                .toList();

        if (years.isEmpty()) {
            throw new RuntimeException("No data in column NĂM BẮT ĐẦU");
        }

        return years.get(new Random().nextInt(years.size()));
    }
    public String getRandomExistingCohortCode() {

        List<WebElement> elements = driver.findElements(
                By.xpath("//table//tbody//tr//td[" + getColumnIndex("KHÓA") + "]")
        );

        List<String> codes = elements.stream()
                .map(e -> e.getText().trim())
                .filter(s -> !s.isEmpty())
                .distinct()
                .toList();

        if (codes.isEmpty()) {
            throw new RuntimeException("No data in column KHÓA");
        }

        return codes.get(new Random().nextInt(codes.size()));
    }
    public void clickOutsideForm() {
        driver.findElement(By.tagName("body")).click();
    }
    public boolean isToastMessageDisplayed() {

        try {
            return wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//p[@class='text-sm text-red-600 mb-3']")
                    )
            ).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

}