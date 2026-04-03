package test;

import common.BaseTest;
import models.Setting.CertificateItem;
import models.Setting.StudentItem;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.Constants;

public class Certification extends BaseTest {

    @Test(priority = 2)
    public void CC001_VerifyAddCertificateSuccessfully() throws InterruptedException {
        Thread.sleep(2000);

        loginPage.login(Constants.EMAIL, Constants.PASSWORD);
        Thread.sleep(2000);

        certificatePage.goToCertificationPage();
        Thread.sleep(2000);

       // certificatePage.filterByClass("48K14");
        Thread.sleep(2000);
        certificatePage.openAddCertificationForm();
        Thread.sleep(2000);

        CertificateItem actual = certificatePage.addCertification();

        Assert.assertNotNull(actual, " Không thêm được chứng chỉ");

    }
//    @Test(priority = 3)
//    public void CC002_VerifyAddCertificateWithoutStudent() throws InterruptedException{
//        Thread.sleep(2000);
//        loginPage.login(Constants.EMAIL, Constants.PASSWORD);
//        Thread.sleep(2000);
//        certificatePage.goToCertificationPage();
//        Thread.sleep(2000);
//        certificatePage.openAddCertificationForm();
//        Thread.sleep(2000);
//        certificatePage.addCertificationWithoutStudent();
//        Thread.sleep(2000);
//        String actualMsg = certificatePage.getStudentErrorMessage();
//
//        softAssert.assertEquals(actualMsg, "Sinh viên là bắt buộc");
//
//        softAssert.assertAll();
//    }
//    @Test
//    public void TC_Search_By_FirstName() throws InterruptedException{
//
//        Thread.sleep(2000);
//        loginPage.login(Constants.EMAIL, Constants.PASSWORD);
//        Thread.sleep(2000);
//        certificatePage.goToCertificationPage();
//        Thread.sleep(2000);
//        certificatePage.filterByClass("48K14");
//
//        // lấy random student từ table
//        StudentItem student = certificatePage.getRandomStudent();
//
//        String fullName = student.getFullName();
//        String[] parts = fullName.split(" ");
//
//        String firstName = parts[parts.length - 1];
//
//        certificatePage.searchStudent(firstName);
//
//        Assert.assertTrue(
//                certificatePage.verifySearchResultContainsKeyword(firstName),
//                "Search theo tên FAILED"
//        );
//    }
//
//    @Test
//    public void TC_Search_By_LastName() throws InterruptedException {
//        Thread.sleep(2000);
//        loginPage.login(Constants.EMAIL, Constants.PASSWORD);
//        Thread.sleep(2000);
//        certificatePage.goToCertificationPage();
//        Thread.sleep(2000);
//        certificatePage.filterByClass("48K14");
//        StudentItem student = certificatePage.getRandomStudent();
//
//        String fullName = student.getFullName();
//        String[] parts = fullName.split(" ");
//
//        String hoLot = fullName.replace(" " + parts[parts.length - 1], "");
//
//       certificatePage.searchStudent(hoLot);
//
//        Assert.assertTrue(
//                certificatePage.verifySearchResultContainsKeyword(hoLot),
//                "Search theo họ lót FAILED"
//        );
//    }
//    @Test
//    public void TC_Filter_By_Class() throws InterruptedException {
//
//        Thread.sleep(2000);
//        loginPage.login(Constants.EMAIL, Constants.PASSWORD);
//        Thread.sleep(2000);
//        certificatePage.goToCertificationPage();
//        Thread.sleep(2000);
//        String className = "48K14";
//        certificatePage.filterByClass(className);
//
//        // verify
//        Assert.assertTrue(
//                certificatePage.verifyAllRowsMatchClass(className),
//                "Filter theo lớp FAILED"
//        );
//    }
    @Test
    public void TC_Filter_By_Course() throws InterruptedException {
        Thread.sleep(2000);
        loginPage.login(Constants.EMAIL, Constants.PASSWORD);
        Thread.sleep(2000);
        certificatePage.goToCertificationPage();
        Thread.sleep(2000);

        String courseName = "Khóa 48 (2022-2026)";
        certificatePage.filterByCourse(courseName);
        Thread.sleep(1000);

        // verify tất cả rows trong table đều thuộc khóa này
        Assert.assertTrue(
                certificatePage.verifyAllRowsMatchCourse(courseName),
                "Filter theo khóa FAILED"
        );
    }
}