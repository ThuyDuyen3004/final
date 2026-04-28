package test;

import common.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Issue;
import models.StudentItem;
import org.testng.annotations.Test;

import java.util.ArrayList;

public class Students extends BaseTest {

    String createdMSSV;

    @Test(priority = 1)
    public void STM001_VerifyStudentDetailsMatchBetweenFormAndTable() throws InterruptedException {

        studentsPage.goToStudentsPage();

        studentsPage.openAddStudentForm();

        String mssv = studentsPage.randomMSSV();
        createdMSSV = mssv;
        String fullName = studentsPage.randomFullName();

        ArrayList<StudentItem> expectedStudents = new ArrayList<>();
        expectedStudents.add(new StudentItem(mssv, fullName));

        studentsPage.addStudent(
                mssv,
                fullName,
                "48K21.1",
                "15/03/2004",
                "test"
        );

        Thread.sleep(1000);

        studentsPage.filterByClass("48K21.1");

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


        studentsPage.goToStudentsPage();

        studentsPage.openAddStudentForm();

        String fullName = studentsPage.randomFullName();

        studentsPage.addStudent(
                "",
                fullName,
                "48K21.1",
                "15/03/2004",
                "test"
        );


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

        studentsPage.goToStudentsPage();
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

        studentsPage.goToStudentsPage();

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

        studentsPage.goToStudentsPage();

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

        studentsPage.goToStudentsPage();

        studentsPage.openAddStudentForm();

        String fullName = studentsPage.randomFullName();

        studentsPage.addStudent(
                createdMSSV,
                fullName,
                "48K21.1",
                "10/10/2004",
                "test"
        );


        String actualMessage = studentsPage.getValidationMessage();

        softAssert.assertEquals(
                actualMessage,
                "MSSV đã tồn tại trong hệ thống",
                "Duplicate MSSV validation message incorrect"
        );

        softAssert.assertAll();
    }
    @Issue("S001")
    @Test(priority = 7)
    public void STM007_VerifyDeleteStudentSuccessfully() throws InterruptedException {

        studentsPage.goToStudentsPage();

        String deletedMSSV = studentsPage.randomClickIconAndDeleteStudent();

        Thread.sleep(2000);

        studentsPage.searchStudent(deletedMSSV);

        boolean isStillExist = studentsPage.verifySearchResultContainsMSSV(deletedMSSV);

        softAssert.assertFalse(
                isStillExist,
                "Student with MSSV " + deletedMSSV + " still exists after deletion"
        );

        softAssert.assertAll();
        //

    }
    @Test(priority = 8)
    public void STM0021_VerifyCannotAddStudent_WhenIDEmpty() throws InterruptedException {


        studentsPage.goToStudentsPage();

        studentsPage.openAddStudentForm();

        String fullName = studentsPage.randomFullName();

        studentsPage.addStudent(
                "",
                fullName,
                "48K21.1",
                "15/03/2004",
                "test"
        );


        String actualMessage = studentsPage.getValidationMessage();

        softAssert.assertEquals(
                actualMessage,
                "Vui lòng điền đầy đủ các trường bắt buộc",
                "Validation message is incorrect"
        );

        softAssert.assertAll();
    }

    @Test(priority = 9)
    public void STM0031_VerifyCannotAddStudent_WhenNameEmpty() throws InterruptedException {

        studentsPage.goToStudentsPage();
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

    @Test(priority = 10)
    public void STM0041_VerifyCannotAddStudent_WhenClassEmpty() throws InterruptedException {

        studentsPage.goToStudentsPage();

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

    @Test(priority = 11)
    public void STM0051_VerifyCannotAddStudent_WhenDOBEmpty() throws InterruptedException {

        studentsPage.goToStudentsPage();

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

    @Test(priority = 12)
    public void STM0061_VerifyCannotAddStudent_WhenIDDuplicated() throws InterruptedException {

        studentsPage.goToStudentsPage();

        studentsPage.openAddStudentForm();

        String fullName = studentsPage.randomFullName();

        studentsPage.addStudent(
                createdMSSV,
                fullName,
                "48K21.1",
                "10/10/2004",
                "test"
        );


        String actualMessage = studentsPage.getValidationMessage();

        softAssert.assertEquals(
                actualMessage,
                "MSSV đã tồn tại trong hệ thống",
                "Duplicate MSSV validation message incorrect"
        );

        softAssert.assertAll();
    }

//    @Issue("S002")
//    @Test(priority = 8)
//    public void STM008_VerifyEditStudentIDSuccessfully() throws InterruptedException {
//
//        studentsPage.goToStudentsPage();
//
//        studentsPage.randomClickIconAndEditStudent();
//
//        String newMSSV = studentsPage.randomMSSV();
//
//        studentsPage.updateStudent(
//                newMSSV,
//                null,
//                null,
//                null,
//                null
//        );
//        Thread.sleep(2000);
//        studentsPage.searchStudent(newMSSV);
//
//        boolean isUpdated =
//                studentsPage.verifySearchResultContainsMSSV(newMSSV);
//
//        softAssert.assertTrue(
//                isUpdated,
//                "Student ID was not updated successfully"
//        );
//
//        softAssert.assertAll();
//    }

//    @Test(priority = 9)
//    public void STM009_VerifyEditStudentNameSuccessfully() throws InterruptedException {
//
//        studentsPage.goToStudentsPage();
//
//        String mssv = studentsPage.randomClickIconAndEditStudent();
//
//        String expectedName = studentsPage.randomFullName();
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

    @Test(priority = 13)
    public void STM010_VerifyEditStudentClassSuccessfully() throws InterruptedException {

        studentsPage.goToStudentsPage();

        String mssv = studentsPage.randomClickIconAndEditStudent();

        String newClass = "48K21.2";

        studentsPage.updateStudent(
                null,
                null,
                newClass,
                null,
                null
        );
        Thread.sleep(1000);
        studentsPage.scrollToTop();
        Thread.sleep(1000);

        studentsPage.filterByClass("48K21.2");

        Thread.sleep(2000);

        boolean isStudentExist = studentsPage.isStudentDisplayed(mssv);

        softAssert.assertTrue(
                isStudentExist,
                "Student was not moved to new class successfully"
        );

        softAssert.assertAll();
    }

//    @Test(priority = 14)
//    public void STM011_VerifyEditStudentDOBSuccessfully() throws InterruptedException {
//
//        studentsPage.goToStudentsPage();
//
//        String mssv = studentsPage.randomClickIconAndEditStudent();
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

    @Description("User can search student by MSSV")
    @Test(priority = 15)
    public void STM012_VerifyUserCanSearchStudentByMSSV() throws InterruptedException {

        studentsPage.goToStudentsPage();

        StudentItem randomStudent = studentsPage.getRandomStudent();
        String mssv = randomStudent.getMssv();

        studentsPage.searchStudent(mssv);

        softAssert.assertTrue(
                studentsPage.verifySearchResultContainsMSSV(mssv),
                "Search result does not contain MSSV"
        );

        softAssert.assertAll();
    }

    @Description("User can search student with non-existing MSSV")
    @Test(priority = 16)
    public void STM013_VerifySearchStudent_NoDataDisplayed() throws InterruptedException {

        studentsPage.goToStudentsPage();

        String invalidKeyword = studentsPage.generateNonExistingKeyword();
        Thread.sleep(500);
        studentsPage.searchStudent(invalidKeyword);
        Thread.sleep(500);
        softAssert.assertTrue(
                studentsPage.isStudentTableEmpty(),
                "Student table should be empty but still has data"
        );

        softAssert.assertAll();
    }

    @Description("User can import student list from file")
    @Test(priority = 17)
    public void STM014_VerifyUserCanImportStudentFromFileValid() throws Exception {

        studentsPage.goToStudentsPage();

        studentsPage.goToImportForm(
                "\"C:\\Users\\ACER\\Downloads\\danh_sach_sinh_vien.csv\""
        );

        Thread.sleep(2000);

        softAssert.assertAll();
    }

    @Description("User cannot import student when file format is invalid")
    @Test(priority = 18)
    public void STM015_VerifyCannotImport_InvalidFileFormat() throws Exception {

        studentsPage.goToStudentsPage();

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
    @Test(priority = 19)
    public void STM016_VerifyCannotImport_FileSizeExceeds50MB() throws Exception {
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