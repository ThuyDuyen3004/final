
package pages;

import models.Setting.ClassItem;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ClassPage extends BasePage {

    public ClassPage(WebDriver driver) {
        super(driver);
    }

    /* ================= NAVIGATION ================= */

    public void goToSettingPage() {
        clickMenu("Cài đặt");
    }

    public void goToUserManagePage() {
        goToSettingPage();
        clickMenu("Quản lý lớp học");
    }

    /* ================= LOCATORS ================= */

    private final By addButton = By.xpath("//button[contains(text(),'Thêm')]");
    private final By classInput = By.xpath("//div[@class='space-y-2']/input");
    private final By majorDropdown = By.xpath("//button[@role='combobox' and contains(.,'Chuyên ngành')]");
    private final By teacherDropdown = By.xpath("//button[@role='combobox' and contains(.,'Giáo viên phụ trách')]");
    private final By saveButton = By.xpath("//button[contains(text(),'Lưu')]");
    private final By searchInput = By.xpath("//input[@data-slot='input']");
    private final By errorMessage = By.xpath("//p[contains(@class,'text-sm text-red')]");
    private final By deleteButton = By.xpath("//div[.='Xóa']");
    private final By confirmYesButton = By.xpath("//button[.='Có']");
    private final By noDataMessage = By.xpath("//div[contains(@class,'text-sm')]");
    private final By tableRows = By.xpath("//tbody/tr");

    private static final String OPTION_XPATH =
            "//div[@role='option' and normalize-space(.)='%s']";

    private static final List<String> COLUMN_NAMES = List.of(
            "STT",
            "TÊN LỚP",
            "CHUYÊN NGÀNH",
            "GIÁO VIÊN PHỤ TRÁCH",
            "SỐ LƯỢNG"
    );

    /* ================= ADD CLASS ================= */

    public void openAddClassForm() {
        wait.until(ExpectedConditions.elementToBeClickable(addButton)).click();
    }

    public void selectMajor(String majorName) {
        wait.until(ExpectedConditions.elementToBeClickable(majorDropdown)).click();
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath(String.format(OPTION_XPATH, majorName))
        )).click();
    }

    public void selectTeacher(String teacherName) {
        wait.until(ExpectedConditions.elementToBeClickable(teacherDropdown)).click();
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath(String.format(OPTION_XPATH, teacherName))
        )).click();
    }

    public void addClass(String className, String majorName, String teacherName) {
        openAddClassForm();

        if (className != null && !className.isBlank()) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(classInput))
                    .sendKeys(className);
        }
        if (majorName != null && !majorName.isBlank()) {
            selectMajor(majorName);
        }
        if (teacherName != null && !teacherName.isBlank()) {
            selectTeacher(teacherName);
        }

        wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
    }

    public String getMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage))
                .getText().trim();
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

    public int getTotalClasses() {
        return driver.findElements(tableRows).size();
    }

    public ArrayList<String> getAllClassNames() {
        ArrayList<String> classNames = new ArrayList<>();
        int totalRows = getTotalClasses();

        for (int i = 1; i <= totalRows; i++) {
            classNames.add(
                    getCell(i, getColumnIndex("TÊN LỚP")).getText().trim()
            );
        }
        return classNames;
    }

    public String getRandomClassName() {
        ArrayList<String> classNames = getAllClassNames();
        if (classNames.isEmpty()) {
            throw new RuntimeException("Class table is empty");
        }
        return classNames.get(new Random().nextInt(classNames.size()));
    }

    public ClassItem getRandomClass() {
        ArrayList<ClassItem> classes = new ArrayList<>();
        int totalRows = getTotalClasses();

        for (int i = 1; i <= totalRows; i++) {
            classes.add(
                    new ClassItem(
                            getCell(i, getColumnIndex("TÊN LỚP")).getText().trim()
                    )
            );
        }

        if (classes.isEmpty()) {
            throw new RuntimeException("Class table is empty");
        }

        return classes.get(new Random().nextInt(classes.size()));
    }

    /* ================= SEARCH ================= */

    public void searchClassName(String className) {
        WebElement searchBox = wait.until(
                ExpectedConditions.visibilityOfElementLocated(searchInput)
        );
        searchBox.clear();
        searchBox.sendKeys(className);
    }

    public boolean verifySearchResultContainsKeyword(String keyword) {
        int totalRows = getTotalClasses();

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

        String deletedClassName = row.findElement(
                By.xpath(".//td[" + getColumnIndex("TÊN LỚP") + "]")
        ).getText().trim();

        WebElement dropdownIcon = row.findElement(
                By.xpath("//td//button[@data-slot='dropdown-menu-trigger']")
        );

        new Actions(driver).moveToElement(dropdownIcon).click().perform();
        wait.until(ExpectedConditions.elementToBeClickable(deleteButton)).click();
        wait.until(ExpectedConditions.elementToBeClickable(confirmYesButton)).click();

        return deletedClassName;
    }

    /* ================= EMPTY STATE ================= */

    public String getNoDataMessageText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(noDataMessage))
                .getText().trim();
    }
}
