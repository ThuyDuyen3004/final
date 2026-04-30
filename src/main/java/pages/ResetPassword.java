package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ResetPassword {

    WebDriver driver;
    WebDriverWait wait;

    // Constructor
    public ResetPassword(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    /* ================= LOCATOR ================= */
    private By passwordTab = By.xpath("//button[contains(text(),'Mật khẩu')]");
    private final By currentPasswordInput = By.id("current-password");
    private final By newPasswordInput = By.id("new-password");
    private final By confirmPasswordInput = By.id("confirm-password");
    private final By saveButton = By.xpath("//button[contains(text(),'Lưu')]");
    private final By errorMsg = By.xpath("//div[contains(@class,'text-red-600')]");

    /* ================= ACTION ================= */

    public void resetPassword(String currentPassword, String newPassword, String confirmPassword) {

        // click tab (có wait)
        wait.until(ExpectedConditions.elementToBeClickable(passwordTab)).click();

        // wait form load xong (quan trọng)
        wait.until(ExpectedConditions.visibilityOfElementLocated(currentPasswordInput));

        // nhập current password
        var currentPassEl = wait.until(ExpectedConditions.visibilityOfElementLocated(currentPasswordInput));
        currentPassEl.clear();
        currentPassEl.sendKeys(currentPassword);

        // nhập new password
        var newPassEl = wait.until(ExpectedConditions.visibilityOfElementLocated(newPasswordInput));
        newPassEl.clear();
        newPassEl.sendKeys(newPassword);

        // nhập confirm password
        var confirmPassEl = wait.until(ExpectedConditions.visibilityOfElementLocated(confirmPasswordInput));
        confirmPassEl.clear();
        confirmPassEl.sendKeys(confirmPassword);

        // click save
        wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
    }

    /* ================= VERIFY ================= */

    public boolean isErrorMessageDisplayed(String expectedText) {
        try {
            String actualText = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(errorMsg)
            ).getText();

            return actualText.trim().contains(expectedText);

        } catch (Exception e) {
            return false;
        }
    }
}