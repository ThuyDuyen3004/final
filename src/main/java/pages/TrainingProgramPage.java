package pages;

import models.SubjectItem;
import models.TrainingProgramItem;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class TrainingProgramPage extends BasePage{
    public TrainingProgramPage(WebDriver driver) {
        super(driver);
    }

    /* ================= NAVIGATION ================= */

    public void goToSettingPage() {
        clickMenu("Quản lý dữ liệu");
    }

    public void goToTrainingProgramPage() {
        goToSettingPage();
        clickMenu("Chương trình đào tạo");
    }

    /* ================= LOCATORS ================= */

    private final By addButtonLocator =
            By.xpath("//button[contains(text(),'Thêm')]");

    private final By cohortDropdownLocator =
            By.xpath("//button[.='Chọn các khóa áp dụng']");
    private final By majorDropdownLocator =
            By.xpath("//button[.='Chọn chuyên ngành']");
    private static final String COHORT_OPTION_XPATH =
            "//button[normalize-space(.)='%s']";
    private final By cohortOptions =
            By.xpath("//div[contains(@class,'space-y-1')]//button");

    By cohortLabel = By.xpath("//label[contains(text(),'Khóa áp dụng')]");
    private final By majorOptions =
            By.xpath("//label[contains(text(),'Chuyên ngành')]/following::div[@role='option']");

    private final By saveButton =
            By.xpath("//button[contains(text(),'Lưu')]");

    private final By messageLocator =
            By.xpath("//p[@class='text-xs text-red-500']");

    private final By searchBarLocator =
            By.xpath("//input[@placeholder='Nhập tên CTĐT...']");

    private final By tableRows =
            By.xpath("//tbody/tr");

    private By importErrorMessage =
            By.xpath("//div[@class='pt-0 pb-4']/descendant::span");
    /* ================= ADD TRAINING PROGRAM================= */

    public void openAddTrainingProgramForm() {
        wait.until(ExpectedConditions.elementToBeClickable(addButtonLocator)).click();
    }

    /* ================= TABLE UTILS ================= */

    private static final List<String> COLUMN_NAMES = List.of(
            "STT",
            "CHUYÊN NGÀNH",
            "KHÓA ÁP DỤNG",
            "THAO TÁC"
    );

    private int getColumnIndex(String columnName) {
        return COLUMN_NAMES.indexOf(columnName) + 1;
    }

    private WebElement getCell(int row, int column) {
        String xpath = String.format("//tbody/tr[%d]/td[%d]", row, column);
        return driver.findElement(By.xpath(xpath));
    }

    public int getTotalPrograms() {
        return driver.findElements(tableRows).size();
    }

    public int getTotalTrainingProgram() {
        return driver.findElements(tableRows).size();
    }

    public ArrayList<TrainingProgramItem> getAllPrograms() {
        ArrayList<TrainingProgramItem> programs = new ArrayList<>();

        for (int i = 1; i <= getTotalPrograms(); i++) {

            String majorName = getCell(i, getColumnIndex("CHUYÊN NGÀNH")).getText().trim();

            String cohort = getCell(i, getColumnIndex("KHÓA ÁP DỤNG")).getText().trim();

            programs.add(new TrainingProgramItem(majorName, cohort));
        }

        return programs;
    }

    public String randomCohort() {

        wait.until(ExpectedConditions.elementToBeClickable(cohortDropdownLocator)).click();

        List<WebElement> options = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(cohortOptions)
        );

        Random random = new Random();
        WebElement randomOption = options.get(random.nextInt(options.size()));

        String cohort = randomOption.getText().trim();

        randomOption.click();

        return cohort;
    }
    public String randomMajor() {

        wait.until(ExpectedConditions.elementToBeClickable(majorDropdownLocator)).click();

        List<WebElement> options = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(majorOptions)
        );

        Random random = new Random();
        WebElement randomOption = options.get(random.nextInt(options.size()));

        String major = randomOption.getText().trim();

        randomOption.click();

        return major;
    }
    public void selectMajorByText(String majorName) {

        wait.until(ExpectedConditions.elementToBeClickable(majorDropdownLocator)).click();

        By option = By.xpath("//div[@role='option' and normalize-space()='" + majorName + "']");

        wait.until(ExpectedConditions.elementToBeClickable(option)).click();
    }
    public void selectCohortByText(String cohort) {

        // mở dropdown
        wait.until(ExpectedConditions.elementToBeClickable(cohortDropdownLocator)).click();

        By option = By.xpath("//div[@role='dialog']//*[normalize-space()='" + cohort + "']");

        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(option));

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", element);

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", element);
    }
    public void clickSave() {
        driver.findElement(saveButton).click();
    }
    public void clickCohortLabel() {
        driver.findElement(cohortLabel).click();
    }
    public String getErrorMessage() {

        By error = By.xpath("//p[contains(@class,'text-red')]");

        return wait.until(ExpectedConditions.visibilityOfElementLocated(error))
                .getText()
                .trim();
    }
    public TrainingProgramItem addTrainingProgram(String majorName, String cohortName) {

        openAddTrainingProgramForm();

        selectMajorByText(majorName);
        selectCohortByText(cohortName);
        clickCohortLabel();
        clickSave();

        return new TrainingProgramItem(majorName, cohortName);
    }

    public void scrollToTrainingProgram(String programName) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        WebElement programRow = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//td[contains(.,'" + programName + "')]")
                )
        );

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", programRow);
    }
    public TrainingProgramItem getProgramByMajor(String major) {

        int majorCol = getColumnIndex("CHUYÊN NGÀNH");
        int cohortCol = getColumnIndex("KHÓA ÁP DỤNG");

        // tìm row chứa major
        List<WebElement> rows = driver.findElements(By.xpath("//tbody/tr"));

        for (int i = 1; i <= rows.size(); i++) {

            String majorText = getCell(i, majorCol).getText().trim();

            if (majorText.equalsIgnoreCase(major)) {

                String cohortText = getCell(i, cohortCol).getText().trim();

                return new TrainingProgramItem(majorText, cohortText);
            }
        }

        throw new RuntimeException("Không tìm thấy program với major: " + major);
    }
    public String getValidationMessage() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement message = wait.until(
                ExpectedConditions.visibilityOfElementLocated(messageLocator)
        );

        return message.getText().trim();
    }
    /* ================= SEARCH ================= */
    public TrainingProgramItem getRandomTrainingProgram() {
        ArrayList<TrainingProgramItem> programs = getAllPrograms();
        if (programs.isEmpty()) {
            throw new RuntimeException("Training program table is empty");
        }
        return programs.get(new Random().nextInt(programs.size()));
    }
    public void searchTrainingProgram(String keyword) {
        WebElement searchBox = wait.until(
                ExpectedConditions.visibilityOfElementLocated(searchBarLocator)
        );
        searchBox.clear();
        searchBox.sendKeys(keyword);
    }
    public String generateNonExistingKeyword() {
        return "Program_" + System.currentTimeMillis();
    }
    public boolean isTrainingProgramTableEmpty() {
        List<WebElement> rows = driver.findElements(tableRows);
        return rows.isEmpty();
    }

    public boolean verifySearchResultContainsKeyword(String keyword) {
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(tableRows));

        for (int i = 1; i <= getTotalTrainingProgram(); i++) {
            String username = getCell(i, getColumnIndex("CHUYÊN NGÀNH"))
                    .getText().toLowerCase();

            if (username.contains(keyword.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
    public String clickRandomMajor() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        List<WebElement> majors = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(
                        By.xpath("//tbody/tr/td[2]//button")
                )
        );

        if (majors.isEmpty()) {
            throw new RuntimeException("No major found in table");
        }

        Random random = new Random();
        WebElement randomMajor = majors.get(random.nextInt(majors.size()));

        String majorName = randomMajor.getText().trim();

        randomMajor.click();

        return majorName;
    }
    //
    private final By subjectCodeInput = By.xpath("//input[@placeholder='Nhập mã học phần']");
    private final By subjectNameInput = By.xpath("//input[@placeholder='Nhập tên học phần']");
    private final By creditInput = By.xpath("//input[@placeholder='Nhập số tín chỉ']");
    private final By subjectTypeDropdown = By.xpath("//button[@role='combobox']");
    private static final String SUBJECT_OPTION_XPATH =
            "//div[@role='option' and normalize-space(.)='%s']";
    /* ================= SUBJECT TABLE UTILS ================= */

    private static final List<String> SUBJECT_COLUMN_NAMES = List.of(
            "STT",
            "MÃ HỌC PHẦN",
            "TÊN HỌC PHẦN",
            "SỐ TÍN CHỈ",
            "BẮT BUỘC",
            "TỰ CHỌN"
    );

    private int getSubjectColumnIndex(String columnName) {
        return SUBJECT_COLUMN_NAMES.indexOf(columnName) + 1;
    }

    private WebElement getSubjectCell(int row, int column) {
        String xpath = String.format("//tbody/tr[%d]/td[%d]", row, column);
        return driver.findElement(By.xpath(xpath));
    }

    public int getTotalSubjects() {
        return driver.findElements(By.xpath("//tbody/tr")).size();
    }

    public ArrayList<SubjectItem> getAllSubjects() {
        ArrayList<SubjectItem> subjects = new ArrayList<>();

        for (int i = 1; i <= getTotalSubjects(); i++) {

            String subjectCode = getSubjectCell(i, getSubjectColumnIndex("MÃ HỌC PHẦN"))
                    .getText().trim();

            String subjectName = getSubjectCell(i, getSubjectColumnIndex("TÊN HỌC PHẦN"))
                    .getText().trim();

            // 🔥 FIX QUAN TRỌNG: lấy credit đúng cột
            String requiredCredit = getSubjectCell(i, getSubjectColumnIndex("BẮT BUỘC"))
                    .getText().trim();

            String electiveCredit = getSubjectCell(i, getSubjectColumnIndex("TỰ CHỌN"))
                    .getText().trim();

            String credit = !requiredCredit.isEmpty() ? requiredCredit : electiveCredit;

            // (optional) normalize nếu UI có "tín chỉ"
            credit = credit.replaceAll("[^0-9]", "");

            subjects.add(new SubjectItem(subjectCode, subjectName, credit));
        }

        return subjects;
    }

    public void addSubject(String subjectCode, String subjectName, String credit, String subjectType) {

        driver.findElement(addButtonLocator).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(subjectCodeInput))
                .sendKeys(subjectCode);

        driver.findElement(subjectNameInput).sendKeys(subjectName);

        driver.findElement(creditInput).sendKeys(credit);

        driver.findElement(subjectTypeDropdown).click();

        By subjectTypeOption = By.xpath(String.format(SUBJECT_OPTION_XPATH, subjectType));

        wait.until(ExpectedConditions.elementToBeClickable(subjectTypeOption)).click();

        driver.findElement(saveButton).click();
    }
    public void scrollToSubject(String subjectName) {

        List<WebElement> rows = driver.findElements(By.xpath("//tbody/tr"));

        for (WebElement row : rows) {

            String name = row.findElement(By.xpath("./td["
                            + getSubjectColumnIndex("TÊN HỌC PHẦN") + "]"))
                    .getText().trim();

            if (name.equals(subjectName)) {

                ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].scrollIntoView(true);", row
                );

                break;
            }
        }
    }
    private final By errorMessage = By.xpath("//p[@class='text-xs text-red-500']");

    public boolean isErrorMessageDisplayed(String expectedMessage) {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

            WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[contains(text(),'tồn tại trong chương trình đào tạo')]")
            ));

            String actual = error.getText().replaceAll("\\s+", " ").trim();

            return actual.contains(expectedMessage);
    }
    public ArrayList<String> getAllSubjectCodes() {

        ArrayList<String> subjectCodes = new ArrayList<>();

        List<WebElement> rows = driver.findElements(By.xpath("//tbody/tr"));

        for (WebElement row : rows) {

            String subjectCode = row.findElement(By.xpath("./td[2]"))
                    .getText()
                    .trim();

            subjectCodes.add(subjectCode);
        }

        return subjectCodes;
    }
    public String getRandomSubjectCode() {

        ArrayList<String> subjectCodes = getAllSubjectCodes();

        if (subjectCodes.isEmpty()) {
            throw new RuntimeException("Subject table is empty");
        }

        return subjectCodes.get(new Random().nextInt(subjectCodes.size()));
    }
    public String getImportErrorMessage() {
        return driver.findElement(importErrorMessage).getText();
    }
    public List<String> getAllMajorsInTable() {

        int majorColIndex = getColumnIndex("CHUYÊN NGÀNH");

        List<WebElement> rows = driver.findElements(By.xpath("//tbody/tr"));

        List<String> majors = new ArrayList<>();

        for (int i = 1; i <= rows.size(); i++) {
            String major = getCell(i, majorColIndex).getText().trim().toLowerCase();
            majors.add(major);
        }

        return majors;
    }
    public String randomMajorUnique() {

        wait.until(ExpectedConditions.elementToBeClickable(majorDropdownLocator)).click();

        List<WebElement> options = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(majorOptions)
        );

        List<String> existingMajors = getAllMajorsInTable();

        List<WebElement> filteredOptions = options.stream()
                .filter(opt -> {
                    String text = opt.getText().trim().toLowerCase();
                    return !existingMajors.contains(text);
                })
                .collect(Collectors.toList());

        if (filteredOptions.isEmpty()) {
            throw new RuntimeException("Tất cả major đã tồn tại trong table");
        }

        WebElement randomOption = filteredOptions.get(new Random().nextInt(filteredOptions.size()));

        String major = randomOption.getText().trim();

        randomOption.click();

        return major;
    }
    public void waitForSubjectVisible(String subjectName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(driver ->
                driver.getPageSource().contains(subjectName)
        );
    }
    public String getNoDataMessage() {
        WebElement message = driver.findElement(
                By.xpath("//td[@data-slot='table-cell']")
        );
        return message.getText().trim();
    }
    public String getRandomChuyenNganh() {
        List<WebElement> rows = driver.findElements(By.xpath("//tbody/tr"));

        if (rows.isEmpty()) {
            throw new RuntimeException("No data in table");
        }

        int randomRow = new Random().nextInt(rows.size()) + 1;

        int columnIndex = getColumnIndex("CHUYÊN NGÀNH");

        return getCell(randomRow, columnIndex).getText().trim();
    }

}

