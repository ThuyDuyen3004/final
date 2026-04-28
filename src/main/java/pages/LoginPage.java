package pages;

import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private By usernameTextBoxLocator = By.id("email");
    private By passwordTextBoxLocator = By.id("password");
    private By loginButtonLocator = By.xpath("//button[contains(text(),'Đăng nhập')]");
    private By messageLocator = By.xpath("//p[contains(@class,'text-sm')]");
    By homePageElement = By.xpath("//span[contains(text(),'Cài đặt')]");

    public void waitForLoginForm() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameTextBoxLocator));
    }

    public String getMessage() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(messageLocator)
        ).getText();
    }

    Faker faker = new Faker();

    public String randomEmail() {
        return faker.internet().emailAddress();
    }

    public String randomPasswordDigit() {
        return faker.number().digits(8);
    }

    public void login(String email, String password) {

        waitForLoginForm();

        WebElement emailInput = wait.until(
                ExpectedConditions.visibilityOfElementLocated(usernameTextBoxLocator)
        );
        emailInput.clear();
        emailInput.sendKeys(email);

        WebElement passwordInput = wait.until(
                ExpectedConditions.visibilityOfElementLocated(passwordTextBoxLocator)
        );
        passwordInput.clear();
        passwordInput.sendKeys(password);

        wait.until(ExpectedConditions.elementToBeClickable(loginButtonLocator)).click();
    }
}
