package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.time.Duration;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }


    protected String menuItemLocator = "//div[@class='mb-3']/descendant::span[contains(.,'%s')]";
    private By importButton = By.xpath("//button[normalize-space()='Import']");
    private By chooseFileButton = By.xpath("//button[contains(text(),'Chọn tệp')]");

    public void clickMenu(String menuName) {
        driver.findElement(By.xpath(String.format(menuItemLocator, menuName))).click();
    }

    public void goToImportForm(String filePath) throws AWTException {

        driver.findElement(importButton).click();
        driver.findElement(chooseFileButton).click();

        Robot robot = new Robot();
        robot.delay(1000);

        StringSelection selection = new StringSelection(filePath);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);

        robot.delay(1000);

        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);

        robot.delay(1000);

        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
    }
    public void waitForTableLoaded() {
        By rows = By.xpath("//tr");
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(rows, 0));
    }
}
