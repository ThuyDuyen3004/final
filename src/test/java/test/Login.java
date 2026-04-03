package test;

import common.BaseTest;
import jdk.jfr.Description;
import org.testng.annotations.Test;
import utils.Constants;

public class Login extends BaseTest {
    @Description("User can login successfully with valid account")
    @Test
    public void LOG03_UserCanLoginWithValidAcc() throws InterruptedException {

        Thread.sleep(1000);

        loginPage.login(Constants.EMAIL, Constants.PASSWORD);

        Thread.sleep(2000);

        softAssert.assertEquals(homePage.getUsername(), "Trang Than",
                "the username is displayed not match with expected result");

        softAssert.assertEquals(homePage.getRole(), "Giáo vụ khoa",
                "the role is displayed not match with expected result");

        softAssert.assertAll();
    }

    @Description("An error message displays when user tries to login with Email empty")
    @Test
    public void LOG04_UserCanNotLoginWithEmailEmpty() throws InterruptedException {

        Thread.sleep(1000);

        loginPage.login("", Constants.PASSWORD);

        Thread.sleep(1000);

        softAssert.assertEquals(loginPage.getMessage(), "Vui lòng nhập đầy đủ email và mật khẩu",
                "the message is not match with expected message");

        softAssert.assertAll();
    }

    @Description("An error message displays when user tries to login with Email invalid")
    @Test
    public void LOG05_UserCanNotLoginWithEmailInvalid() throws InterruptedException {

        Thread.sleep(1000);

        loginPage.login(loginPage.randomEmail(), Constants.PASSWORD);

        Thread.sleep(1000);

        softAssert.assertEquals(loginPage.getMessage(), "Email hoặc mật khẩu không đúng",
                "the message is not match with expected message");

        softAssert.assertAll();
    }

    @Description("An error message displays when user tries to login with Email invalid")
    @Test
    public void LOG006_UserCanNotLoginWithPasswordEmpty() throws InterruptedException {

        Thread.sleep(1000);

        loginPage.login(Constants.EMAIL, "");

        Thread.sleep(1000);

        softAssert.assertEquals(loginPage.getMessage(), "Vui lòng nhập đầy đủ email và mật khẩu",
                "the message is not match with expected message");

        softAssert.assertAll();
    }

    @Description("An error message displays when user tries to login with Password invalid")
    @Test
    public void LOG007_UserCanNotLoginWithPasswordInvalid() throws InterruptedException {

        Thread.sleep(1000);

        loginPage.login(Constants.EMAIL, loginPage.randomPasswordDigit());

        Thread.sleep(1000);

        softAssert.assertEquals(loginPage.getMessage(), "Email hoặc mật khẩu không đúng",
                "the message is not match with expected message");

        softAssert.assertAll();
    }
}
