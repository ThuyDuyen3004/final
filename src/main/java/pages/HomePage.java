package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomePage extends BasePage {

    public HomePage(WebDriver driver) {
        super(driver);
    }

    private By usernameLocator =
            By.xpath("//div[@class='flex-1 min-w-0']/p[contains(@class,'text-sm')]");

    private By roleLocator =
            By.xpath("//div[@class='flex-1 min-w-0']/p[contains(@class,'text-xs')]");

    public String getUsername() {
        return wait
                .until(ExpectedConditions.visibilityOfElementLocated(usernameLocator))
                .getText()
                .trim();
    }

    public String getRole() {
        return wait
                .until(ExpectedConditions.visibilityOfElementLocated(roleLocator))
                .getText()
                .trim();
    }
}
