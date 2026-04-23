package common;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import pages.*;
import utils.Constants;

public class BaseTest {

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    protected SoftAssert softAssert;

    protected LoginPage loginPage;
    protected HomePage homePage;
    protected UserPage userPage;
    protected ClassPage classPage;
    protected StudentsPage studentsPage;
    protected TrainingProgramPage trainingProgramPage;
    protected CertificationPage certificationPage;
    protected CertificationManagementPage certificationManagementPage;
    protected RegulationsPage regulationsPage;
    protected ScorePage scorePage;

    public WebDriver getDriver() {
        return driver.get();
    }

    @BeforeMethod
    @Parameters({"browser"})
    public void setup(@Optional("chrome") String browser) {

        driver.set(setupDriver(browser));

        getDriver().manage().window().maximize();
        getDriver().get(Constants.URL);

        softAssert = new SoftAssert();

        loginPage = new LoginPage(getDriver());
        homePage = new HomePage(getDriver());
        userPage = new UserPage(getDriver());
        classPage = new ClassPage(getDriver());
        studentsPage = new StudentsPage(getDriver());
        trainingProgramPage = new TrainingProgramPage(getDriver());

        certificationPage = new CertificationPage(getDriver());
        certificationManagementPage = new CertificationManagementPage(getDriver());

        regulationsPage = new RegulationsPage(getDriver());
        scorePage = new ScorePage(getDriver());
    }

    public WebDriver setupDriver(String browserName) {
        switch (browserName.trim().toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                return new ChromeDriver();

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                return new FirefoxDriver();

            case "edge":
                WebDriverManager.edgedriver().setup();
                return new EdgeDriver();

            default:
                WebDriverManager.chromedriver().setup();
                return new ChromeDriver();
        }
    }

    @AfterMethod
    public void tearDown() {
        if (getDriver() != null) {
            getDriver().quit();
            driver.remove();
        }
    }
}