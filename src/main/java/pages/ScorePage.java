package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ScorePage extends BasePage {
    public ScorePage(WebDriver driver) {
        super(driver);
    }

    /* ================= NAVIGATION ================= */

    public void goDataManagementPage() {
        clickMenu("Quản lý dữ liệu");
    }

    public void goToStudentsPage() {
        goDataManagementPage();
        clickMenu("Điểm");
    }
    private By addButton = By.xpath("//button[normalize-space()='Thêm']");
    private By studentsDropdown = By.xpath("//input[@placeholder='Nhập MSSV/Họ và tên']");
    private By hocphanDropdown = By.xpath("//input[@placeholder='Nhập tên học phần']");
    private By nhapdiemInput = By.xpath("//input[@placeholder='Nhập điểm']");
    private By saveButton = By.xpath("//button[contains(text(),'Lưu')]");

    public String getScoreByMSSVAndSubject(String mssv, String subject) {

        // tìm row theo MSSV
        WebElement row = driver.findElement(By.xpath(
                "//tr[td[contains(text(),'" + mssv + "')]]"
        ));

        // tìm index của column theo tên học phần
        List<WebElement> headers = driver.findElements(By.xpath("//table//th"));

        int columnIndex = -1;
        for (int i = 0; i < headers.size(); i++) {
            if (headers.get(i).getText().trim().equalsIgnoreCase(subject)) {
                columnIndex = i + 1; // xpath index bắt đầu từ 1
                break;
            }
        }

        if (columnIndex == -1) {
            throw new RuntimeException("Không tìm thấy cột học phần: " + subject);
        }

        // lấy cell theo column index
        WebElement cell = row.findElement(By.xpath("td[" + columnIndex + "]"));

        return cell.getText().trim();
    }
}
