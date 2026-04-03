
package pages;

import com.github.javafaker.Faker;
import models.Setting.UserItem;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UserPage extends BasePage {

    public UserPage(WebDriver driver) {
        super(driver);
    }

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
    private final By classDropdownLocator = By.xpath("//label[contains(text(),'Gán lớp')]/following-sibling::div");

    private final By saveButton =
            By.xpath("//button[contains(text(),'Lưu')]");

    private final By messageLocator =
            By.xpath("//p[contains(@class,'text-xs text-red')]");

    private final By deleteButton =
            By.xpath("//div[.='Xóa']");

    private final By UpdateIcon =
            By.xpath("//div[.='Sửa']");

    private final By yesOption =
            By.xpath("//button[.='Có']");

    private final By searchBarLocator =
            By.xpath("//input[@data-slot='input']");

    private final By noDataMessage =
            By.xpath("//div[contains(@class,'flex')]/span");

    private final By tableRows =
            By.xpath("//tbody/tr");

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
            WebElement checkbox =
                    checkboxes.get(new Random().nextInt(checkboxes.size()));

            if (!"true".equals(checkbox.getAttribute("aria-checked"))) {
                checkbox.click();
            }
        }

        fillInput(emailLocator, email);
        fillInput(fullnameLocator, fullName);
        fillInput(passwordLocator, password);
        fillInput(confirmPasswordLocator, confirmPassword);

        WebElement saveBtn = wait.until(
                ExpectedConditions.presenceOfElementLocated(saveButton)
        );

        new Actions(driver).scrollToElement(saveBtn).perform();
        wait.until(ExpectedConditions.elementToBeClickable(saveBtn)).click();
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

    Faker faker = new Faker(new java.util.Locale("vi"));

    public String randomRoleName() {
        String[] roles = {
                "Ban chủ nhiệm khoa",
                "Giáo viên chủ nhiệm",
                "Giáo vụ khoa"
        };
        return roles[new Random().nextInt(roles.length)];
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

    /* ================= DELETE USER ================= */

    public String randomClickIconAndDelete() {

        List<WebElement> rows = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(tableRows)
        );

        WebElement row = rows.get(new Random().nextInt(rows.size()));

        String deletedEmail = row.findElement(
                By.xpath(".//td[" + getColumnIndex("Email") + "]")
        ).getText().trim();

        WebElement dropdownIcon = row.findElement(
                By.xpath(".//button[@data-slot='dropdown-menu-trigger']")
        );

        new Actions(driver).moveToElement(dropdownIcon).perform();
        dropdownIcon.click();

        wait.until(ExpectedConditions.elementToBeClickable(deleteButton)).click();
        wait.until(ExpectedConditions.elementToBeClickable(yesOption)).click();

        return deletedEmail;
    }

    /* ================= UPDATE USER ================= */

    public String openEditForm() {

        List<WebElement> rows = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(tableRows)
        );

        WebElement row = rows.get(new Random().nextInt(rows.size()));

        String email = row.findElement(
                By.xpath(".//td[" + getColumnIndex("Email") + "]")
        ).getText().trim();

        WebElement dropdownIcon = row.findElement(
                By.xpath(".//button[@data-slot='dropdown-menu-trigger']")
        );

        new Actions(driver).moveToElement(dropdownIcon).perform();
        dropdownIcon.click();

        // click Update / Edit
        By updateButton = By.xpath("//div[.='Cập nhật' or .='Chỉnh sửa']");
        wait.until(ExpectedConditions.elementToBeClickable(updateButton)).click();

        return email;
    }


    /* ================= SEARCH ================= */

    public void searchUser(String keyword) {
        WebElement searchBox = wait.until(
                ExpectedConditions.visibilityOfElementLocated(searchBarLocator)
        );
        searchBox.clear();
        searchBox.sendKeys(keyword);
    }

    public boolean verifySearchResultContainsKeyword(String keyword) {
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(tableRows));

        for (int i = 1; i <= getTotalUsers(); i++) {
            String username = getCell(i, getColumnIndex("Tên đăng nhập"))
                    .getText().toLowerCase();

            String email = getCell(i, getColumnIndex("Email"))
                    .getText().toLowerCase();

            if (username.contains(keyword.toLowerCase())
                    || email.contains(keyword.toLowerCase())) {
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
            "Tên đăng nhập",
            "Email",
            "Trạng thái"
    );

    private int getColumnIndex(String columnName) {
        return COLUMN_NAMES.indexOf(columnName) + 1;
    }

    private WebElement getCell(int row, int column) {
        String xpath = String.format("//table/tbody/tr[%d]/td[%d]", row, column);
        return driver.findElement(By.xpath(xpath));
    }

    public int getTotalUsers() {
        return driver.findElements(tableRows).size();
    }

    public ArrayList<UserItem> getAllUsers() {
        ArrayList<UserItem> users = new ArrayList<>();

        for (int i = 1; i <= getTotalUsers(); i++) {
            String username = getCell(i, getColumnIndex("Tên đăng nhập")).getText().trim();
            String email = getCell(i, getColumnIndex("Email")).getText().trim();
            users.add(new UserItem(username, email));
        }
        return users;
    }

    public UserItem getRandomUser() {
        ArrayList<UserItem> users = getAllUsers();
        if (users.isEmpty()) {
            throw new RuntimeException("User table is empty");
        }
        return users.get(new Random().nextInt(users.size()));
    }
    /* ================= UPDATE USER ================= */

    public void updateUser(String roleName,
                           String email,
                           String fullName,
                           String password,
                           String confirmPassword) {

        // Update role
        if (roleName != null && !roleName.isBlank()) {
            wait.until(ExpectedConditions.elementToBeClickable(roleDropdownLocator)).click();
            By roleOption = By.xpath(String.format(ROLE_OPTION_XPATH, roleName));
            wait.until(ExpectedConditions.elementToBeClickable(roleOption)).click();

            // Nếu là GVCN thì gán lớp + tick checkbox
            if ("Giáo viên chủ nhiệm".equals(roleName)) {
                wait.until(ExpectedConditions.elementToBeClickable(classDropdownLocator)).click();

                List<WebElement> checkboxes = wait.until(
                        ExpectedConditions.visibilityOfAllElementsLocatedBy(classCheckboxButton)
                );

                WebElement checkbox =
                        checkboxes.get(new Random().nextInt(checkboxes.size()));

                if (!"true".equals(checkbox.getAttribute("aria-checked"))) {
                    checkbox.click();
                }
            }
        }

        // Update email
        if (email != null && !email.isBlank()) {
            fillInput(emailLocator, email);
        }

        // Update full name
        if (fullName != null && !fullName.isBlank()) {
            fillInput(fullnameLocator, fullName);
        }

        // Update password
        if (password != null && !password.isBlank()) {
            fillInput(passwordLocator, password);
        }

        // Update confirm password
        if (confirmPassword != null && !confirmPassword.isBlank()) {
            fillInput(confirmPasswordLocator, confirmPassword);
        }

        // Click Save
        WebElement saveBtn = wait.until(
                ExpectedConditions.presenceOfElementLocated(saveButton)
        );
        new Actions(driver).scrollToElement(saveBtn).perform();
        wait.until(ExpectedConditions.elementToBeClickable(saveBtn)).click();
    }

}
