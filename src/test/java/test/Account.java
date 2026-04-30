package test;

import com.github.javafaker.Faker;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.AccountPage;

public class Account {

    WebDriver driver;
    AccountPage accountPage;
    Faker faker = new Faker();

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.get("http://localhost:3000/cai-dat-tai-khoan");

        accountPage = new AccountPage(driver);
    }

    @Test
    public void FullnameCannotEmpty() {

        String randomEmail = faker.internet().emailAddress();

        accountPage.settingAccount("", randomEmail);

        Assert.assertTrue(
                accountPage.isErrorMessageDisplayed(),
                "Không hiển thị message: Vui lòng điền đầy đủ thông tin bắt buộc"
        );
    }

    @Test
    public void EmailCannotEmpty() {

        String randomName = faker.name().fullName();

        accountPage.settingAccount(randomName, "");

        Assert.assertTrue(
                accountPage.isErrorMessageDisplayed(),
                "Không hiển thị message: Vui lòng điền đầy đủ thông tin bắt buộc"
        );
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}