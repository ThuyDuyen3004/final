package test;

import com.github.javafaker.Faker;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.ResetPassword;

public class ResetPasswordTest {

    WebDriver driver;
    ResetPassword resetPage;
    Faker faker = new Faker();

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.get("http://localhost:3000/cai-dat-tai-khoan");

        resetPage = new ResetPassword(driver);
    }

    @Test
    public void CurrentPasswordCannotEmpty() {
        String newPass = faker.internet().password();

        resetPage.resetPassword("", newPass, newPass);

        Assert.assertTrue(
                resetPage.isErrorMessageDisplayed("Vui lòng điền đầy đủ thông tin")
        );
    }

    @Test
    public void NewPasswordCannotEmpty() {
        String currentPass = faker.internet().password();

        resetPage.resetPassword(currentPass, "", "");

        Assert.assertTrue(
                resetPage.isErrorMessageDisplayed("Vui lòng điền đầy đủ thông tin")
        );
    }

    @Test
    public void ConfirmPasswordCannotEmpty() {
        String currentPass = faker.internet().password();
        String newPass = faker.internet().password();

        resetPage.resetPassword(currentPass, newPass, "");

        Assert.assertTrue(
                resetPage.isErrorMessageDisplayed("Vui lòng điền đầy đủ thông tin")
        );
    }

    @Test
    public void NewPasswordCannotBeSameAsOld() {
        String password = faker.internet().password();

        resetPage.resetPassword(password, password, password);

        Assert.assertTrue(
                resetPage.isErrorMessageDisplayed("Mật khẩu mới phải khác mật khẩu hiện tại")
        );
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}