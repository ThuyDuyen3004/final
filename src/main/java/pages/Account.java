package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Account extends BasePage{
    public Account(WebDriver driver) {
        super(driver);
    }


    private final By fullName = By.id("name");
    private final By email = By.id("email");
    private final By saveButton = By.xpath("//button[contains(text(),'Lưu')]");
    private final By errorMsg = By.xpath("//div[.='Vui lòng điền đầy đủ thông tin bắt buộc']");


}
