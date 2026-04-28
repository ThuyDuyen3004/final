package test;

import common.BaseTest;
import models.StudentItem;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.awt.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Certification extends BaseTest {

    @Test
    public void TC_Search_By_FirstName() throws InterruptedException {
        certificationPage.goToCertificationPage();

        certificationPage.filterByClass("48K21.1");

        StudentItem student = certificationPage.getRandomStudent();

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

        certificationPage.goToCertificationPage();
        Thread.sleep(1000);
        certificationPage.filterByClass("48K21.1");
        StudentItem student = certificationPage.getRandomStudent();

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

        certificationPage.goToCertificationPage();
        String className = "48K21.1";
        certificationPage.filterByClass(className);

        Assert.assertTrue(
                certificationPage.verifyAllRowsMatchClass(className),
                "Filter theo lớp FAILED"
        );
    }

    @Test
    public void TC_Filter_By_Course() throws InterruptedException {
        certificationPage.goToCertificationPage();
        Thread.sleep(1000);
        String courseName = "Khóa 48";
        certificationPage.filterByCourse(courseName);
        Thread.sleep(1000);
        Assert.assertTrue(
                certificationPage.verifyAllRowsMatchCourse(courseName),
                "Filter theo khóa FAILED"
        );
    }

    @Test
    public void TC_Filter_Class_And_Import_HTML_AutoMapping() throws InterruptedException {

        studentsPage.goToStudentsPage();

        String className = "48K21.1";
        Thread.sleep(1000);
        studentsPage.filterByClass(className);

        List<String> expectedStudents = studentsPage.getStudentNames();

        System.out.println("Expected Students: " + expectedStudents);

        certificationPage.clickMenu("Chứng chỉ");

        certificationPage.filterByClass(className);

        String filePath = "C:\\Users\\ACER\\Downloads\\cap-nhat-chung-chi.html";

        certificationPage.uploadFile(filePath);

        List<String> actualStudents = certificationPage.getStudentFullNames();

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

    @Test
    public void TC_Filter_Class_And_Import_EnglishCredit_AutoMapping() throws AWTException {

        studentsPage.goToStudentsPage();

        List<String> expectedStudents = studentsPage.getStudentNames();

        System.out.println("Expected Students: " + expectedStudents);

        certificationPage.clickMenu("Chứng chỉ");

        certificationPage.openImportEnglishTab();

        String filePath = "\"C:\\Users\\ACER\\Downloads\\tiếng anh khoa.csv\"";
        certificationPage.uploadEngFile(filePath);
    }
    //
    @Test
    public void TC1111111_Search_By_FirstName() throws InterruptedException {
        certificationPage.goToCertificationPage();

        certificationPage.filterByClass("48K21.1");

        StudentItem student = certificationPage.getRandomStudent();

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
    public void TC1111_Search_By_LastName() throws InterruptedException {

        certificationPage.goToCertificationPage();
        Thread.sleep(1000);
        certificationPage.filterByClass("48K21.1");
        StudentItem student = certificationPage.getRandomStudent();

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
    public void TC111_Filter_By_Class() throws InterruptedException {

        certificationPage.goToCertificationPage();
        String className = "48K21.1";
        certificationPage.filterByClass(className);

        Assert.assertTrue(
                certificationPage.verifyAllRowsMatchClass(className),
                "Filter theo lớp FAILED"
        );
    }

    @Test
    public void TC11_Filter_By_Course() throws InterruptedException {
        certificationPage.goToCertificationPage();
        Thread.sleep(1000);
        String courseName = "Khóa 48";
        certificationPage.filterByCourse(courseName);
        Thread.sleep(1000);
        Assert.assertTrue(
                certificationPage.verifyAllRowsMatchCourse(courseName),
                "Filter theo khóa FAILED"
        );
    }

    @Test
    public void TC1_Filter_Class_And_Import_EnglishCredit_AutoMapping() throws AWTException {

        studentsPage.goToStudentsPage();

        List<String> expectedStudents = studentsPage.getStudentNames();

        System.out.println("Expected Students: " + expectedStudents);

        certificationPage.clickMenu("Chứng chỉ");

        certificationPage.openImportEnglishTab();

        String filePath = "\"C:\\Users\\ACER\\Downloads\\tiếng anh khoa.csv\"";
        certificationPage.uploadEngFile(filePath);
    }
}

//        List<String> actualStudents = certificationPage.getStudentFullNames();
//
//        Assert.assertEquals(
//                actualStudents.size(),
//                expectedStudents.size(),
//                "Số lượng sinh viên không khớp sau khi import"
//        );
//
//        for (String student : expectedStudents) {
//            Assert.assertTrue(
//                    actualStudents.contains(student),
//                    "Thiếu sinh viên: " + student
//            );
//        }
//
//        Set<String> uniqueStudents = new HashSet<>(actualStudents);
//
//        Assert.assertEquals(
//                uniqueStudents.size(),
//                actualStudents.size(),
//                "Có sinh viên bị duplicate"
//        );
//    }
