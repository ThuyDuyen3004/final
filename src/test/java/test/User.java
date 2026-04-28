package test;

import com.github.javafaker.Faker;
import common.BaseTest;
import jdk.jfr.Description;
import models.UserItem;
import org.testng.annotations.Test;

import java.util.ArrayList;

public class User extends BaseTest {

    @Test(priority = 1)
    public void UMG002_VerifyUserDetailsMatchBetweenFormAndTable() throws InterruptedException {

        userPage.goToUserManagePage();
        userPage.openAddUserForm();

        String email = userPage.randomEmail();
        String fullName = userPage.randomFullName();
        String password ="123";
        String role = userPage.randomRoleName();

        ArrayList<UserItem> expectedUsers = new ArrayList<>();
        expectedUsers.add(new UserItem(fullName, email, role,null));
        UserItem createdUser = new UserItem(fullName, email, role, null);

        userPage.addUser(role, email, fullName, password, password);
        Thread.sleep(1000);

        ArrayList<UserItem> actualUsers = userPage.getAllUsers();

        boolean result = actualUsers.containsAll(expectedUsers);
        softAssert.assertTrue(result,
                "User details do not match between Add User form and User table");

        softAssert.assertAll();
    }

    @Description("user can not add new user when leaving Fullname empty")
    @Test(priority = 2)
    public void UMG_003CanNotAddUserWithFullNameEmpty() throws InterruptedException {

        userPage.goToUserManagePage();
        userPage.openAddUserForm();

        String password = userPage.randomPassword();

        userPage.addUser(userPage.randomRoleName(),
                userPage.randomEmail(),
                "",
                password,
                password
        );

        softAssert.assertEquals(userPage.getMessage(), "Vui lòng nhập họ và tên",
                "the error message is displayed not match with expected error message");

        softAssert.assertAll();
    }

    @Description("An message displays with Full Name contains numberic characters")
    @Test(priority = 3)
    public void UMN004_CanNotAddUserWithFullNameContainsNumberic() throws InterruptedException {

        userPage.goToUserManagePage();
        userPage.openAddUserForm();

        String password = userPage.randomPassword();
        Faker faker = new Faker();

        userPage.addUser(userPage.randomRoleName(),
                userPage.randomEmail(),
                faker.number().digits(10),
                password,
                password
        );

        softAssert.assertTrue(userPage.isErrorMessageDisplayed(),
                "Error message is NOT displayed");

        softAssert.assertAll();
    }

    @Description("An message displays with Full Name contains special characters")
    @Test(priority = 4)
    public void UMN005_CanNotAddUserWithFullNameContainsSpecial() throws InterruptedException {

        userPage.goToUserManagePage();
        userPage.openAddUserForm();

        String password = userPage.randomPassword();
        Faker faker = new Faker();

        userPage.addUser(userPage.randomRoleName(),
                userPage.randomEmail(),
                faker.regexify("[!@#$%^&*]{10}"),
                password,
                password
        );

        softAssert.assertTrue(userPage.isErrorMessageDisplayed(),
                "Error message is NOT displayed");

        softAssert.assertAll();
    }

    @Description("An message displays with Full Name contains only space characters")
    @Test(priority = 5)
    public void UMN006_CanNotAddUserWithFullNameContainsOnlySpaces() throws InterruptedException {

        userPage.goToUserManagePage();
        userPage.openAddUserForm();

        String password = userPage.randomPassword();

        userPage.addUser(userPage.randomRoleName(),
                userPage.randomEmail(),
                "     ",
                password,
                password
        );

        softAssert.assertTrue(userPage.isErrorMessageDisplayed(),
                "Error message is NOT displayed");

        softAssert.assertAll();
    }

    @Description("user can not add new user when leaving email empty")
    @Test(priority = 6)
    public void TC007_CanNotAddUserWithEmailEmpty() throws InterruptedException {

        userPage.goToUserManagePage();
        userPage.openAddUserForm();

        String password = userPage.randomPassword();

        userPage.addUser(userPage.randomRoleName(),
                "",
                userPage.randomFullName(),
                password,
                password
        );

        softAssert.assertEquals(userPage.getMessage(), "Vui lòng nhập email",
                "error message mismatch");

        softAssert.assertAll();
    }

    @Test(priority = 7)
    public void TC008_CanNotAddUserWithEmailContainsSpaceCharacter() throws InterruptedException {

        userPage.goToUserManagePage();
        userPage.openAddUserForm();

        String password = userPage.randomPassword();

        userPage.addUser(userPage.randomRoleName(),
                "    ",
                userPage.randomFullName(),
                password,
                password
        );

        softAssert.assertTrue(userPage.isErrorMessageDisplayed());
        softAssert.assertAll();
    }

    @Test(priority = 8)
    public void TC009_CanNotAddUserWithEmailMissingUsername() throws InterruptedException {

        userPage.goToUserManagePage();
        userPage.openAddUserForm();

        String password = userPage.randomPassword();

        userPage.addUser(userPage.randomRoleName(),
                "@gmail.com",
                userPage.randomFullName(),
                password,
                password
        );

        softAssert.assertTrue(userPage.isErrorMessageDisplayed());
        softAssert.assertAll();
    }

    @Test(priority = 9)
    public void UMG010_CanNotAddUserWithEmailMissingAtSymbol() throws InterruptedException {

        Faker faker = new Faker();

        userPage.goToUserManagePage();
        userPage.openAddUserForm();

        userPage.addUser(userPage.randomRoleName(),
                faker.internet().emailAddress().replace("@", ""),
                faker.name().fullName(),
                faker.internet().password(8, 16),
                faker.internet().password(8, 16)
        );

        softAssert.assertTrue(userPage.isErrorMessageDisplayed());
        softAssert.assertAll();
    }

    @Test(priority = 10)
    public void UMG011_CanNotAddUserWithEmailMissingDomain() throws InterruptedException {

        Faker faker = new Faker();

        userPage.goToUserManagePage();
        userPage.openAddUserForm();

        userPage.addUser(userPage.randomRoleName(),
                faker.internet().emailAddress().split("@")[0] + "@",
                faker.name().fullName(),
                faker.internet().password(8, 16),
                faker.internet().password(8, 16)
        );

        softAssert.assertTrue(userPage.isErrorMessageDisplayed());
        softAssert.assertAll();
    }

    @Test(priority = 11)
    public void UMG012_CanNotAddUserWithEmailContainsMultipleAtSymbols() throws InterruptedException {

        Faker faker = new Faker();

        userPage.goToUserManagePage();
        userPage.openAddUserForm();

        String invalidEmail = faker.internet().emailAddress().replace("@", "@@");

        userPage.addUser(userPage.randomRoleName(),
                invalidEmail,
                faker.name().fullName(),
                faker.internet().password(8, 16),
                faker.internet().password(8, 16)
        );

        softAssert.assertTrue(userPage.isErrorMessageDisplayed());
        softAssert.assertAll();
    }

    @Test(priority = 12)
    public void UMG013_CanNotAddUserWithInvalidEmailFormat_DomainUser() throws InterruptedException {

        Faker faker = new Faker();

        userPage.goToUserManagePage();
        userPage.openAddUserForm();

        String invalidEmail = "@" + faker.internet().emailAddress().split("@")[0];

        userPage.addUser(userPage.randomRoleName(),
                invalidEmail,
                faker.name().fullName(),
                faker.internet().password(8, 16),
                faker.internet().password(8, 16)
        );

        softAssert.assertTrue(userPage.isErrorMessageDisplayed());
        softAssert.assertAll();
    }

    @Test(priority = 13)
    public void UMG_14CanNotAddUserWithPasswordEmpty() throws InterruptedException {

        userPage.goToUserManagePage();
        userPage.openAddUserForm();

        String password = userPage.randomPassword();

        userPage.addUser(userPage.randomRoleName(),
                userPage.randomEmail(),
                userPage.randomFullName(),
                "",
                password
        );

        softAssert.assertEquals(userPage.getMessage(), "Mật khẩu xác nhận không khớp");
        softAssert.assertAll();
    }

    @Test(priority = 14)
    public void UMG_15CanNotAddUserWithPasswordContainsSpaceCharacters() throws InterruptedException {

        userPage.goToUserManagePage();
        userPage.openAddUserForm();

        userPage.addUser(userPage.randomRoleName(),
                userPage.randomEmail(),
                userPage.randomFullName(),
                "    ",
                userPage.randomPassword()
        );

        softAssert.assertEquals(userPage.getMessage(), "Mật khẩu xác nhận không khớp");
        softAssert.assertAll();
    }

    @Test(priority = 15)
    public void UMG_16CanNotAddUserWithConfirmPasswordEmpty() throws InterruptedException {

        userPage.goToUserManagePage();
        userPage.openAddUserForm();

        String password = userPage.randomPassword();

        userPage.addUser(userPage.randomRoleName(),
                userPage.randomEmail(),
                userPage.randomFullName(),
                password,
                ""
        );

        softAssert.assertEquals(userPage.getMessage(), "Mật khẩu xác nhận không khớp");
        softAssert.assertAll();
    }

    @Test(priority = 16)
    public void UMG_19_UserCanSearchByEmail() {

        userPage.goToUserManagePage();
        userPage.resetSearch();

        UserItem randomUser = userPage.getRandomUser();
        String email = randomUser.getEmail();

        userPage.searchUser(email);

        softAssert.assertTrue(userPage.verifySearchResultContainsKeyword(email));
        softAssert.assertAll();
    }
    @Test(priority = 17)
    public void UMG_20_UserCanSearch_NoDataDisplayed() {

        userPage.goToUserManagePage();
        userPage.resetSearch();

        String invalidKeyword = userPage.generateNonExistingKeyword();

        userPage.searchUser(invalidKeyword);

        userPage.waitForTableLoaded();

        softAssert.assertEquals(userPage.getTotalUsers(), 0,
                "Table is not empty after searching invalid keyword");

        softAssert.assertAll();
    }
}