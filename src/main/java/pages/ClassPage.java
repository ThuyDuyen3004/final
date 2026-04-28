package pages;

import models.ClassItem;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ClassPage extends BasePage {

    public ClassPage(WebDriver driver) {
        super(driver);
    }

    // ================= MENU =================

    public void goToSettingPage() {
        clickMenu("Cài đặt");
    }

    public void goToClassManagePage() {
        goToSettingPage();
        clickMenu("Quản lý lớp học");
    }

    // ================= LOCATORS =================

    private final By addButton = By.xpath("//button[contains(text(),'Thêm')]");
    private final By searchInput = By.xpath("//input[@placeholder='Nhập tên lớp...']");
    private final By saveButton = By.xpath("//button[contains(text(),'Lưu')]");
    private final By classInput = By.xpath("//input[@placeholder='Tên lớp']");
    private final By tableRows = By.xpath("//tbody/tr");
    private final By noDataMessage = By.xpath("//div[contains(@class,'text-sm')]");

    private final By confirmYesButton = By.xpath("//button[.='Có']");
    private final By errorMessage = By.xpath("//p[contains(@class,'red')]");

    private static final String OPTION_XPATH =
            "//div[@role='option' and normalize-space(.)='%s']";

    private static final List<String> COLUMN_NAMES = List.of(
            "STT",
            "TÊN LỚP",
            "KHÓA",
            "CHUYÊN NGÀNH",
            "GIÁO VIÊN PHỤ TRÁCH",
            "SỐ LƯỢNG"
    );

    // ================= COMMON =================

    public void openAddClassForm() {
        wait.until(ExpectedConditions.elementToBeClickable(addButton)).click();
    }

    private void openSelect(String label) {
        By locator = By.xpath(
                String.format("//button[@role='combobox' and contains(.,'%s')]", label)
        );
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    private void selectOption(String value) {
        By option = By.xpath(String.format(OPTION_XPATH, value));
        wait.until(ExpectedConditions.elementToBeClickable(option)).click();
    }

    private int getColumnIndex(String columnName) {
        for (int i = 0; i < COLUMN_NAMES.size(); i++) {
            if (COLUMN_NAMES.get(i).equalsIgnoreCase(columnName)) {
                return i + 1;
            }
        }
        throw new RuntimeException("Column not found: " + columnName);
    }

    private WebElement getCell(int row, int col) {
        return driver.findElement(By.xpath(
                String.format("//tbody/tr[%d]/td[%d]", row, col)
        ));
    }

    public int getTotalClasses() {
        return driver.findElements(tableRows).size();
    }

    // ================= ADD CLASS =================

    public void addClass(String khoa, String majorName, String teacherName, String className) {
        openAddClassForm();

        openSelect("Khóa");
        selectOption(khoa);

        openSelect("Chuyên ngành");
        selectOption(majorName);

        openSelect("Giáo viên phụ trách");
        selectOption(teacherName);

        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(classInput));
        input.clear();
        input.sendKeys(className);

        wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
    }
    public void addClassWithNoCohort(String majorName, String teacherName, String className) {
        openAddClassForm();

        openSelect("Chuyên ngành");
        selectOption(majorName);

        openSelect("Giáo viên phụ trách");
        selectOption(teacherName);

        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(classInput));
        input.clear();
        input.sendKeys(className);

        wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
    }

    public String getMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage))
                .getText().trim();
    }
    public void addClassWithNoMajor(String khoa,String teacherName, String className) {
        openAddClassForm();

        openSelect("Khóa");
        selectOption(khoa);

        openSelect("Giáo viên phụ trách");
        selectOption(teacherName);

        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(classInput));
        input.clear();
        input.sendKeys(className);

        wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
    }
    public void addClassWithNoTeacher(String khoa, String majorName, String className) {
        openAddClassForm();

        openSelect("Khóa");
        selectOption(khoa);

        openSelect("Chuyên ngành");
        selectOption(majorName);

        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(classInput));
        input.clear();
        input.sendKeys(className);

        wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
    }
    public void addClassWithNoClass(String khoa, String majorName, String teacherName) {
        openAddClassForm();

        openSelect("Khóa");
        selectOption(khoa);

        openSelect("Chuyên ngành");
        selectOption(majorName);

        openSelect("Giáo viên phụ trách");
        selectOption(teacherName);

        wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
    }

    // ================= SEARCH =================

    // ================= SEARCH =================

    public void searchClassName(String keyword) {

        WebElement input = wait.until(
                ExpectedConditions.elementToBeClickable(searchInput)
        );

        input.clear();
        input.sendKeys(keyword);
        input.sendKeys(Keys.ENTER);
    }

    public boolean isNoDataDisplayed() {
        List<WebElement> els = driver.findElements(noDataMessage);
        return !els.isEmpty() && els.get(0).isDisplayed();
    }

    public boolean verifySearchResultContainsKeyword(String keyword) {

        int total = getTotalClasses();
        if (total == 0) return false;

        String key = keyword.toLowerCase();

        for (int i = 1; i <= total; i++) {

            String name = getCell(i, getColumnIndex("TÊN LỚP"))
                    .getText()
                    .trim()
                    .toLowerCase();

            if (!name.contains(key)) {
                return false;
            }
        }

        return true;
    }
    // ================= DATA =================

    public String getRandomClassName() {
        wait.until(driver -> getTotalClasses() > 0);

        int total = getTotalClasses();
        int row = new Random().nextInt(total) + 1;

        return getCell(row, getColumnIndex("TÊN LỚP"))
                .getText()
                .trim();
    }

    public int getQuantityByClassName(String className) {

        int total = getTotalClasses();

        for (int i = 1; i <= total; i++) {

            String name = getCell(i, getColumnIndex("TÊN LỚP"))
                    .getText()
                    .trim();

            if (name.equals(className)) {

                String raw = getCell(i, getColumnIndex("SỐ LƯỢNG"))
                        .getText()
                        .replaceAll("[^0-9]", "");

                return raw.isEmpty() ? 0 : Integer.parseInt(raw);
            }
        }

        return 0;
    }

    public String getClassNameHavingQuantityEqual(int value) {

        wait.until(driver -> getTotalClasses() > 0);

        int total = getTotalClasses();

        for (int i = 1; i <= total; i++) {

            String raw = getCell(i, getColumnIndex("SỐ LƯỢNG"))
                    .getText()
                    .replaceAll("[^0-9]", "");

            if (!raw.isEmpty() && Integer.parseInt(raw) == value) {

                return getCell(i, getColumnIndex("TÊN LỚP"))
                        .getText()
                        .trim();
            }
        }

        return null;
    }

    // ================= DELETE =================

    public void deleteClass(String className) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        int total = getTotalClasses();

        for (int i = 1; i <= total; i++) {

            WebElement cell = getCell(i, getColumnIndex("TÊN LỚP"));

            String name = cell.getText().trim();

            if (name.equals(className)) {

                // click action button
                WebElement actionBtn = wait.until(
                        ExpectedConditions.elementToBeClickable(
                                By.xpath("//tr[" + i + "]//td[last()]//button")
                        )
                );
                actionBtn.click();

                // wait menu visible
                WebElement deleteOption = wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                By.xpath("//div[@role='menuitem' and contains(.,'Xóa')]")
                        )
                );
                deleteOption.click();

                WebElement yesBtn = wait.until(
                        ExpectedConditions.elementToBeClickable(confirmYesButton)
                );
                yesBtn.click();

                wait.until(ExpectedConditions.stalenessOf(cell));

                return;
            }
        }
    }

    // ================= OTHER =================

    public String getNoDataMessageText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(noDataMessage))
                .getText().trim();
    }

    public void waitForTableLoaded() {
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(tableRows));
    }

    // ================= GET ALL =================

    public ArrayList<ClassItem> getAllClasses() {

        wait.until(driver -> getTotalClasses() > 0);

        ArrayList<ClassItem> list = new ArrayList<>();
        int total = getTotalClasses();

        for (int i = 1; i <= total; i++) {

            String className = getCell(i, getColumnIndex("TÊN LỚP")).getText().trim();
            String cohort = getCell(i, getColumnIndex("KHÓA")).getText().trim();
            String major = getCell(i, getColumnIndex("CHUYÊN NGÀNH")).getText().trim();
            String teacher = getCell(i, getColumnIndex("GIÁO VIÊN PHỤ TRÁCH")).getText().trim();

            list.add(new ClassItem(className, cohort, major, teacher));
        }

        return list;
    }
}