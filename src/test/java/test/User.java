package test;

import com.github.javafaker.Faker;
import common.BaseTest;
import jdk.jfr.Description;
import models.Setting.UserItem;
import org.testng.annotations.Test;
import utils.Constants;

import java.util.ArrayList;

public class User extends BaseTest {

    @Test
    public void UMG002_VerifyUserDetailsMatchBetweenFormAndTable() throws InterruptedException {

        Thread.sleep(1000);

        loginPage.login(Constants.EMAIL, Constants.PASSWORD);

        Thread.sleep(1000);

        userPage.goToUserManagePage();

        Thread.sleep(1000);

        userPage.openAddUserForm();

        String email = userPage.randomEmail();

        String fullName = userPage.randomFullName();

        String password = userPage.randomPassword();

        ArrayList<UserItem> expectedUsers = new ArrayList<>();

        expectedUsers.add(new UserItem(fullName, email));

        Thread.sleep(1000);

        userPage.addUser(
                userPage.randomRoleName(),
                email,
                fullName,
                password,
                password
        );

        Thread.sleep(1000);

        ArrayList<UserItem> actualUsers = userPage.getAllUsers();

        softAssert.assertTrue(
                actualUsers.containsAll(expectedUsers),
                "User details do not match between Add User form and User table"
        );

        softAssert.assertAll();
    }

    @Description("user can not add new user when leaving Fullname empty")
    @Test
    public void UMG_003CanNotAddUserWithFullNameEmpty() throws InterruptedException {

        Thread.sleep(1000);

        loginPage.login(Constants.EMAIL, Constants.PASSWORD);

        Thread.sleep(1000);

        userPage.goToUserManagePage();

        Thread.sleep(1000);

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
    @Test
    public void UMN004_CanNotAddUserWithFullNameContainsNumberic() throws InterruptedException {

        Thread.sleep(1000);

        loginPage.login(Constants.EMAIL, Constants.PASSWORD);

        Thread.sleep(1000);

        userPage.goToUserManagePage();

        Thread.sleep(1000);

        userPage.openAddUserForm();

        String password = userPage.randomPassword();

        Faker faker = new Faker();

        userPage.addUser(userPage.randomRoleName(),
                userPage.randomEmail(),
                faker.number().digits(10),
                password,
                password
        );

        softAssert.assertTrue(
                userPage.isErrorMessageDisplayed(), "Error message is NOT displayed");

        softAssert.assertAll();
    }

    @Description("An message displays with Full Name contains special characters")
    @Test
    public void UMN005_CanNotAddUserWithFullNameContainsSpecial() throws InterruptedException {

        Thread.sleep(1000);

        loginPage.login(Constants.EMAIL, Constants.PASSWORD);

        Thread.sleep(1000);

        userPage.goToUserManagePage();

        Thread.sleep(1000);

        userPage.openAddUserForm();

        String password = userPage.randomPassword();

        Faker faker = new Faker();

        userPage.addUser(userPage.randomRoleName(),
                userPage.randomEmail(),
                faker.regexify("[!@#$%^&*]{10}"),
                password,
                password
        );

        softAssert.assertTrue(
                userPage.isErrorMessageDisplayed(), "Error message is NOT displayed");

        softAssert.assertAll();
    }

    @Description("An message displays with Full Name contains only space characters")
    @Test
    public void UMN006_CanNotAddUserWithFullNameContainsOnlySpaces() throws InterruptedException {

        Thread.sleep(1000);

        loginPage.login(Constants.EMAIL, Constants.PASSWORD);

        Thread.sleep(1000);

        userPage.goToUserManagePage();

        Thread.sleep(1000);

        userPage.openAddUserForm();

        String password = userPage.randomPassword();

        userPage.addUser(userPage.randomRoleName(),
                userPage.randomEmail(),
                "     ",
                password,
                password
        );

        softAssert.assertTrue(
                userPage.isErrorMessageDisplayed(),
                "Error message is NOT displayed when fullname contains only spaces"
        );

        softAssert.assertAll();
    }

    @Description("user can not add new user when leaving email empty")
    @Test
    public void TC007_CanNotAddUserWithEmailEmpty() throws InterruptedException {

        Thread.sleep(1000);

        loginPage.login(Constants.EMAIL, Constants.PASSWORD);

        Thread.sleep(1000);

        userPage.goToUserManagePage();

        Thread.sleep(1000);

        userPage.openAddUserForm();

        String password = userPage.randomPassword();

        userPage.addUser(userPage.randomRoleName(),
                "",
                userPage.randomFullName(),
                password,
                password
        );

        softAssert.assertEquals(userPage.getMessage(), "Vui lòng nhập email",
                "the error message is displayed not match with expected error message");

        softAssert.assertAll();
    }

    @Description("An message displays with Email contains space characters")
    @Test
    public void TC008_CanNotAddUserWithEmailContainsSpaceCharacter() throws InterruptedException {

        Thread.sleep(1000);
        loginPage.login(Constants.EMAIL, Constants.PASSWORD);

        Thread.sleep(1000);

        userPage.goToUserManagePage();

        Thread.sleep(1000);

        userPage.openAddUserForm();

        String password = userPage.randomPassword();

        userPage.addUser(userPage.randomRoleName(), "    ",
                userPage.randomFullName(),
                password,
                password
        );

        softAssert.assertTrue(
                userPage.isErrorMessageDisplayed(),
                "Error message is NOT displayed when fullname contains only spaces"
        );

        softAssert.assertAll();
    }

    @Description("An error message is displayed when the username is missing in the Email format ")
    @Test
    public void TC009_CanNotAddUserWithEmailMissingUsername() throws InterruptedException {

        Thread.sleep(1000);

        loginPage.login(Constants.EMAIL, Constants.PASSWORD);

        Thread.sleep(1000);

        userPage.goToUserManagePage();

        Thread.sleep(1000);

        userPage.openAddUserForm();

        String password = userPage.randomPassword();

        userPage.addUser(userPage.randomRoleName(), "@gmail.com",
                userPage.randomFullName(),
                password,
                password
        );

        softAssert.assertTrue(
                userPage.isErrorMessageDisplayed(),
                "Error message is NOT displayed when email is missing username"
        );

        softAssert.assertAll();
    }

    @Description("An error message displays when email is missing '@'")
    @Test
    public void UMG010_CanNotAddUserWithEmailMissingAtSymbol() throws InterruptedException {

        Faker faker = new Faker();

        Thread.sleep(1000);

        loginPage.login(Constants.EMAIL, Constants.PASSWORD);

        Thread.sleep(1000);

        userPage.goToUserManagePage();

        Thread.sleep(1000);

        userPage.openAddUserForm();

        userPage.addUser(
                userPage.randomRoleName(),
                faker.internet().emailAddress().replace("@", ""),
                faker.name().fullName(),
                faker.internet().password(8, 16),
                faker.internet().password(8, 16)
        );

        softAssert.assertTrue(
                userPage.isErrorMessageDisplayed(),
                "Error message is NOT displayed when email is missing '@'"
        );

        softAssert.assertAll();
    }

    @Description("An error message is displayed when the domain is missing in the Email format (username@)")
    @Test
    public void UMG011_CanNotAddUserWithEmailMissingDomain() throws InterruptedException {

        Faker faker = new Faker();

        Thread.sleep(1000);

        loginPage.login(Constants.EMAIL, Constants.PASSWORD);

        Thread.sleep(1000);

        userPage.goToUserManagePage();

        Thread.sleep(1000);

        userPage.openAddUserForm();

        userPage.addUser(
                userPage.randomRoleName(),
                faker.internet().emailAddress().split("@")[0] + "@",
                faker.name().fullName(),
                faker.internet().password(8, 16),
                faker.internet().password(8, 16)
        );

        softAssert.assertTrue(
                userPage.isErrorMessageDisplayed(),
                "Error message is NOT displayed when email is missing domain"
        );

        softAssert.assertAll();
    }

    @Description("An error message is displayed when more than one '@' character is entered in the Email text box")
    @Test
    public void UMG012_CanNotAddUserWithEmailContainsMultipleAtSymbols() throws InterruptedException {

        Faker faker = new Faker();

        Thread.sleep(1000);

        loginPage.login(Constants.EMAIL, Constants.PASSWORD);

        Thread.sleep(1000);

        userPage.goToUserManagePage();

        Thread.sleep(1000);

        userPage.openAddUserForm();

        String validEmail = faker.internet().emailAddress();

        String invalidEmail = validEmail.replace("@", "@@");

        userPage.addUser(
                userPage.randomRoleName(),
                invalidEmail,
                faker.name().fullName(),
                faker.internet().password(8, 16),
                faker.internet().password(8, 16)
        );

        softAssert.assertTrue(
                userPage.isErrorMessageDisplayed(),
                "Error message is NOT displayed when email contains multiple '@' symbols"
        );

        softAssert.assertAll();
    }

    @Description("An error message is displayed when an invalid email format is entered in the Email text box (e.g. @domainUser)")
    @Test
    public void UMG013_CanNotAddUserWithInvalidEmailFormat_DomainUser() throws InterruptedException {

        Faker faker = new Faker();

        Thread.sleep(1000);

        loginPage.login(Constants.EMAIL, Constants.PASSWORD);

        Thread.sleep(1000);

        userPage.goToUserManagePage();

        Thread.sleep(1000);

        userPage.openAddUserForm();

        String invalidEmail = "@" + faker.internet().emailAddress().split("@")[0];

        userPage.addUser(
                userPage.randomRoleName(),
                invalidEmail,
                faker.name().fullName(),
                faker.internet().password(8, 16),
                faker.internet().password(8, 16)
        );

        softAssert.assertTrue(
                userPage.isErrorMessageDisplayed(),
                "Error message is NOT displayed when invalid email format '@domainUser' is entered"
        );

        softAssert.assertAll();
    }

    @Description("user can not add new user when leaving password empty")
    @Test
    public void UMG_14CanNotAddUserWithPasswordEmpty() throws InterruptedException {

        Thread.sleep(1000);

        loginPage.login(Constants.EMAIL, Constants.PASSWORD);

        Thread.sleep(1000);

        userPage.goToUserManagePage();

        Thread.sleep(1000);

        userPage.openAddUserForm();

        String password = userPage.randomPassword();

        userPage.addUser(userPage.randomRoleName(),
                userPage.randomEmail(),
                userPage.randomFullName(),
                "",
                password
        );

        softAssert.assertEquals(userPage.getMessage(), "Vui lòng nhập mật khẩu",
                "the error message is displayed not match with expected error message");

        softAssert.assertAll();
    }

    @Description("An message displays with Password contains space characters")
    @Test
    public void UMG_15CanNotAddUserWithPasswordContainsSpaceCharacters() throws InterruptedException {

        Thread.sleep(1000);

        loginPage.login(Constants.EMAIL, Constants.PASSWORD);

        Thread.sleep(1000);

        userPage.goToUserManagePage();

        Thread.sleep(1000);

        userPage.openAddUserForm();

        String password = userPage.randomPassword();

        userPage.addUser(userPage.randomRoleName(),
                userPage.randomEmail(),
                userPage.randomFullName(), "    ",
                password
        );

        softAssert.assertEquals(userPage.getMessage(), "Vui lòng nhập mật khẩu",
                "the error message is displayed not match with expected error message");

        softAssert.assertAll();
    }

    @Description("user can not add new user when leaving password empty")
    @Test
    public void UMG_16CanNotAddUserWithConfirmPasswordEmpty() throws InterruptedException {

        Thread.sleep(1000);

        loginPage.login(Constants.EMAIL, Constants.PASSWORD);

        Thread.sleep(1000);

        userPage.goToUserManagePage();

        Thread.sleep(1000);

        userPage.openAddUserForm();

        String password = userPage.randomPassword();

        userPage.addUser(userPage.randomRoleName(),
                userPage.randomEmail(),
                userPage.randomFullName(),
                password,
                ""
        );

        softAssert.assertEquals(userPage.getMessage(), "Vui lòng xác nhận mật khẩu",
                "the error message is displayed not match with expected error message");

        softAssert.assertAll();
    }

    @Description("user can not add new user when confirm password not match password")
    @Test
    public void UMG_17CanNotAddUserWithConfirmPasswordNotMatchWithPassword() throws InterruptedException {

        Thread.sleep(1000);

        loginPage.login(Constants.EMAIL, Constants.PASSWORD);

        Thread.sleep(1000);

        userPage.goToUserManagePage();

        Thread.sleep(1000);

        userPage.openAddUserForm();

        String password = userPage.randomPassword();

        userPage.addUser(userPage.randomRoleName(),
                userPage.randomEmail(),
                userPage.randomFullName(),
                password,
                userPage.randomPassword()
        );

        softAssert.assertEquals(userPage.getMessage(), "Xác nhận mật khẩu không khơp",
                "the error message is displayed not match with expected error message");

        softAssert.assertAll();
    }

    @Description("User can search by username ")
    @Test
    public void UMG_18UserCanSearchByUsername() throws InterruptedException {

        Thread.sleep(1000);

        loginPage.login(Constants.EMAIL, Constants.PASSWORD);

        Thread.sleep(1000);

        userPage.goToUserManagePage();

        Thread.sleep(1000);

        UserItem user = userPage.getRandomUser();

        String username = user.getUsername();

        userPage.searchUser(username);

        Thread.sleep(1000);

        softAssert.assertTrue(
                userPage.verifySearchResultContainsKeyword(username),
                "Search result does not contain searched username"
        );
        softAssert.assertAll();
    }

    @Description("User can search by email")
    @Test
    public void UMG_19_UserCanSearchByEmail() throws InterruptedException {
        Thread.sleep(1000);

        loginPage.login(Constants.EMAIL, Constants.PASSWORD);
        Thread.sleep(1000);

        userPage.goToUserManagePage();
        Thread.sleep(1000);

        UserItem randomUser = userPage.getRandomUser();
        String email = randomUser.getEmail();
        Thread.sleep(1000);

        userPage.searchUser(email);
        Thread.sleep(1000);

        softAssert.assertTrue(
                userPage.verifySearchResultContainsKeyword(email),
                "Search result does not contain email"
        );
        softAssert.assertAll();
    }


    @Description("User can search with non-existing keyword")
    @Test
    public void UMG_20_UserCanSearch_NoDataDisplayed() throws InterruptedException {

        Thread.sleep(1000);

        loginPage.login(Constants.EMAIL, Constants.PASSWORD);

        Thread.sleep(1000);

        userPage.goToUserManagePage();

        Thread.sleep(1000);

        String invalidKeyword = userPage.generateNonExistingKeyword();

        userPage.searchUser(invalidKeyword);

        softAssert.assertTrue(
                userPage.verifyNoDataMessageDisplayed(),
                "No data message is not displayed or incorrect"
        );

        softAssert.assertAll();
    }
    @Description("The user is deleted successfully")
    @Test
    public void UMG_21UserCanDeleted() throws InterruptedException {

        Thread.sleep(1000);

        loginPage.login(Constants.EMAIL, Constants.PASSWORD);

        Thread.sleep(1000);

        userPage.goToUserManagePage();

        Thread.sleep(1000);

        String deletedEmail = userPage.randomClickIconAndDelete();

        Thread.sleep(1000);

        userPage.searchUser(deletedEmail);

        softAssert.assertTrue(
                userPage.verifyNoDataMessageDisplayed(),
                "Deleted user email still exists"
        );

        softAssert.assertAll();
    }

    @Test
    public void UMG_022_VerifyUserCanBeUpdatedSuccessfully() throws InterruptedException {

        Thread.sleep(1000);
        loginPage.login(Constants.EMAIL, Constants.PASSWORD);

        Thread.sleep(1000);
        userPage.goToUserManagePage();

        Thread.sleep(1000);
        userPage.openAddUserForm();

        String email = userPage.randomEmail();
        String fullName = userPage.randomFullName();
        String password = userPage.randomPassword();

        Thread.sleep(1000);
        userPage.updateUser(
                userPage.randomRoleName(),
                email,
                fullName,
                password,
                password
        );

        Thread.sleep(1000);
        ArrayList<UserItem> actualUsers = userPage.getAllUsers();
        Thread.sleep(2000);
        softAssert.assertFalse(
                actualUsers.isEmpty(),
                "User was not updated / not displayed after update"
        );

        softAssert.assertAll();
    }

}

