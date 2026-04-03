package pages;

import models.Setting.CertificateItem;
import models.Setting.StudentItem;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.*;

public class CertificationPage extends BasePage {

    public CertificationPage(WebDriver driver) {
        super(driver);
    }

    /* ================= NAVIGATION ================= */

    public void goToSettingPage() {
        clickMenu("Quản lý dữ liệu");
    }

    public void goToCertificationPage() {
        goToSettingPage();
        clickMenu("Chứng chỉ");
    }

    /* ================= LOCATORS ================= */

    private final By addButton = By.xpath("//button[contains(.,'Thêm')]");
    private final By studentInput = By.xpath("//input[@placeholder='Nhập MSSV/Họ và tên']");
    private final By saveButton = By.xpath("//button[contains(.,'Lưu')]");
    private final By tableRows = By.xpath("//tbody/tr");

    // ===== STUDENT DROPDOWN =====
    private final By studentOptions = By.xpath("//div[contains(@class,'overflow-auto')]//button");

    // ===== CERTIFICATE =====
    private final By certificateDropdown =
            By.xpath("//label[contains(text(),'Các loại chứng chỉ')]/following-sibling::div");

    private final By certificateContainer =
            By.xpath("//div[@class='p-2 space-y-1']");

    private final By certificateItems =
            By.xpath("//div[@class='p-2 space-y-1']/div");

    private final By checkboxBtn = By.xpath(".//button");
    private final By checkboxText = By.xpath(".//span");
    private final By courseDropdown = By.xpath("//button[.='Khóa']");

    private final By searchInput =
            By.xpath("//input[@placeholder='Nhập tên sinh viên...']");

    /* ================= OPEN FORM ================= */

    public void openAddCertificationForm() {
        wait.until(ExpectedConditions.elementToBeClickable(addButton)).click();
    }

    /* ================= RANDOM STUDENT FROM TABLE ================= */

    public StudentItem getRandomStudent() {

        wait.until(ExpectedConditions.visibilityOfElementLocated(tableRows));

        List<WebElement> rows = driver.findElements(tableRows);

        if (rows.isEmpty()) {
            throw new RuntimeException("❌ Student table is empty");
        }

        int index = new Random().nextInt(rows.size()) + 1;

        String hoLot = getCell(index, getColumnIndex("HỌ LÓT")).getText().trim();
        String ten = getCell(index, getColumnIndex("TÊN")).getText().trim();

        // chuẩn hóa tránh lỗi space/null
        hoLot = hoLot == null ? "" : hoLot.trim();
        ten = ten == null ? "" : ten.trim();

        System.out.println("👉 Random student: " + hoLot + " " + ten);

        return new StudentItem(hoLot, ten);
    }

    /* ================= SEARCH STUDENT ================= */

    public void searchStudentByName(String fullName) {

        WebElement searchBox = wait.until(
                ExpectedConditions.visibilityOfElementLocated(searchInput)
        );

        searchBox.clear();

        // 👉 chỉ search TÊN cho ổn định
        String[] parts = fullName.split(" ");
        String keyword = parts[parts.length - 1];

        searchBox.sendKeys(keyword);

        // wait table reload
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(tableRows, 0));
    }

    /* ================= SELECT STUDENT IN DROPDOWN ================= */

    public String selectRandomStudent() {

        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(studentInput));

        input.click();
        input.clear();
        input.sendKeys("221");

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(@class,'overflow-auto')]")
        ));

        List<WebElement> options = wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(studentOptions)
        );

        List<String> valid = new ArrayList<>();

        for (WebElement e : options) {
            String text = e.getText().trim();
            if (text.matches("\\d+\\s-\\s.*")) {
                valid.add(text);
            }
        }

        if (valid.isEmpty()) {
            throw new RuntimeException("❌ Không có student");
        }

        String selected = valid.get(new Random().nextInt(valid.size()));

        System.out.println("👉 Selected student: " + selected);

        By locator = By.xpath(String.format(
                "//div[contains(@class,'overflow-auto')]//button[normalize-space()='%s']",
                selected
        ));

        WebElement option = wait.until(ExpectedConditions.elementToBeClickable(locator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", option);

        return selected;
    }

    /* ================= RANDOM CERTIFICATE ================= */

    public Map<String, Boolean> selectRandomCertificates() {

        wait.until(ExpectedConditions.visibilityOfElementLocated(certificateContainer));

        List<WebElement> items = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(certificateItems)
        );

        if (items.isEmpty()) {
            throw new RuntimeException("❌ Không có certificate");
        }

        Collections.shuffle(items);

        int numberToSelect = new Random().nextInt(items.size()) + 1;

        Map<String, Boolean> result = new LinkedHashMap<>();

        for (int i = 0; i < items.size(); i++) {

            List<WebElement> freshItems = driver.findElements(certificateItems);
            WebElement item = freshItems.get(i);

            WebElement btn = item.findElement(checkboxBtn);
            WebElement textEl = item.findElement(checkboxText);

            String text = textEl.getText().trim();

            boolean shouldSelect = i < numberToSelect;

            if (shouldSelect) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
            }

            result.put(text, shouldSelect);
        }

        return result;
    }

    /* ================= ADD CERTIFICATION (PASS) ================= */

    public CertificateItem addCertification() {

        // 1. chọn student
        String studentName = selectRandomStudent();

        // 2. mở dropdown chứng chỉ
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(certificateDropdown));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", dropdown);

        // 3. chọn chứng chỉ
        selectRandomCertificates();

        // 4. đóng dropdown (QUAN TRỌNG)
        driver.findElement(By.xpath("//body")).click();

        // 5. save
        clickSave();

        // 6. wait table
        wait.until(ExpectedConditions.visibilityOfElementLocated(tableRows));

        // 7. scroll
        scrollToStudent(studentName);

        return getCertificateByStudent(studentName);
    }

    /* ================= ADD CERTIFICATION (NO STUDENT) ================= */

    public void addCertificationWithoutStudent() {

        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(certificateDropdown));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", dropdown);

        selectRandomCertificates();

        driver.findElement(By.xpath("//body")).click();

        clickSave();
    }

    /* ================= ERROR MESSAGE ================= */

    public String getStudentErrorMessage() {

        By errorMsg = By.xpath("//p[contains(@class,'text-red-500')]");
        return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMsg)).getText().trim();
    }

    /* ================= COMMON ================= */

    public void clickSave() {
        WebElement saveBtn = wait.until(ExpectedConditions.elementToBeClickable(saveButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", saveBtn);
    }

    /* ================= TABLE ================= */

    private static final List<String> COLUMN_NAMES = List.of(
            "STT", "LỚP", "HỌ LÓT", "TÊN", "NGÀY SINH",
            "CC QUÂN SỰ", "CC THỂ DỤC", "CC NGOẠI NGỮ", "CC TIN HỌC"
    );

    private int getColumnIndex(String name) {
        return COLUMN_NAMES.indexOf(name) + 1;
    }

    private WebElement getCell(int row, int col) {
        return driver.findElement(By.xpath("//tbody/tr[" + row + "]/td[" + col + "]"));
    }

    public int getTotalRows() {
        return driver.findElements(tableRows).size();
    }

    public int findRowByStudentName(String studentName) {

        for (int i = 1; i <= getTotalRows(); i++) {

            String fullName = getCell(i, getColumnIndex("HỌ LÓT")).getText() + " "
                    + getCell(i, getColumnIndex("TÊN")).getText();

            if (fullName.trim().equalsIgnoreCase(studentName.trim())) {
                return i;
            }
        }

        throw new RuntimeException("Không tìm thấy: " + studentName);
    }

    private boolean isChecked(WebElement el) {
        return el.getAttribute("checked") != null ||
                "true".equals(el.getAttribute("aria-checked"));
    }

    public CertificateItem getCertificateByStudent(String studentName) {

        int row = findRowByStudentName(studentName);

        boolean military = isChecked(getCell(row, getColumnIndex("CC QUÂN SỰ")).findElement(By.xpath(".//input")));
        boolean physical = isChecked(getCell(row, getColumnIndex("CC THỂ DỤC")).findElement(By.xpath(".//input")));
        boolean language = isChecked(getCell(row, getColumnIndex("CC NGOẠI NGỮ")).findElement(By.xpath(".//input")));
        boolean it = isChecked(getCell(row, getColumnIndex("CC TIN HỌC")).findElement(By.xpath(".//input")));

        return new CertificateItem(studentName, military, physical, language, it);
    }

    public void scrollToStudent(String studentName) {

        WebElement el = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//td[contains(.,'" + studentName + "')]")
                )
        );

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", el);
    }

    public boolean verifySearchResultContainsName(String fullName) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // wait table load
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tbody/tr")));

        List<WebElement> rows = driver.findElements(By.xpath("//tbody/tr"));

        for (int i = 1; i <= rows.size(); i++) {

            String hoLot = driver.findElement(
                    By.xpath("//tbody/tr[" + i + "]/td[3]")
            ).getText().trim();

            String ten = driver.findElement(
                    By.xpath("//tbody/tr[" + i + "]/td[4]")
            ).getText().trim();

            String nameInTable = (hoLot + " " + ten).trim();

            if (nameInTable.equalsIgnoreCase(fullName.trim())) {
                return true;
            }
        }

        return false;
    }
    public void waitForTableLoaded() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // 👉 đợi table có data
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tbody/tr")));

        // 👉 đợi load xong (nếu có loading)
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(
                    By.xpath("//div[contains(@class,'loading')]")
            ));
        } catch (Exception ignored) {}
    }
    public void filterByClass(String className) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // mở dropdown lớp
        WebElement classDropdown = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[.='Lớp']")
                )
        );

        classDropdown.click();

        // đợi option xuất hiện
        WebElement option = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//div[@role='option'][.='" + className + "']")
                )
        );

        // scroll tới option (tránh bị overlay)
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", option);

        wait.until(ExpectedConditions.elementToBeClickable(option)).click();
    }
    public boolean verifySearchResultContainsKeyword(String keyword) {

        waitForTableLoaded();

        List<WebElement> rows = driver.findElements(By.xpath("//tbody/tr"));

        if (rows.isEmpty()) {
            throw new RuntimeException("❌ Không có dữ liệu sau khi search");
        }

        for (int i = 1; i <= rows.size(); i++) {

            String hoLot = driver.findElement(
                    By.xpath("//tbody/tr[" + i + "]/td[3]")
            ).getText().trim();

            String ten = driver.findElement(
                    By.xpath("//tbody/tr[" + i + "]/td[4]")
            ).getText().trim();

            String fullName = (hoLot + " " + ten).toLowerCase();

            if (!fullName.contains(keyword.toLowerCase())) {
                System.out.println("❌ Sai row: " + fullName);
                return false;
            }
        }

        return true;
    }
    public void searchStudent(String keyword) {

        WebElement searchBox = wait.until(
                ExpectedConditions.visibilityOfElementLocated(searchInput)
        );

        // clear bằng cách chắc chắn (tránh bug input không xóa)
        searchBox.sendKeys(Keys.CONTROL + "a");
        searchBox.sendKeys(Keys.DELETE);

        searchBox.sendKeys(keyword);

        System.out.println("🔍 Searching: " + keyword);

        // đợi table reload
        waitForTableLoaded();
    }
    public boolean verifyAllRowsMatchClass(String expectedClass) {

        waitForTableLoaded();

        List<WebElement> rows = driver.findElements(By.xpath("//tbody/tr"));

        if (rows.isEmpty()) {
            throw new RuntimeException("❌ Table không có dữ liệu");
        }

        for (int i = 1; i <= rows.size(); i++) {

            String actualClass = driver.findElement(
                    By.xpath("//tbody/tr[" + i + "]/td[2]")
            ).getText().trim();

            System.out.println("👉 Row " + i + " | Class: " + actualClass);

            if (!actualClass.equalsIgnoreCase(expectedClass.trim())) {
                System.out.println("❌ Sai row: " + actualClass);
                return false;
            }
        }

        return true;
    }
    public void filterByCourse(String courseName) {
        WebElement dropdown = driver.findElement(courseDropdown);
        dropdown.click();
        WebElement option = driver.findElement(By.xpath("//span[text()='" + courseName + "']"));
        option.click();
    }

    public boolean verifyAllRowsMatchCourse(String courseName) {
        List<WebElement> rows = driver.findElements(tableRows);
        for (WebElement row : rows) {
            // Lấy giá trị cột lớp
            String className = row.findElement(By.xpath("./td[2]")).getText().trim();
            if (!className.equals(courseName)) { // So sánh chính xác
                return false;
            }
        }
        return true;
    }
    }