package test;

import com.github.javafaker.Faker;
import common.BaseTest;
import jdk.jfr.Description;
import models.Setting.SubjectItem;
import models.Setting.TrainingProgramItem;
import org.testng.annotations.Test;
import utils.Constants;

import java.util.ArrayList;

public class TrainingProgram extends BaseTest {

    @Test(priority = 1)
    public void TPG001_VerifyTrainingProgramAddedSuccessfully() throws InterruptedException {
        Thread.sleep(2000);
        loginPage.login(Constants.EMAIL, Constants.PASSWORD);
        Thread.sleep(2000);
        trainingProgramPage.goToTrainingProgramPage();
        Thread.sleep(3000);

        String major = "Tin học quản lý";
        String cohort = "46";

        TrainingProgramItem addedProgram =
                trainingProgramPage.addTrainingProgram(major, cohort);

        trainingProgramPage.scrollToTrainingProgram(major);

        TrainingProgramItem actualProgram =
                trainingProgramPage.getProgramByMajor(major);

        softAssert.assertEquals(
                actualProgram,
                addedProgram,
                "Training program row does not match added data"
        );

        softAssert.assertAll();
    }
    @Test(priority = 2)
    public void TPG002_VerifyCannotAddTrainingProgramWhenMajorEmpty() throws InterruptedException {
        Thread.sleep(2000);
        loginPage.login(Constants.EMAIL, Constants.PASSWORD);
        Thread.sleep(2000);
        trainingProgramPage.goToTrainingProgramPage();
        Thread.sleep(2000);
        trainingProgramPage.openAddTrainingProgramForm();
        Thread.sleep(2000);
        trainingProgramPage.selectCohortByText("46");
        trainingProgramPage.clickCohortLabel();
        trainingProgramPage.clickSave();

        String actualError = trainingProgramPage.getErrorMessage();

        softAssert.assertEquals(
                actualError,
                "Vui lòng chọn chuyên ngành",
                "Error message is incorrect"
        );

        softAssert.assertAll();
    }
    @Test(priority = 3)
    public void TPG003_VerifyCannotAddTrainingProgramWhenCohortEmpty() throws InterruptedException {

        Thread.sleep(2000);
        loginPage.login(Constants.EMAIL, Constants.PASSWORD);

        Thread.sleep(2000);
        trainingProgramPage.goToTrainingProgramPage();

        Thread.sleep(2000);
        trainingProgramPage.openAddTrainingProgramForm();

        Thread.sleep(2000);

        trainingProgramPage.selectMajorByText("Tin học quản lý");

        trainingProgramPage.clickCohortLabel();

        trainingProgramPage.clickSave();

        String actualError = trainingProgramPage.getErrorMessage();

        softAssert.assertEquals(
                actualError,
                "Vui lòng chọn ít nhất 1 khóa áp dụng",
                "Error message is incorrect"
        );

        softAssert.assertAll();
    }
    @Test
    public void TPG004_VerifySearchByMajorSuccessfully() throws InterruptedException {

        Thread.sleep(1000);
        loginPage.login(Constants.EMAIL, Constants.PASSWORD);

        Thread.sleep(2000);
        trainingProgramPage.goToTrainingProgramPage();

        Thread.sleep(3000);
        TrainingProgramItem program = trainingProgramPage.getRandomTrainingProgram();

        String major = program.getMajor();

        trainingProgramPage.searchTrainingProgram(major);

        Thread.sleep(2000);

        boolean isMatch = trainingProgramPage.getAllPrograms()
                .stream()
                .allMatch(p -> p.getMajor().toLowerCase().contains(major.toLowerCase()));

        softAssert.assertTrue(
                isMatch,
                "Search result does not match searched major"
        );

        softAssert.assertAll();
    }
//    @Description("User can search by training program name")
//    @Test
//    public void UMG_18_UserCanSearchByTrainingProgram() throws InterruptedException {
//
//        Thread.sleep(1000);
//
//        loginPage.login(Constants.EMAIL, Constants.PASSWORD);
//
//        Thread.sleep(2000);
//
//        trainingProgramPage.goToTrainingProgramPage();
//
//        Thread.sleep(5000);
//
//        TrainingProgramItem program = trainingProgramPage.getRandomTrainingProgram();
//        Thread.sleep(2000);
//        String programName = program.getProgramName();
//
//        trainingProgramPage.searchTrainingProgram(programName);
//
//        Thread.sleep(2000);
//
//        softAssert.assertTrue(
//                trainingProgramPage.verifySearchResultContainsKeyword(programName),
//                "Search result does not contain searched training program name"
//        );
//
//        softAssert.assertAll();
//    }
@Test
public void TPG005_VerifySearchWithNonExistingKeywordReturnsEmpty() throws InterruptedException {

    Thread.sleep(1000);
    loginPage.login(Constants.EMAIL, Constants.PASSWORD);

    Thread.sleep(2000);
    trainingProgramPage.goToTrainingProgramPage();

    Thread.sleep(3000);

    // 👉 tạo keyword random chắc chắn không tồn tại
    String keyword = "zzz_not_exist_" + System.currentTimeMillis();

    System.out.println("Search keyword: " + keyword);

    // 👉 search
    trainingProgramPage.searchTrainingProgram(keyword);

    Thread.sleep(2000);

    // 👉 lấy data table
    ArrayList<TrainingProgramItem> actualPrograms =
            trainingProgramPage.getAllPrograms();

    System.out.println("Actual Programs:");
    actualPrograms.forEach(System.out::println);

    // 👉 verify table rỗng
    softAssert.assertTrue(
            actualPrograms.isEmpty(),
            "Expected empty table but found data"
    );

    softAssert.assertAll();
}
    @Description("User can search training program with non-existing name")
    @Test
    public void UMG_19_SearchTrainingProgram_NoDataDisplayed() throws InterruptedException {

        Thread.sleep(1000);
        loginPage.login(Constants.EMAIL, Constants.PASSWORD);

        Thread.sleep(1000);
        trainingProgramPage.goToTrainingProgramPage();

        Thread.sleep(3000);

        String invalidKeyword = trainingProgramPage.generateNonExistingKeyword();

        trainingProgramPage.searchTrainingProgram(invalidKeyword);

        Thread.sleep(1000);

        String actualMessage = trainingProgramPage.getNoDataMessage();

        softAssert.assertEquals(
                actualMessage,
                "Chưa có chương trình đào tạo nào",
                "No data message is incorrect"
        );

        softAssert.assertAll();
    }
//    @Description("Verify subject added successfully to training program")
//    @Test
//    public void TPG002_VerifySubjectAddedSuccessfully() throws InterruptedException {
//
//        Faker faker = new Faker();
//
//        Thread.sleep(1000);
//
//        loginPage.login(Constants.EMAIL, Constants.PASSWORD);
//
//        Thread.sleep(1000);
//
//        trainingProgramPage.goToTrainingProgramPage();
//
//        Thread.sleep(1000);
//
//        // chọn random major
//        trainingProgramPage.clickRandomMajor();
//
//        Thread.sleep(1000);
//
//        String subjectCode = "HP_" + faker.number().numberBetween(100, 999);
//        String subjectName = faker.educator().course();
//        String credit = String.valueOf(faker.number().numberBetween(1, 5));
//        String subjectType = "Bắt buộc";
//
//        ArrayList<SubjectItem> expectedSubjects = new ArrayList<>();
//
//        trainingProgramPage.addSubject(subjectCode, subjectName, credit, subjectType);
//
//        expectedSubjects.add(new SubjectItem(subjectCode, subjectName, credit));
//
//        Thread.sleep(1000);
//
//        trainingProgramPage.scrollToSubject(subjectName);
//
//        ArrayList<SubjectItem> actualSubjects =
//                trainingProgramPage.getAllSubjects();
//
//        softAssert.assertTrue(
//                actualSubjects.containsAll(expectedSubjects),
//                "Subject details do not match between Add form and table"
//        );
//
//        softAssert.assertAll();
//    }
    @Description("Verify cannot add subject when subject code is empty")
    @Test
    public void TPG003_VerifyCannotAddSubject_WhenCodeEmpty() throws InterruptedException {

        Faker faker = new Faker();
        Thread.sleep(1000);
        loginPage.login(Constants.EMAIL, Constants.PASSWORD);
        Thread.sleep(5000);
        trainingProgramPage.goToTrainingProgramPage();
        Thread.sleep(1000);
        trainingProgramPage.clickRandomMajor();

        String subjectName = faker.educator().course();
        String credit = "3";
        Thread.sleep(1000);
        trainingProgramPage.addSubject("", subjectName, credit, "Bắt buộc");

        softAssert.assertTrue(
                trainingProgramPage.isErrorMessageDisplayed("Vui lòng nhập mã học phần"),
                "Error message not displayed when subject code is empty"
        );

        softAssert.assertAll();
    }
    @Description("Verify cannot add subject when subject name is empty")
    @Test
    public void TPG004_VerifyCannotAddSubject_WhenNameEmpty() throws InterruptedException {

        Faker faker = new Faker();
        Thread.sleep(1000);
        loginPage.login(Constants.EMAIL, Constants.PASSWORD);
        Thread.sleep(1000);
        trainingProgramPage.goToTrainingProgramPage();
        Thread.sleep(5000);
        trainingProgramPage.clickRandomMajor();

        String subjectCode = "HP_" + faker.number().numberBetween(100,999);
        String credit = "3";
        Thread.sleep(1000);
        trainingProgramPage.addSubject(subjectCode, "", credit, "Bắt buộc");

        softAssert.assertTrue(
                trainingProgramPage.isErrorMessageDisplayed("Vui lòng nhập tên học phần"),
                "Error message not displayed when subject name is empty"
        );

        softAssert.assertAll();
    }
    @Description("Verify cannot add subject when credit is empty")
    @Test
    public void TPG005_VerifyCannotAddSubject_WhenCreditEmpty() throws InterruptedException {

        Faker faker = new Faker();
        Thread.sleep(1000);
        loginPage.login(Constants.EMAIL, Constants.PASSWORD);
        Thread.sleep(1000);
        trainingProgramPage.goToTrainingProgramPage();
        Thread.sleep(5000);
        trainingProgramPage.clickRandomMajor();

        String subjectCode = "HP_" + faker.number().numberBetween(100,999);
        String subjectName = faker.educator().course();
        Thread.sleep(1000);
        trainingProgramPage.addSubject(subjectCode, subjectName, "", "Bắt buộc");

        softAssert.assertTrue(
                trainingProgramPage.isErrorMessageDisplayed("Vui lòng nhập số tín chỉ hợp lệ"),
                "Error message not displayed when credit is empty"
        );

        softAssert.assertAll();
    }
    @Description("Verify cannot add subject with duplicated subject code")
    @Test
    public void TPG006_VerifyCannotAddSubject_WhenCodeDuplicated() throws InterruptedException {

        Thread.sleep(1000);

        loginPage.login(Constants.EMAIL, Constants.PASSWORD);

        Thread.sleep(1000);

        trainingProgramPage.goToTrainingProgramPage();

        Thread.sleep(5000);

        trainingProgramPage.clickRandomMajor();

        Thread.sleep(2000);

        String subjectCode = trainingProgramPage.getRandomSubjectCode();

        Thread.sleep(1000);

        trainingProgramPage.addSubject(
                subjectCode,
                "Test Duplicate Subject",
                "3",
                "Bắt buộc"
        );

        softAssert.assertTrue(
                trainingProgramPage.isErrorMessageDisplayed("Mã học phần đã tồn tại"),
                "Error message not displayed when subject code duplicated"
        );

        softAssert.assertAll();
    }
    @Description("Verify cannot add subject when credit is invalid")
    @Test
    public void TPG007_VerifyCannotAddSubject_WhenCreditInvalid() throws InterruptedException {

        Faker faker = new Faker();
        Thread.sleep(1000);
        loginPage.login(Constants.EMAIL, Constants.PASSWORD);
        Thread.sleep(2000);
        trainingProgramPage.goToTrainingProgramPage();
        Thread.sleep(5000);
        trainingProgramPage.clickRandomMajor();

        String subjectCode = "HP_" + faker.number().numberBetween(100,999);
        String subjectName = faker.educator().course();
        Thread.sleep(2000);
        trainingProgramPage.addSubject(subjectCode, subjectName, "abc", "Bắt buộc");

        softAssert.assertTrue(
                trainingProgramPage.isErrorMessageDisplayed("Vui lòng nhập số tín chỉ hợp lệ"),
                "Error message not displayed when credit invalid"
        );

        softAssert.assertAll();
    }
    @Description("User can import training program list from file")
    @Test(priority = 14)
    public void TP014_VerifyUserCanImportTrainingProgramFromFileValid() throws Exception {

        Thread.sleep(1000);
        loginPage.login(Constants.EMAIL, Constants.PASSWORD);

        Thread.sleep(2000);
        trainingProgramPage.goToTrainingProgramPage();

        Thread.sleep(5000);
        trainingProgramPage.clickRandomMajor();
        Thread.sleep(1000);
        trainingProgramPage.goToImportForm(
                "\"C:\\Users\\ACER\\Downloads\\subjects_40MB.csv\""
        );

        Thread.sleep(2000);

        softAssert.assertAll();
    }
    @Description("User cannot import training program when file format is invalid")
    @Test(priority = 15)
    public void TP015_VerifyCannotImport_InvalidFileFormat() throws Exception {

        Thread.sleep(1000);
        loginPage.login(Constants.EMAIL, Constants.PASSWORD);

        Thread.sleep(1000);
        trainingProgramPage.goToTrainingProgramPage();
        Thread.sleep(5000);
        trainingProgramPage.clickRandomMajor();
        Thread.sleep(1000);

        trainingProgramPage.goToImportForm(
                "\"C:\\Users\\ACER\\Downloads\\Collocation practice.docx\""
        );

        Thread.sleep(1000);

        softAssert.assertEquals(
                trainingProgramPage.getImportErrorMessage(),
                "Định dạng file không hợp lệ, chỉ chấp nhận .csv"
        );

        softAssert.assertAll();
    }@Description("User cannot import training program when file size exceeds 50MB")
    @Test(priority = 16)
    public void TP016_VerifyCannotImport_FileSizeExceeds50MB() throws Exception {

        Thread.sleep(1000);
        loginPage.login(Constants.EMAIL, Constants.PASSWORD);

        Thread.sleep(3000);
        trainingProgramPage.goToTrainingProgramPage();
        Thread.sleep(5000);
        trainingProgramPage.clickRandomMajor();
        Thread.sleep(1000);

        trainingProgramPage.goToImportForm(
                "\"C:\\Users\\ACER\\Downloads\\subjects_100MB.csv\""
        );

        Thread.sleep(1000);

        softAssert.assertEquals(
                trainingProgramPage.getImportErrorMessage(),
                "File vượt quá dung lượng cho phép. Giới hạn tối đa: 50 MB."
        );

        softAssert.assertAll();
    }
}
