package test;

import com.github.javafaker.Faker;
import common.BaseTest;
import jdk.jfr.Description;
import models.TrainingProgramItem;
import org.testng.annotations.Test;


public class TrainingProgram extends BaseTest {

    /* ================= TRAINING PROGRAM ================= */

    @Test(priority = 1)
    public void TPG001_VerifyTrainingProgramAddedSuccessfully() throws InterruptedException {
        trainingProgramPage.goToTrainingProgramPage();
        Thread.sleep(3000);

        String major = "Tin học quản lý";
        String cohort = "48";

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
        trainingProgramPage.goToTrainingProgramPage();
        Thread.sleep(2000);
        trainingProgramPage.openAddTrainingProgramForm();
        Thread.sleep(2000);
        trainingProgramPage.selectCohortByText("48");
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

    @Test(priority = 4)
    public void TPG004_UserCanSearchByTrainingProgram() throws InterruptedException {

        trainingProgramPage.goToTrainingProgramPage();

        Thread.sleep(5000);

        TrainingProgramItem program = trainingProgramPage.getRandomTrainingProgram();

        Thread.sleep(2000);

        String programName = program.getMajor();

        trainingProgramPage.searchTrainingProgram(programName);

        Thread.sleep(2000);

        softAssert.assertTrue(
                trainingProgramPage.verifySearchResultContainsKeyword(programName),
                "Search result does not contain searched training program name"
        );

        softAssert.assertAll();
    }

    @Test(priority = 5)
    public void TPG005_VerifySearchWithNonExistingKeywordReturnsEmpty() throws InterruptedException {

        trainingProgramPage.goToTrainingProgramPage();

        Thread.sleep(3000);

        String keyword = "zzz_not_exist_" + System.currentTimeMillis();

        System.out.println("Search keyword: " + keyword);

        trainingProgramPage.searchTrainingProgram(keyword);

        Thread.sleep(2000);

        String actualMessage = trainingProgramPage.getNoDataMessage();
        String expectedMessage = "Không có chương trình đào tạo nào";

        softAssert.assertEquals(
                actualMessage,
                expectedMessage,
                "No data message is incorrect"
        );

        softAssert.assertAll();
    }

//    /* ================= SUBJECT ================= */

//    @Description("Verify subject added successfully to training program")
//    @Test(priority = 6)
//    public void TPG006_VerifySubjectAddedSuccessfully() throws InterruptedException {
//
//        Faker faker = new Faker();
//
//        trainingProgramPage.goToTrainingProgramPage();
//
//        trainingProgramPage.clickRandomMajor();
//
//        String subjectCode = "HP_" + faker.number().numberBetween(100, 999);
//        String subjectName = faker.educator().course();
//        String credit = String.valueOf(faker.number().numberBetween(1, 5));
//        String subjectType = "Bắt buộc";
//
//        trainingProgramPage.addSubject(subjectCode, subjectName, credit, subjectType);
//    }

    @Description("Verify cannot add subject when subject code is empty")
    @Test(priority = 7)
    public void TPG007_VerifyCannotAddSubject_WhenCodeEmpty() throws InterruptedException {

        Faker faker = new Faker();

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
    @Test(priority = 8)
    public void TPG008_VerifyCannotAddSubject_WhenNameEmpty() throws InterruptedException {

        Faker faker = new Faker();
        Thread.sleep(2000);

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
    @Test(priority = 9)
    public void TPG009_VerifyCannotAddSubject_WhenCreditEmpty() throws InterruptedException {

        Faker faker = new Faker();

        Thread.sleep(1000);
        trainingProgramPage.goToTrainingProgramPage();
        Thread.sleep(5000);
        trainingProgramPage.clickRandomMajor();

        String subjectCode = "MIS30018";
        String subjectName = faker.educator().course();
        Thread.sleep(1000);
        trainingProgramPage.addSubject(subjectCode, subjectName, "", "Bắt buộc");

        softAssert.assertTrue(
                trainingProgramPage.isErrorMessageDisplayed("Vui lòng nhập số tín chỉ hợp lệ"),
                "Error message not displayed when credit is empty"
        );

        softAssert.assertAll();
    }

    @Description("Verify cannot add subject when credit is invalid")
    @Test(priority = 10)
    public void TPG011_VerifyCannotAddSubject_WhenCreditInvalid() throws InterruptedException {

        Faker faker = new Faker();
        Thread.sleep(1000);

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

    /* ================= IMPORT ================= */

    @Description("User can import training program list from file")
    @Test(priority = 11)
    public void TPG012_VerifyUserCanImportTrainingProgramFromFileValid() throws Exception {

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
    @Test(priority = 12)
    public void TPG013_VerifyCannotImport_InvalidFileFormat() throws Exception {

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
    }

    @Description("User cannot import training program when file size exceeds 50MB")
    @Test(priority = 13)
    public void TPG014_VerifyCannotImport_FileSizeExceeds50MB() throws Exception {

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