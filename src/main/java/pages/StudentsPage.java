package pages;

import com.github.javafaker.Faker;
import models.StudentItem;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StudentsPage extends BasePage {
    String createdMSSV;

    public StudentsPage(WebDriver driver) {
        super(driver);
    }

    /* ================= NAVIGATION ================= */

    public void goDataManagementPage() {
        clickMenu("Quản lý dữ liệu");
    }

    public void goToStudentsPage() {
        goDataManagementPage();
        clickMenu("Sinh viên");
    }

    /* ================= LOCATORS ================= */

    private final By addButtonLocator =
            By.xpath("//button[contains(text(),'Thêm')]");

    private final By studentIDInputLocator = By.id("mssv");
    private final By fullNameInputLocator = By.id("hoTen");
    private final By classInputLocator = By.id("lop");
    private final By birthdayInputLocator = By.id("ngaySinh");
    private final By noteInputLocator = By.id("ghiChu");
    private final By tableRows = By.xpath("//table/tbody/tr");
    private final By mgsLocator = By.xpath("//div[@class='text-red-600 text-sm px-6']");
    private final By deleteButton =
            By.xpath("//div[.=' Xóa']");

    private final By editButton =
            By.xpath("//div[.=' Sửa']");

    private final By yesOption =
            By.xpath("//button[.='Có']");


    private final By saveButton =
            By.xpath("//button[contains(text(),'Lưu')]");

    private String classOptionLocator =
            "//div[contains(@class,'absolute')]//button[normalize-space()='%s']";

    private By searchInput = By.xpath("//input[@placeholder='Nhập MSSV...']");
    private By importErrorMessage = By.xpath("//div[@class='pt-0 pb-4']/descendant::span");

    /* ================= ACTIONS ================= */

    public void enterStudentID(String mssv) {

        WebElement idInput = driver.findElement(studentIDInputLocator);

        idInput.clear();
        idInput.sendKeys(mssv);
    }

    public void enterFullName(String name) {

        WebElement nameInput = driver.findElement(fullNameInputLocator);

        nameInput.clear();
        nameInput.sendKeys(name);
    }

    public void enterBirthday(String birthday) {

        WebElement birthdayInput = driver.findElement(birthdayInputLocator);

        birthdayInput.clear();
        birthdayInput.sendKeys(birthday);
    }

    public void enterNote(String note) {
        driver.findElement(noteInputLocator).sendKeys(note);
    }

    public void clickSave() {
        driver.findElement(saveButton).click();
    }

    /* ================= RANDOM CLASS ================= */
    public void selectClass(String className) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // 1. Xóa class cũ nếu có
        List<WebElement> removeButtons = driver.findElements(
                By.xpath("//button[@class='hover:text-blue-900']")
        );

        if (!removeButtons.isEmpty()) {
            removeButtons.get(0).click();
        }

        // 2. Click vào input lớp
        WebElement classInput = wait.until(
                ExpectedConditions.elementToBeClickable(classInputLocator)
        );
        classInput.click();

        // 3. Chọn lớp mới
        By optionLocator = By.xpath(String.format(classOptionLocator, className));

        WebElement option = wait.until(
                ExpectedConditions.elementToBeClickable(optionLocator)
        );

        option.click();
    }

    public void addStudent(String mssv, String name, String className, String birthday, String note) {

        if (mssv != null && !mssv.isEmpty()) {
            enterStudentID(mssv);
        }

        if (name != null && !name.isEmpty()) {
            enterFullName(name);
        }

        if (className != null && !className.isEmpty()) {
            selectClass(className);
        }

        if (birthday != null && !birthday.isEmpty()) {
            enterBirthday(birthday);
        }

        if (note != null && !note.isEmpty()) {
            enterNote(note);
        }

        clickSave();
    }

    private Faker faker = new Faker();

    public String randomMSSV() {
        return faker.number().digits(6);
    }

    public String randomFullName() {
        return faker.name().fullName();
    }
    /* ================= ADD STUDENT ================= */

    public void openAddStudentForm() {

        wait.until(
                ExpectedConditions.elementToBeClickable(addButtonLocator)
        ).click();
    }
    /* ================= TABLE UTILS ================= */

    private static final List<String> COLUMN_NAMES = List.of(
            "STT",
            "MSSV",
            "HỌ VÀ TÊN",
            "LỚP",
            "NGÀY SINH",
            "GHI CHÚ"
    );

    private int getColumnIndex(String columnName) {
        return COLUMN_NAMES.indexOf(columnName) + 1;
    }

    private WebElement getCell(int row, int column) {
        String xpath = String.format("//table/tbody/tr[%d]/td[%d]", row, column);
        return driver.findElement(By.xpath(xpath));
    }

    public int getTotalStudents() {
        return driver.findElements(tableRows).size();
    }

    public ArrayList<StudentItem> getAllStudents() {

        ArrayList<StudentItem> students = new ArrayList<>();

        for (int i = 1; i <= getTotalStudents(); i++) {

            String mssv = getCell(i, getColumnIndex("MSSV")).getText().trim();
            String fullName = getCell(i, getColumnIndex("HỌ VÀ TÊN")).getText().trim();

            students.add(new StudentItem(mssv, fullName));
        }

        return students;
    }

    public StudentItem getRandomStudent() {

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(tableRows)
        );

        ArrayList<StudentItem> students = getAllStudents();

        if (students.isEmpty()) {
            throw new RuntimeException("Student table is empty");
        }

        return students.get(new Random().nextInt(students.size()));
    }

    public void filterByCourse(String course) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // mở dropdown khóa
        WebElement courseDropdown = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[.//span[text()='Khóa']]")
                )
        );

        courseDropdown.click();

        // chọn option
        WebElement option = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//div[@role='option'][text()='" + course + "']")
                )
        );

        option.click();
    }

    public void filterByClass(String className) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement classDropdown = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[@role='combobox'][2]")
                )
        );

        classDropdown.click();

        // chờ dropdown option render xong (ổn định hơn visibility)
        By optionLocator = By.xpath("//div[@role='option'][.='" + className + "']");

        wait.until(
                ExpectedConditions.presenceOfElementLocated(optionLocator)
        );

        WebElement option = wait.until(
                ExpectedConditions.visibilityOfElementLocated(optionLocator)
        );

        // scroll để tránh bị overlay che
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", option);

        // thêm wait click lại lần cuối cho chắc (tránh stale/overlay)
        wait.until(ExpectedConditions.elementToBeClickable(option)).click();
    }

    public void scrollToStudent(String mssv) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        WebElement studentRow = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//td[contains(.,'" + mssv + "')]")
                )
        );

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", studentRow);
    }
    public void scrollToBottom() {

        JavascriptExecutor js = (JavascriptExecutor) driver;

        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }

    public String getValidationMessage() {

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(mgsLocator)
        ).getText();
    }

    // delete
    public String randomClickIconAndDeleteStudent() {

        List<WebElement> rows = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(tableRows)
        );

        WebElement row = rows.get(new Random().nextInt(rows.size()));

        String deletedMSSV = row.findElement(
                By.xpath(".//td[" + getColumnIndex("MSSV") + "]")
        ).getText().trim();

        WebElement dropdownIcon = row.findElement(
                By.xpath(".//button[@data-slot='dropdown-menu-trigger']")
        );

        new Actions(driver).moveToElement(dropdownIcon).perform();
        dropdownIcon.click();

        wait.until(ExpectedConditions.elementToBeClickable(deleteButton)).click();
        wait.until(ExpectedConditions.elementToBeClickable(yesOption)).click();

        return deletedMSSV;
    }
    // edit
    public String randomClickIconAndEditStudent() {

        List<WebElement> rows = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(tableRows)
        );

        int randomIndex = new Random().nextInt(rows.size());

        WebElement row = rows.get(randomIndex);

        String editedMSSV = row.findElement(
                By.xpath(".//td[" + getColumnIndex("MSSV") + "]")
        ).getText().trim();

        WebElement dropdownIcon = row.findElement(
                By.xpath(".//button[@data-slot='dropdown-menu-trigger']")
        );

        dropdownIcon.click();

        WebElement editBtn = wait.until(
                ExpectedConditions.visibilityOfElementLocated(editButton)
        );

        editBtn.click();

        System.out.println("Edited MSSV: " + editedMSSV);

        return editedMSSV;
    }
    public void updateStudent(String mssv, String name, String className, String birthday, String note) {

            if (mssv != null && !mssv.isEmpty()) {
                enterStudentID(mssv);
            }

            if (name != null && !name.isEmpty()) {
                enterFullName(name);
            }

            if (className != null && !className.isEmpty()) {
                selectClass(className);
            }

            if (birthday != null && !birthday.isEmpty()) {
                enterBirthday(birthday);
            }

            if (note != null && !note.isEmpty()) {
                enterNote(note);
            }

            clickSave();
        }
    public boolean isValueDisplayedInTable(String value) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            WebElement element = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//td[normalize-space()='" + value + "']")
                    )
            );
            return element.isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }
    public void scrollToTop() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, 0);");
    }
    public String getStudentNameByMSSV(String mssv) {

        int nameColumn = getColumnIndex("HỌ VÀ TÊN");

        List<WebElement> rows = driver.findElements(
                By.xpath("//table/tbody/tr[td[normalize-space()='" + mssv + "']]")
        );

        if (rows.isEmpty()) {
            return "";
        }

        WebElement row = rows.get(0);

        String name = row.findElement(By.xpath("./td[" + nameColumn + "]"))
                .getText()
                .trim();

        return name;
    }
    public boolean isStudentDisplayed(String mssv) {

        List<WebElement> students = driver.findElements(
                By.xpath("//td[normalize-space()='" + mssv + "']")
        );

        return !students.isEmpty();
    }
    public void searchStudent(String mssv) {

        WebElement searchBox = wait.until(
                ExpectedConditions.visibilityOfElementLocated(searchInput)
        );

        searchBox.clear();
        searchBox.sendKeys(mssv);

        wait.until(
                ExpectedConditions.textToBePresentInElementValue(searchInput, mssv)
        );
    }
    public boolean verifySearchResultContainsMSSV(String mssv) {

        List<WebElement> results = driver.findElements(
                By.xpath("//td[contains(text(),'" + mssv + "')]")
        );

        return !results.isEmpty();
    }

    public String generateNonExistingKeyword() {
        return String.valueOf(System.currentTimeMillis());
    }

    public boolean isStudentTableEmpty() {

        List<WebElement> rows = driver.findElements(tableRows);

        return rows.size() == 0;
    }

    public String getImportErrorMessage() {
        return driver.findElement(importErrorMessage).getText();
    }
    private By studentRows = By.xpath("//table/tbody/tr/td[3]");
    public List<String> getStudentNames() {
        List<WebElement> elements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(studentRows));
        List<String> names = new ArrayList<>();

        for (WebElement e : elements) {
            names.add(e.getText().trim());
        }
        return names;
    }
}