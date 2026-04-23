package test;

import common.BaseTest;
import models.Setting.CertificateItem;
import models.Setting.StudentItem;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.Constants;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Certification extends BaseTest {

//    @Test(priority = 2)
//    public void CC001_VerifyAddCertificateSuccessfully() throws InterruptedException {
//
//        Thread.sleep(2000);
//        loginPage.login(Constants.EMAIL, Constants.PASSWORD);
//
//        Thread.sleep(2000);
//        certificatePage.goToCertificationPage();
//
//        Thread.sleep(2000);
//        certificatePage.filterByClass("48K14");
//
//        Thread.sleep(3000);
//        certificatePage.openAddCertificationForm();
//
//        Thread.sleep(2000);
//
//        // 🔥 chọn certificate cố định (không random)
//        String certificateName = "TOEIC 800";
//
//        CertificateItem actual = certificatePage.addCertification(certificateName);
//
//        Thread.sleep(3000);
//
//        // ✅ verify có data
//        Assert.assertNotNull(actual, "Không thêm được chứng chỉ");
//
//        // 🔥 verify đúng chứng chỉ
//        Assert.assertTrue(
//                actual.getCertificateName().contains(certificateName),
//                "Chứng chỉ hiển thị không đúng"
//        );
//    }
//@Test(priority = 3)
//public void CC002_VerifyAddCertificateWithoutStudent() throws InterruptedException {
//
//    Thread.sleep(2000);
//    loginPage.login(Constants.EMAIL, Constants.PASSWORD);
//
//    Thread.sleep(2000);
//    certificatePage.goToCertificationPage();
//
//    Thread.sleep(2000);
//    certificatePage.openAddCertificationForm();
//
//    Thread.sleep(2000);
//
//    // 🔥 sửa: truyền certificateName vào (không random)
//    String certificateName = "TOEIC 800";
//    certificatePage.addCertificationWithoutStudent(certificateName);
//
//    Thread.sleep(2000);
//
//    String actualMsg = certificatePage.getStudentErrorMessage();
//
//    // 🔥 normalize để tránh fail do space / xuống dòng
//    actualMsg = actualMsg.trim();
//
//    softAssert.assertEquals(
//            actualMsg,
//            "Sinh viên là bắt buộc",
//            "Error message không đúng khi thiếu sinh viên"
//    );
//
//    softAssert.assertAll();
//}
    @Test
    public void TC_Search_By_FirstName() throws InterruptedException{

        Thread.sleep(2000);
        loginPage.login(Constants.EMAIL, Constants.PASSWORD);
        Thread.sleep(2000);
        certificationPage.goToCertificationPage();
        Thread.sleep(2000);
        certificationPage.filterByClass("48K21.1");

        // lấy random student từ table
        StudentItem student =  certificationPage.getRandomStudent();

        String fullName = student.getFullName();
        String[] parts = fullName.split(" ");

        String firstName = parts[parts.length - 1];

        certificationPage.searchStudent(firstName);

        Assert.assertTrue(
                certificationPage.verifySearchResultContainsKeyword(firstName),
                "Search theo tên FAILED"
        );
    }

    @Test
    public void TC_Search_By_LastName() throws InterruptedException {
        Thread.sleep(2000);
        loginPage.login(Constants.EMAIL, Constants.PASSWORD);
        Thread.sleep(2000);
        certificationPage.goToCertificationPage();
        Thread.sleep(2000);
        certificationPage.filterByClass("48K21.1");
        StudentItem student =  certificationPage.getRandomStudent();

        String fullName = student.getFullName();
        String[] parts = fullName.split(" ");

        String hoLot = fullName.replace(" " + parts[parts.length - 1], "");

        certificationPage.searchStudent(hoLot);

        Assert.assertTrue(
                certificationPage.verifySearchResultContainsKeyword(hoLot),
                "Search theo họ lót FAILED"
        );
    }
    @Test
    public void TC_Filter_By_Class() throws InterruptedException {

        Thread.sleep(2000);
        loginPage.login(Constants.EMAIL, Constants.PASSWORD);
        Thread.sleep(2000);
        certificationPage.goToCertificationPage();
        Thread.sleep(2000);
        String className = "48K21.1";
        certificationPage.filterByClass(className);

        Assert.assertTrue(
                certificationPage.verifyAllRowsMatchClass(className),
                "Filter theo lớp FAILED"
        );
    }
    @Test
    public void TC_Filter_By_Course() throws InterruptedException {
        Thread.sleep(2000);
        loginPage.login(Constants.EMAIL, Constants.PASSWORD);
        Thread.sleep(2000);
        certificationPage.goToCertificationPage();
        Thread.sleep(2000);

        String courseName = "Khóa 48 (2022-2026)";
        certificationPage.filterByCourse(courseName);
        Thread.sleep(1000);
        Assert.assertTrue(
                certificationPage.verifyAllRowsMatchCourse(courseName),
                "Filter theo khóa FAILED"
        );
    }
    @Test
    public void TC_Filter_Class_And_Import_HTML_AutoMapping() throws InterruptedException {

        Thread.sleep(2000);
        loginPage.login(Constants.EMAIL, Constants.PASSWORD);

        Thread.sleep(4000);
        studentsPage.goToStudentsPage();

        Thread.sleep(2000);
        String className = "48K21.1";
        studentsPage.filterByClass(className);

        List<String> expectedStudents = studentsPage.getStudentNames();

        System.out.println("Expected Students: " + expectedStudents);

        Thread.sleep(2000);
        certificationPage.clickMenu("Chứng chỉ");

        Thread.sleep(2000);
        certificationPage.filterByClass(className);


        Thread.sleep(2000);

        String filePath = "C:\\Users\\ACER\\Downloads\\cap-nhat-chung-chi.html";

        certificationPage.uploadFile(filePath);

        Thread.sleep(3000);

        List<String> actualStudents =   certificationPage.getStudentFullNames();

        System.out.println("Actual Students: " + actualStudents);
        
        Assert.assertEquals(
                actualStudents.size(),
                expectedStudents.size(),
                "Số lượng sinh viên không khớp sau khi import"
        );

        for (String student : expectedStudents) {
            Assert.assertTrue(
                    actualStudents.contains(student),
                    "Thiếu sinh viên: " + student
            );
        }

        Set<String> uniqueStudents = new HashSet<>(actualStudents);
        Assert.assertEquals(
                uniqueStudents.size(),
                actualStudents.size(),
                "Có sinh viên bị duplicate"
        );
    }
}