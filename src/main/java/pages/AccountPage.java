package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AccountPage {

    WebDriver driver;
    public AccountPage(WebDriver driver) {
        this.driver = driver;
    }

    /* ================= LOCATOR ================= */
    private final By fullName = By.id("name");
    private final By emailLocator = By.id("email");
    private final By saveButton = By.xpath("//button[contains(text(),'Lưu')]");
    private final By errorMsg = By.xpath("//div[.='Vui lòng điền đầy đủ thông tin bắt buộc']");

    /* ================= ACTION ================= */

    public void settingAccount(String fullname, String email) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        var fullnameEl = wait.until(ExpectedConditions.visibilityOfElementLocated(fullName));
        fullnameEl.clear();
        fullnameEl.sendKeys(fullname);

        var emailEl = wait.until(ExpectedConditions.visibilityOfElementLocated(emailLocator));
        emailEl.clear();
        emailEl.sendKeys(email);

        wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
    }

    public boolean isErrorMessageDisplayed() {
        return driver.findElement(errorMsg).isDisplayed();
    }
}