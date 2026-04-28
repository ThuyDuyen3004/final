package test;

import io.github.bonigarcia.wdm.WebDriverManager;
import jdk.jfr.Description;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import pages.HomePage;
import pages.LoginPage;
import utils.Constants;

import java.time.Duration;

public class Login {

    private WebDriver driver;
    private LoginPage loginPage;
    private HomePage homePage;
    private SoftAssert softAssert;

    @BeforeMethod
    @Parameters("browser")
    public void setUp(@Optional("chrome") String browser) {

        driver = createDriver(browser);

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get(Constants.URL);

        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
        softAssert = new SoftAssert();
    }

    // ✅ FIX: tách hàm createDriver đúng chuẩn
    private WebDriver createDriver(String browser) {

        switch (browser.toLowerCase().trim()) {

            case "chrome":
                WebDriverManager.chromedriver().setup();
                return new ChromeDriver();

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                return new FirefoxDriver();

            case "edge":
                System.setProperty("webdriver.edge.driver",
                        "C:\\Users\\ACER\\Downloads\\edgedriver_win64\\msedgedriver.exe");
                return new EdgeDriver();

            default:
                WebDriverManager.chromedriver().setup();
                return new ChromeDriver();
        }
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @Description("User can login successfully with valid account")
    public void LOG03_UserCanLoginWithValidAcc() {

        loginPage.login(Constants.EMAIL, Constants.PASSWORD);

        softAssert.assertEquals(homePage.getUsername(), "Trang Than",
                "username mismatch");

        softAssert.assertEquals(homePage.getRole(), "Giáo vụ khoa",
                "role mismatch");

        softAssert.assertAll();
    }

    @Test
    @Description("Email empty")
    public void LOG04_UserCanNotLoginWithEmailEmpty() {

        loginPage.login("", Constants.PASSWORD);

        softAssert.assertEquals(loginPage.getMessage(),
                "Vui lòng nhập đầy đủ email và mật khẩu");

        softAssert.assertAll();
    }

    @Test
    @Description("Email invalid")
    public void LOG05_UserCanNotLoginWithEmailInvalid() {

        loginPage.login(loginPage.randomEmail(), Constants.PASSWORD);

        softAssert.assertEquals(loginPage.getMessage(),
                "Incorrect email or password");

        softAssert.assertAll();
    }

    @Test
    @Description("Password empty")
    public void LOG06_UserCanNotLoginWithPasswordEmpty() {

        loginPage.login(Constants.EMAIL, "");

        softAssert.assertEquals(loginPage.getMessage(),
                "Vui lòng nhập đầy đủ email và mật khẩu");

        softAssert.assertAll();
    }

    @Test
    @Description("Password invalid")
    public void LOG07_UserCanNotLoginWithPasswordInvalid() {

        loginPage.login(Constants.EMAIL, loginPage.randomPasswordDigit());

        softAssert.assertEquals(loginPage.getMessage(),
                "Incorrect email or password");

        softAssert.assertAll();
    }
    //

    @Test
    @Description("User can login successfully with valid account")
    public void LOG031_UserCanLoginWithValidAcc() {

        loginPage.login(Constants.EMAIL, Constants.PASSWORD);

        softAssert.assertEquals(homePage.getUsername(), "Trang Than",
                "username mismatch");

        softAssert.assertEquals(homePage.getRole(), "Giáo vụ khoa",
                "role mismatch");

        softAssert.assertAll();
    }

    @Test
    @Description("Email empty")
    public void LOG041_UserCanNotLoginWithEmailEmpty() {

        loginPage.login("", Constants.PASSWORD);

        softAssert.assertEquals(loginPage.getMessage(),
                "Vui lòng nhập đầy đủ email và mật khẩu");

        softAssert.assertAll();
    }

    @Test
    @Description("Email invalid")
    public void LOG051_UserCanNotLoginWithEmailInvalid() {

        loginPage.login(loginPage.randomEmail(), Constants.PASSWORD);

        softAssert.assertEquals(loginPage.getMessage(),
                "Incorrect email or password");

        softAssert.assertAll();
    }

    @Test
    @Description("Password empty")
    public void LOG061_UserCanNotLoginWithPasswordEmpty() {

        loginPage.login(Constants.EMAIL, "");

        softAssert.assertEquals(loginPage.getMessage(),
                "Vui lòng nhập đầy đủ email và mật khẩu");

        softAssert.assertAll();
    }

    @Test
    @Description("Password invalid")
    public void LOG071_UserCanNotLoginWithPasswordInvalid() {

        loginPage.login(Constants.EMAIL, loginPage.randomPasswordDigit());

        softAssert.assertEquals(loginPage.getMessage(),
                "Incorrect email or password");

        softAssert.assertAll();
    }
}