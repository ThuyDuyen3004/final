package pages;

import com.github.javafaker.Faker;
import models.UserItem;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UserPage extends BasePage{

    public UserPage(WebDriver driver) {
        super(driver);
    }

    private final Random random = new Random();
    Faker faker = new Faker(new java.util.Locale("vi"));

    /* ================= NAVIGATION ================= */

    public void goToSettingPage() {
        clickMenu("Cài đặt");
    }

    public void goToUserManagePage() {
        goToSettingPage();
        clickMenu("Quản lý người dùng");
    }

    /* ================= LOCATORS ================= */

    private final By addButtonLocator =
            By.xpath("//button[contains(text(),'Thêm')]");

    private final By roleDropdownLocator =
            By.xpath("//button[@role='combobox']");

    private static final String ROLE_OPTION_XPATH =
            "//div[@role='option' and normalize-space(.)='%s']";

    private final By classCheckboxButton =
            By.xpath("//button[@role='checkbox']");

    private final By emailLocator = By.xpath("//div[3]//input[1]");
    private final By fullnameLocator = By.xpath("(//input)[3]");
    private final By passwordLocator = By.xpath("//div[5]//input[1]");
    private final By confirmPasswordLocator = By.xpath("//div[6]//input[1]");
    private final By classDropdownLocator =
            By.xpath("//label[contains(text(),'Gán lớp')]/following-sibling::div");

    private final By saveButton =
            By.xpath("//button[contains(text(),'Lưu')]");

    private final By messageLocator =
            By.xpath("//p[contains(@class,'text-xs text-red')]");

    private final By deleteButton =
            By.xpath("//div[.='Xóa']");

    private final By updateButton =
            By.xpath("//div[.='Sửa']");

    private final By yesOption =
            By.xpath("//button[.='Có']");

    private final By searchBarLocator =
            By.xpath("//input[@placeholder='Nhập họ tên...']");

    private final By noDataMessage =
            By.xpath("//div[contains(@class,'flex')]/span");

    private final By tableRows =
            By.xpath("//tbody/tr");

    /* ================= SAFE TABLE WAIT ================= */

    public void waitForTableLoaded() {

        wait.until(d -> {
            List<WebElement> rows = d.findElements(tableRows);
            List<WebElement> empty = d.findElements(noDataMessage);

            return rows.size() > 0 || empty.size() > 0;
        });
    }
    /* ================= ADD USER ================= */

    public void openAddUserForm() {
        wait.until(ExpectedConditions.elementToBeClickable(addButtonLocator)).click();
    }

    public void addUser(String roleName,
                        String email,
                        String fullName,
                        String password,
                        String confirmPassword) {

        wait.until(ExpectedConditions.elementToBeClickable(roleDropdownLocator)).click();

        By roleOption = By.xpath(String.format(ROLE_OPTION_XPATH, roleName));
        wait.until(ExpectedConditions.elementToBeClickable(roleOption)).click();

        if ("Giáo viên chủ nhiệm".equals(roleName)) {
            wait.until(ExpectedConditions.elementToBeClickable(classDropdownLocator)).click();

            List<WebElement> checkboxes = wait.until(
                    ExpectedConditions.visibilityOfAllElementsLocatedBy(classCheckboxButton)
            );

            WebElement checkbox = checkboxes.get(random.nextInt(checkboxes.size()));

            if (!"true".equals(checkbox.getAttribute("aria-checked"))) {
                checkbox.click();
            }
        }

        fillInput(emailLocator, email);
        fillInput(fullnameLocator, fullName);
        fillInput(passwordLocator, password);
        fillInput(confirmPasswordLocator, confirmPassword);

        WebElement saveBtn = wait.until(
                ExpectedConditions.elementToBeClickable(saveButton)
        );

        new Actions(driver).scrollToElement(saveBtn).perform();
        saveBtn.click();
    }

    private void fillInput(By locator, String value) {
        WebElement input = wait.until(
                ExpectedConditions.visibilityOfElementLocated(locator)
        );
        input.clear();
        if (value != null && !value.isBlank()) {
            input.sendKeys(value);
        }
    }

    /* ================= RANDOM DATA ================= */

    public String randomRoleName() {
        String[] roles = {
                "Ban chủ nhiệm khoa",
                "Giáo viên chủ nhiệm",
                "Giáo vụ khoa"
        };
        return roles[random.nextInt(roles.length)];
    }

    public String randomEmail() {
        return "test" + System.currentTimeMillis() + "@gmail.com";
    }

    public String randomFullName() {
        return faker.name().fullName();
    }

    public String randomPassword() {
        return faker.number().digits(6);
    }

    public String generateNonExistingKeyword() {
        return "not_exist_" + System.currentTimeMillis();
    }

    /* ================= DELETE ================= */

    public String randomClickIconAndDelete() {

        waitForTableLoaded();

        List<WebElement> rows = driver.findElements(tableRows);

        if (rows.isEmpty()) {
            throw new RuntimeException("Table is empty - cannot delete");
        }

        WebElement row = rows.get(random.nextInt(rows.size()));

        String deletedEmail = row.findElement(
                By.xpath(".//td[" + getColumnIndex("EMAIL") + "]")
        ).getText().trim();

        WebElement dropdownIcon = row.findElement(
                By.xpath(".//button[@data-slot='dropdown-menu-trigger']")
        );

        new Actions(driver).moveToElement(dropdownIcon).click().perform();

        wait.until(ExpectedConditions.elementToBeClickable(deleteButton)).click();
        wait.until(ExpectedConditions.elementToBeClickable(yesOption)).click();

        return deletedEmail;
    }

    /* ================= UPDATE ================= */

    public String openEditForm() {

        waitForTableLoaded();

        wait.until(driver -> !driver.findElements(tableRows).isEmpty());

        List<WebElement> rows = wait.until(
                driver -> driver.findElements(tableRows)
        );

        if (rows.isEmpty()) {
            throw new RuntimeException("Table is empty - cannot edit");
        }

        WebElement row = rows.get(random.nextInt(rows.size()));

        String email = row.findElement(
                By.xpath(".//td[" + getColumnIndex("EMAIL") + "]")
        ).getText().trim();

        WebElement dropdownIcon = row.findElement(
                By.xpath(".//button[@data-slot='dropdown-menu-trigger']")
        );

        wait.until(ExpectedConditions.elementToBeClickable(dropdownIcon));

        new Actions(driver)
                .moveToElement(dropdownIcon)
                .click()
                .perform();

        wait.until(ExpectedConditions.elementToBeClickable(updateButton)).click();

        return email;
    }
    public void updateUser(String roleName,
                           String email,
                           String fullName,
                           String password,
                           String confirmPassword) {

        if (roleName != null && !roleName.isBlank()) {
            wait.until(ExpectedConditions.elementToBeClickable(roleDropdownLocator)).click();

            By roleOption = By.xpath(String.format(ROLE_OPTION_XPATH, roleName));
            wait.until(ExpectedConditions.elementToBeClickable(roleOption)).click();

            if ("Giáo viên chủ nhiệm".equals(roleName)) {
                wait.until(ExpectedConditions.elementToBeClickable(classDropdownLocator)).click();

                List<WebElement> checkboxes = wait.until(
                        ExpectedConditions.visibilityOfAllElementsLocatedBy(classCheckboxButton)
                );

                WebElement checkbox = checkboxes.get(random.nextInt(checkboxes.size()));

                if (!"true".equals(checkbox.getAttribute("aria-checked"))) {
                    checkbox.click();
                }
                driver.findElement(saveButton).click();
            }
        }

        if (email != null && !email.isBlank()) fillInput(emailLocator, email);
        if (fullName != null && !fullName.isBlank()) fillInput(fullnameLocator, fullName);
        if (password != null && !password.isBlank()) fillInput(passwordLocator, password);
        if (confirmPassword != null && !confirmPassword.isBlank()) {
            fillInput(confirmPasswordLocator, confirmPassword);
        }

        WebElement saveBtn = wait.until(
                ExpectedConditions.elementToBeClickable(saveButton)
        );

        new Actions(driver).scrollToElement(saveBtn).perform();
        saveBtn.click();
    }

//    /* ================= SEARCH ================= */
//
    public void searchUser(String keyword) {
        WebElement searchBox = wait.until(
                ExpectedConditions.visibilityOfElementLocated(searchBarLocator)
        );
        searchBox.clear();
        searchBox.sendKeys(keyword);
    }
    public void resetSearch() {
        WebElement input = wait.until(
                ExpectedConditions.elementToBeClickable(searchBarLocator)
        );

        input.clear();
        input.sendKeys(Keys.ENTER);

        waitForTableLoaded();
    }

    public boolean verifyNoDataMessageDisplayed() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(noDataMessage)
        ).getText().trim().equals("Không có dữ liệu");
    }

    public boolean isErrorMessageDisplayed() {
        return !driver.findElements(messageLocator).isEmpty();
    }

    public String getMessage() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(messageLocator)
        ).getText().trim();
    }

    /* ================= TABLE ================= */

    private static final List<String> COLUMN_NAMES = List.of(
            "STT",
            "HỌ TÊN",
            "EMAIL",
            "VAI TRÒ",
            "Trạng thái"
    );

    private int getColumnIndex(String columnName) {
        return COLUMN_NAMES.indexOf(columnName) + 1;
    }


    public int getTotalUsers() {
        waitForTableLoaded();
        return driver.findElements(tableRows).size();
    }
    private WebElement getCell(int row, int column) {

        wait.until(d -> d.findElements(tableRows).size() > 0);

        return wait.until(driver -> {
            List<WebElement> rows = driver.findElements(tableRows);

            if (rows.size() < row) return null;

            try {
                return rows.get(row - 1)
                        .findElement(By.xpath("./td[" + column + "]"));
            } catch (StaleElementReferenceException e) {
                return null;
            } catch (NoSuchElementException e) {
                return null;
            }
        });
    }
    public ArrayList<UserItem> getAllUsers() {

        waitForTableLoaded();

        ArrayList<UserItem> users = new ArrayList<>();

        int total = getTotalUsers();

        for (int i = 1; i <= total; i++) {

            String fullName = getCell(i, getColumnIndex("HỌ TÊN")).getText().trim();
            String email = getCell(i, getColumnIndex("EMAIL")).getText().trim();
            String roleName = getCell(i, getColumnIndex("VAI TRÒ")).getText().trim();

            users.add(new UserItem(fullName, email, roleName, null));
        }

        return users;
    }

    public UserItem getRandomUser() {

        waitForTableLoaded();

        wait.until(driver -> !getAllUsers().isEmpty());

        ArrayList<UserItem> users = getAllUsers();

        if (users.isEmpty()) {
            throw new RuntimeException("User table is empty");
        }

        return users.get(random.nextInt(users.size()));
    }

    public boolean verifySearchResultContainsKeyword(String keyword) {

        waitForTableLoaded();

        String lowerKeyword = keyword.toLowerCase();

        for (int i = 1; i <= getTotalUsers(); i++) {

            String username = getCell(i, getColumnIndex("HỌ TÊN"))
                    .getText().toLowerCase();

            String email = getCell(i, getColumnIndex("EMAIL"))
                    .getText().toLowerCase();

            if (username.contains(lowerKeyword) || email.contains(lowerKeyword)) {
                return true;
            }
        }

        return false;
    }
}