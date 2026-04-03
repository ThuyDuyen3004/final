package test;

import common.BaseTest;
import io.qameta.allure.Description;
import models.Setting.StudentItem;
import org.testng.annotations.Test;
import utils.Constants;

import java.util.ArrayList;

public class Students extends BaseTest {

    String createdMSSV;

    @Test(priority = 1)
    public void STM001_VerifyStudentDetailsMatchBetweenFormAndTable() throws InterruptedException {

        Thread.sleep(1000);
        loginPage.login(Constants.EMAIL, Constants.PASSWORD);

        Thread.sleep(2000);
        studentsPage.goToStudentsPage();

        Thread.sleep(1000);
        studentsPage.openAddStudentForm();

        String mssv = studentsPage.randomMSSV();
        createdMSSV = mssv;
        String fullName = studentsPage.randomFullName();

        ArrayList<StudentItem> expectedStudents = new ArrayList<>();
        expectedStudents.add(new StudentItem(mssv, fullName));

        studentsPage.addStudent(
                mssv,
                fullName,
                "48K14",
                "15/03/2004",
                "test"
        );

        Thread.sleep(1000);

        studentsPage.filterByClass("48K14");

        Thread.sleep(1000);
        studentsPage.scrollToStudent(mssv);

        ArrayList<StudentItem> actualStudents = studentsPage.getAllStudents();

        softAssert.assertTrue(
                actualStudents.containsAll(expectedStudents),
                "Student details do not match between Add Student form and Student table"
        );

        softAssert.assertAll();
    }

    @Test(priority = 2)
    public void STM002_VerifyCannotAddStudent_WhenIDEmpty() throws InterruptedException {

        Thread.sleep(1000);
        loginPage.login(Constants.EMAIL, Constants.PASSWORD);

        Thread.sleep(2000);
        studentsPage.goToStudentsPage();

        Thread.sleep(1000);
        studentsPage.openAddStudentForm();

        String fullName = studentsPage.randomFullName();

        studentsPage.addStudent(
                "",
                fullName,
                "48K14",
                "15/03/2004",
                "test"
        );

        Thread.sleep(1000);

        String actualMessage = studentsPage.getValidationMessage();

        softAssert.assertEquals(
                actualMessage,
                "Vui lòng điền đầy đủ các trường bắt buộc",
                "Validation message is incorrect"
        );

        softAssert.assertAll();
    }

    @Test(priority = 3)
    public void STM003_VerifyCannotAddStudent_WhenNameEmpty() throws InterruptedException {

        Thread.sleep(1000);
        loginPage.login(Constants.EMAIL, Constants.PASSWORD);

        Thread.sleep(2000);
        studentsPage.goToStudentsPage();

        Thread.sleep(1000);
        studentsPage.openAddStudentForm();

        String mssv = studentsPage.randomMSSV();

        studentsPage.addStudent(
                mssv,
                "",
                "48K21.1",
                "10/10/2004",
                "test"
        );

        String actualMessage = studentsPage.getValidationMessage();

        softAssert.assertEquals(
                actualMessage,
                "Vui lòng điền đầy đủ các trường bắt buộc"
        );

        softAssert.assertAll();
    }

    @Test(priority = 4)
    public void STM004_VerifyCannotAddStudent_WhenClassEmpty() throws InterruptedException {

        Thread.sleep(1000);
        loginPage.login(Constants.EMAIL, Constants.PASSWORD);

        Thread.sleep(2000);
        studentsPage.goToStudentsPage();

        Thread.sleep(1000);
        studentsPage.openAddStudentForm();

        String mssv = studentsPage.randomMSSV();
        String fullName = studentsPage.randomFullName();

        studentsPage.addStudent(
                mssv,
                fullName,
                "",
                "10/10/2004",
                "test"
        );

        String actualMessage = studentsPage.getValidationMessage();

        softAssert.assertEquals(
                actualMessage,
                "Vui lòng điền đầy đủ các trường bắt buộc"
        );

        softAssert.assertAll();
    }

    @Test(priority = 5)
    public void STM005_VerifyCannotAddStudent_WhenDOBEmpty() throws InterruptedException {

        Thread.sleep(1000);
        loginPage.login(Constants.EMAIL, Constants.PASSWORD);

        Thread.sleep(2000);
        studentsPage.goToStudentsPage();

        Thread.sleep(1000);
        studentsPage.openAddStudentForm();

        String mssv = studentsPage.randomMSSV();
        String fullName = studentsPage.randomFullName();

        studentsPage.addStudent(
                mssv,
                fullName,
                "48K21.1",
                "",
                "test"
        );

        String actualMessage = studentsPage.getValidationMessage();

        softAssert.assertEquals(
                actualMessage,
                "Vui lòng điền đầy đủ các trường bắt buộc"
        );

        softAssert.assertAll();
    }

    @Test(priority = 6)
    public void STM006_VerifyCannotAddStudent_WhenIDDuplicated() throws InterruptedException {

        Thread.sleep(1000);
        loginPage.login(Constants.EMAIL, Constants.PASSWORD);

        Thread.sleep(2000);
        studentsPage.goToStudentsPage();

        Thread.sleep(1000);
        studentsPage.openAddStudentForm();

        String fullName = studentsPage.randomFullName();

        studentsPage.addStudent(
                createdMSSV,
                fullName,
                "48K14",
                "10/10/2004",
                "test"
        );

        Thread.sleep(1000);

        String actualMessage = studentsPage.getValidationMessage();

        softAssert.assertEquals(
                actualMessage,
                "MSSV đã tồn tại trong hệ thống",
                "Duplicate MSSV validation message incorrect"
        );

        softAssert.assertAll();
    }

    @Test(priority = 7)
    public void STM007_VerifyDeleteStudentSuccessfully() throws InterruptedException {

        Thread.sleep(1000);
        loginPage.login(Constants.EMAIL, Constants.PASSWORD);

        Thread.sleep(2000);
        studentsPage.goToStudentsPage();

        Thread.sleep(1000);
        studentsPage.filterByClass("48K21.1");

        Thread.sleep(1000);

        String deletedMSSV = studentsPage.randomClickIconAndDeleteStudent();

        boolean isStillExist = studentsPage.isValueDisplayedInTable(deletedMSSV);

        softAssert.assertFalse(
                isStillExist,
                "Student with MSSV " + deletedMSSV + " still exists after deletion"
        );

        softAssert.assertAll();
    }

//    @Test(priority = 8)
//    public void STM008_VerifyEditStudentIDSuccessfully() throws InterruptedException {
//
//        Thread.sleep(1000);
//        loginPage.login(Constants.EMAIL, Constants.PASSWORD);
//
//        Thread.sleep(3000);
//        studentsPage.goToStudentsPage();
//
//        Thread.sleep(1000);
//        studentsPage.filterByClass("48K14");
//
//        Thread.sleep(2000);
//
//        studentsPage.randomClickIconAndEditStudent();
//
//        String newMSSV = studentsPage.randomMSSV();
//
//        Thread.sleep(1000);
//
//        studentsPage.updateStudent(
//                newMSSV,
//                null,
//                null,
//                null,
//                null
//        );
//
//        Thread.sleep(2000);
//
//        studentsPage.scrollToBottom();
//
//        boolean isUpdated = studentsPage.isValueDisplayedInTable(newMSSV);
//
//        softAssert.assertTrue(
//                isUpdated,
//                "Student ID was not updated successfully"
//        );
//
//        softAssert.assertAll();
//    }
//
//    @Test(priority = 9)
//    public void STM009_VerifyEditStudentNameSuccessfully() throws InterruptedException {
//
//        Thread.sleep(1000);
//        loginPage.login(Constants.EMAIL, Constants.PASSWORD);
//
//        Thread.sleep(3000);
//        studentsPage.goToStudentsPage();
//
//        Thread.sleep(1000);
//        studentsPage.filterByClass("48K14");
//
//        Thread.sleep(2000);
//
//        String mssv = studentsPage.randomClickIconAndEditStudent();
//
//        String expectedName = studentsPage.randomFullName();
//
//        Thread.sleep(2000);
//
//        studentsPage.updateStudent(
//                null,
//                expectedName,
//                null,
//                null,
//                null
//        );
//
//        Thread.sleep(2000);
//
//        studentsPage.scrollToBottom();
//
//        String actualName = studentsPage.getStudentNameByMSSV(mssv);
//
//        softAssert.assertEquals(
//                actualName,
//                expectedName,
//                "Student name was not updated successfully"
//        );
//
//        softAssert.assertAll();
//    }

//    @Test(priority = 10)
//    public void STM010_VerifyEditStudentClassSuccessfully() throws InterruptedException {
//
//        Thread.sleep(1000);
//        loginPage.login(Constants.EMAIL, Constants.PASSWORD);
//
//        Thread.sleep(3000);
//        studentsPage.goToStudentsPage();
//
//        Thread.sleep(1000);
//        studentsPage.filterByClass("48K14");
//
//        Thread.sleep(2000);
//
//        String mssv = studentsPage.randomClickIconAndEditStudent();
//
//        String newClass = "48K21.1";
//
//        studentsPage.updateStudent(
//                null,
//                null,
//                newClass,
//                null,
//                null
//        );
//
//        Thread.sleep(2000);
//
//        studentsPage.filterByClass("48K21.1");
//
//        Thread.sleep(2000);
//
//        boolean isStudentExist = studentsPage.isStudentDisplayed(mssv);
//
//        softAssert.assertTrue(
//                isStudentExist,
//                "Student was not moved to new class successfully"
//        );
//
//        softAssert.assertAll();
//    }
//
//    @Test(priority = 11)
//    public void STM011_VerifyEditStudentDOBSuccessfully() throws InterruptedException {
//
//        Thread.sleep(1000);
//        loginPage.login(Constants.EMAIL, Constants.PASSWORD);
//
//        Thread.sleep(3000);
//        studentsPage.goToStudentsPage();
//
//        Thread.sleep(1000);
//        studentsPage.filterByClass("48K14");
//
//        Thread.sleep(2000);
//
//        String mssv = studentsPage.randomClickIconAndEditStudent();
//
//        Thread.sleep(1000);
//
//        String newDOB = "20/01/2001";
//
//        studentsPage.updateStudent(
//                null,
//                null,
//                null,
//                newDOB,
//                null
//        );
//
//        Thread.sleep(2000);
//
//        studentsPage.scrollToStudent(mssv);
//
//        boolean isUpdated = studentsPage.isValueDisplayedInTable(newDOB);
//
//        softAssert.assertTrue(
//                isUpdated,
//                "Student DOB was not updated successfully"
//        );
//
//        softAssert.assertAll();
//    }
//
//    @Description("User can search student by MSSV")
//    @Test(priority = 12)
//    public void STM012_VerifyUserCanSearchStudentByMSSV() throws InterruptedException {
//
//        Thread.sleep(1000);
//        loginPage.login(Constants.EMAIL, Constants.PASSWORD);
//
//        Thread.sleep(1000);
//        studentsPage.goToStudentsPage();
//
//        Thread.sleep(1000);
//        studentsPage.filterByClass("48K14");
//
//        Thread.sleep(1000);
//
//        StudentItem randomStudent = studentsPage.getRandomStudent();
//        String mssv = randomStudent.getMssv();
//
//        studentsPage.searchStudent(mssv);
//
//        Thread.sleep(1000);
//
//        softAssert.assertTrue(
//                studentsPage.verifySearchResultContainsMSSV(mssv),
//                "Search result does not contain MSSV"
//        );
//
//        softAssert.assertAll();
//    }

    @Description("User can search student with non-existing MSSV")
    @Test(priority = 13)
    public void STM013_VerifySearchStudent_NoDataDisplayed() throws InterruptedException {

        Thread.sleep(1000);
        loginPage.login(Constants.EMAIL, Constants.PASSWORD);

        Thread.sleep(2000);
        studentsPage.goToStudentsPage();

        Thread.sleep(1000);
        studentsPage.filterByClass("48K14");

        Thread.sleep(1000);

        String invalidKeyword = studentsPage.generateNonExistingKeyword();

        studentsPage.searchStudent(invalidKeyword);

        Thread.sleep(1000);

        softAssert.assertTrue(
                studentsPage.isStudentTableEmpty(),
                "Student table should be empty but still has data"
        );

        softAssert.assertAll();
    }

    @Description("User can import student list from file")
    @Test(priority = 14)
    public void STM014_VerifyUserCanImportStudentFromFileValid() throws Exception {

        Thread.sleep(1000);
        loginPage.login(Constants.EMAIL, Constants.PASSWORD);

        Thread.sleep(1000);
        studentsPage.goToStudentsPage();

        Thread.sleep(1000);

        studentsPage.goToImportForm(
                "\"C:\\Users\\ACER\\Downloads\\danh_sach_sinh_vien.csv\""
        );

        Thread.sleep(2000);

        softAssert.assertAll();
    }

    @Description("User cannot import student when file format is invalid")
    @Test(priority = 15)
    public void STM015_VerifyCannotImport_InvalidFileFormat() throws Exception {

        Thread.sleep(1000);
        loginPage.login(Constants.EMAIL, Constants.PASSWORD);

        Thread.sleep(1000);
        studentsPage.goToStudentsPage();

        Thread.sleep(1000);

        studentsPage.goToImportForm(
                "\"C:\\Users\\ACER\\Downloads\\TAI-LIEU-KHOA-IELTS-READING-ONLINE-VIDEO-B.pdf\""
        );

        Thread.sleep(1000);

        softAssert.assertEquals(
                studentsPage.getImportErrorMessage(),
                "Định dạng file không hợp lệ, chỉ chấp nhận .csv"
        );

        softAssert.assertAll();
    }

    @Description("User cannot import student when file size exceeds 50MB")
    @Test(priority = 16)
    public void STM016_VerifyCannotImport_FileSizeExceeds50MB() throws Exception {

        Thread.sleep(1000);
        loginPage.login(Constants.EMAIL, Constants.PASSWORD);

        Thread.sleep(3000);
        studentsPage.goToStudentsPage();

        Thread.sleep(2000);

        studentsPage.goToImportForm(
                "\"C:\\Users\\ACER\\Downloads\\subjects_100MB.csv\""
        );

        Thread.sleep(1000);

        softAssert.assertEquals(
                studentsPage.getImportErrorMessage(),
                "File vượt quá dung lượng cho phép. Giới hạn tối đa: 50 MB."
        );

        softAssert.assertAll();
    }

}