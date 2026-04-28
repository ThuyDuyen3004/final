package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MajorPage extends BasePage{
    public MajorPage(WebDriver driver) {
        super(driver);
    }

    public void goToSettingPage() {
        clickMenu("Cài đặt");
    }

    public void goToMajorPage() {
        goToSettingPage();
        clickMenu("Quản lý chuyên ngành");
    }

    private final By addButton = By.xpath("//button[contains(text(),'Thêm')]");
    private final By majorID = By.id("code");
    private final By majorName = By.id("name");
    private final By saveButton = By.xpath("//button[contains(text(),'Lưu')]");
    private final By updateButton = By.xpath("//button[contains(text(),'Cập nhật')]");
    private static final String OPTION_XPATH =
            "//div[@role='option' and normalize-space(.)='%s']";
    private final By tableRows = By.xpath("//tbody/tr");
    private final By searchInput = By.xpath("//input[@placeholder='Nhập tên chuyên ngành...']");
    private final By deleteButton = By.xpath("//div[.='Xóa']");
    private final By confirmYesButton = By.xpath("//button[normalize-space()='Có']");
    private static final List<String> COLUMN_NAMES = List.of(
            "STT",
            "MÃ CHUYÊN NGÀNH",
            "TÊN CHUYÊN NGÀNH"
    );

    /* ================= ADD CLASS ================= */

    public void openAddMajorForm() {
        wait.until(ExpectedConditions.elementToBeClickable(addButton)).click();
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
    public String getRandomMajorName() {

        wait.until(driver -> getTotalMajors() > 0);

        int total = getTotalMajors();
        int row = new Random().nextInt(total) + 1;

        return getCell(row, getColumnIndex("TÊN CHUYÊN NGÀNH"))
                .getText()
                .trim();
    }
    public void searchMajor(String keyword) {

        WebElement input = wait.until(
                ExpectedConditions.elementToBeClickable(searchInput)
        );

        input.clear();
        input.sendKeys(keyword);
        input.sendKeys(Keys.ENTER);
    }
    public boolean verifyMajorSearchResultContainsKeyword(String keyword) {

        int total = getTotalMajors();

        for (int i = 1; i <= total; i++) {

            String majorName = getCell(i, getColumnIndex("TÊN CHUYÊN NGÀNH"))
                    .getText()
                    .trim();

            if (!majorName.contains(keyword)) {
                return false;
            }
        }

        return true;
    }
    private final By noDataMessage = By.xpath("//div[contains(@class,'text-sm')]");
    public boolean isNoDataDisplayed() {
        List<WebElement> els = driver.findElements(noDataMessage);
        return !els.isEmpty() && els.get(0).isDisplayed();
    }
    public boolean verifyMajorContainsCode(String code) {

        int total = getTotalMajors();

        for (int i = 1; i <= total; i++) {

            String actualCode = getCell(i, getColumnIndex("MÃ CHUYÊN NGÀNH"))
                    .getText()
                    .trim();

            if (actualCode.equals(code)) {
                return true;
            }
        }

        return false;
    }
    public List<String> getAllMajorCodes() {

        List<String> codes = new ArrayList<>();

        int total = getTotalMajors();

        for (int i = 1; i <= total; i++) {

            String code = getCell(i, getColumnIndex("MÃ CHUYÊN NGÀNH"))
                    .getText()
                    .trim();

            codes.add(code);
        }

        return codes;
    }
    public String generateUniqueMajorCode() {

        List<String> existingCodes = getAllMajorCodes();

        Random random = new Random();

        String newCode;

        do {
            int number = random.nextInt(90) + 10; // 10–99
            newCode = "K" + number;
        } while (existingCodes.contains(newCode));

        return newCode;
    }
    public String generateMajorName() {

        List<String> names = List.of(
                "Trí tuệ nhân tạo",
                "Khoa học dữ liệu",
                "An ninh mạng",
                "Phát triển phần mềm",
                "Hệ thống thông tin",
                "IoT"
        );

        return names.get(new Random().nextInt(names.size()));
    }
    public void openEditMajorForm(String majorCode) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement rowActionBtn = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//tr[.//td[contains(.,'" + majorCode + "')]]//button")
                )
        );

        rowActionBtn.click();

        WebElement editBtn = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//div[.='Sửa']")
                )
        );

        editBtn.click();
    }
    public void editMajorCode(String newCode) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement codeInput = wait.until(
                ExpectedConditions.visibilityOfElementLocated(majorID));

        codeInput.clear();
        codeInput.sendKeys(newCode);
        driver.findElement(updateButton).click();
    }
    public void editMajorName(String newName) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement nameInput = wait.until(
                ExpectedConditions.visibilityOfElementLocated(majorName));

        nameInput.clear();
        nameInput.sendKeys(newName);
        driver.findElement(updateButton).click();
    }
    public boolean verifyMajorNameContains(String expectedName) {

        int total = getTotalMajors();

        for (int i = 1; i <= total; i++) {

            String actualName = getCell(i, getColumnIndex("TÊN CHUYÊN NGÀNH"))
                    .getText()
                    .trim();

            if (actualName.equals(expectedName)) {
                return true;
            }
        }

        return false;
    }
    public void deleteMajor(String majorCode) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        By actionBtnLocator = By.xpath("//tr[.//td[contains(.,'" + majorCode + "')]]//button");

        WebElement actionBtn = wait.until(
                ExpectedConditions.elementToBeClickable(actionBtnLocator)
        );

        actionBtn.click();

        WebElement deleteBtn = wait.until(
                ExpectedConditions.visibilityOfElementLocated(deleteButton)
        );

        deleteBtn.click();

        WebElement confirmBtn = wait.until(
                ExpectedConditions.elementToBeClickable(confirmYesButton)
        );

        confirmBtn.click();

    }
}
