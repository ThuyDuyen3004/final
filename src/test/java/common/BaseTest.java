//package common;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.testng.annotations.AfterMethod;
//import org.testng.annotations.BeforeMethod;
//import org.testng.asserts.SoftAssert;
//import pages.*;
//import utils.Constants;
//
//public class BaseTest {
//
//    protected WebDriver driver;
//    protected SoftAssert softAssert;
//    protected LoginPage loginPage;
//    protected HomePage homePage;
//    protected UserPage userPage;
//    protected ClassPage classPage;
//    protected StudentsPage studentsPage;
//    protected TrainingProgramPage trainingProgramPage;
//
//    @BeforeMethod
//    public void setup() {
//        driver = new ChromeDriver();
//        driver.manage().window().maximize();
//        driver.get(Constants.URL);
//
//        softAssert = new SoftAssert();
//        loginPage = new LoginPage(driver);
//        homePage = new HomePage(driver);
//        userPage = new UserPage(driver);
//        classPage = new ClassPage(driver);
//        studentsPage = new StudentsPage(driver);
//        trainingProgramPage = new TrainingProgramPage(driver);
//    }
//
//    @AfterMethod
//    public void tearDown() {
//            driver.quit();
//    }
//
//}
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

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    protected SoftAssert softAssert;

    protected LoginPage loginPage;
    protected HomePage homePage;
    protected UserPage userPage;
    protected ClassPage classPage;
    protected StudentsPage studentsPage;
    protected TrainingProgramPage trainingProgramPage;
    protected CertificationPage certificatePage;
    protected RegulationsPage regulationsPage;

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
        certificatePage = new CertificationPage(getDriver());
        regulationsPage = new RegulationsPage(getDriver());
    }

    public WebDriver setupDriver(String browserName) {
        switch (browserName.trim().toLowerCase()) {
            case "chrome":
                return initChromeDriver();
            case "firefox":
                return initFirefoxDriver();
            case "edge":
                return initEdgeDriver();
            default:
                System.out.println("Browser: " + browserName + " không hợp lệ");
                return initChromeDriver();
        }
    }

    private WebDriver initChromeDriver() {
        System.out.println("=== Launching Chrome ===");
        WebDriverManager.chromedriver().setup();
        return new ChromeDriver();
    }

    private WebDriver initFirefoxDriver() {
        System.out.println("=== Launching Firefox ===");
        WebDriverManager.firefoxdriver().setup();
        return new FirefoxDriver();
    }

    private WebDriver initEdgeDriver() {
        System.out.println("Launching Edge...");
        System.setProperty("webdriver.edge.driver", "C:\\Users\\ACER\\Downloads\\edgedriver_win64\\msedgedriver.exe");
        WebDriver driver = new EdgeDriver();
        driver.manage().window().maximize();
        return driver;
    }

    @AfterMethod
    public void tearDown() {
        if (getDriver() != null) {
            getDriver().quit();
            driver.remove();
        }
    }
}