package test;

import common.BaseTest;
import jdk.jfr.Description;
import models.ClassItem;
import org.testng.annotations.Test;

import java.util.ArrayList;

public class Class extends BaseTest {

    @Test
    public void CLS_01_VerifyClassDetailsMatchBetweenFormAndTable() {

        classPage.goToClassManagePage();

        ArrayList<ClassItem> expectedClasses = new ArrayList<>();

        classPage.addClass(
                "48",
                "K21 - Quản trị hệ thống thông tin",
                "Cao Thị Nhâm",
                "48"
        );

        classPage.waitForTableLoaded();

        ArrayList<ClassItem> actualClasses = classPage.getAllClasses();

        softAssert.assertTrue(
                actualClasses.containsAll(expectedClasses),
                "Class details do not match between Add form and table"
        );

        softAssert.assertAll();
    }
        @Description("User cannot add class when class name is empty")
        @Test
        public void LOG02_UserCanNotAddClassWithClassNameEmpty() {

            classPage.goToClassManagePage();

            classPage.addClassWithNoCohort(
                    "K21 - Quản trị hệ thống thông tin",
                    "Cao Thị Nhâm",
                    "48K21"
            );

            softAssert.assertEquals(
                    classPage.getMessage(),
                    "Khóa không được để trống",
                    "The message does not match expected result"
            );

            softAssert.assertAll();
        }

        @Description("User cannot add class when major name is empty")
        @Test
        public void LOG03_UserCanNotAddClassWithMajorNameEmpty() {

            classPage.goToClassManagePage();

            classPage.addClassWithNoMajor(
                    "48",
                    "Cao Thị Nhâm",
                    "48K21"
            );

            softAssert.assertEquals(
                    classPage.getMessage(),
                    "Chuyên ngành không được để trống",
                    "The message does not match expected result"
            );

            softAssert.assertAll();
        }

        @Description("User cannot add class when teacher name is empty")
        @Test
        public void LOG04_UserCanNotAddClassWithTeacherNameEmpty() {

            classPage.goToClassManagePage();

            classPage.addClassWithNoTeacher(
                    "48",
                    "K21 - Quản trị hệ thống thông tin",
                    "48K21.1"
            );

            softAssert.assertEquals(
                    classPage.getMessage(),
                    "Giáo viên phụ trách không được để trống",
                    "The message does not match expected result"
            );

            softAssert.assertAll();
        }
//    @Description("User cannot add class when class name is empty")
//    @Test
//    public void LOG05_UserCanNotAddClassWithClassEmpty() {
//
//        classPage.goToClassManagePage();
//
//        classPage.addClassWithNoClass(
//                "48",
//                "K21 - Quản trị hệ thống thông tin",
//                "Cao Thị Nhâm"
//        );
//
//        softAssert.assertEquals(
//                classPage.getMessage(),
//                "Lớp học đã tồn tại trong hệ thống",
//                "The message does not match expected result"
//        );
//
//        softAssert.assertAll();
//    }
    @Description("User can search class by class name")
    @Test
    public void CLS_06_UserCanSearchByClassName() {

        classPage.goToClassManagePage();

        String className = classPage.getRandomClassName();

        classPage.searchClassName(className);

        softAssert.assertTrue(
                classPage.verifySearchResultContainsKeyword(className),
                "Search result does not contain class name"
        );

        softAssert.assertAll();
    }
    @Test
    public void CLS_07_VerifySearchWithNonExistingKeywordReturnsEmpty() {

        classPage.goToClassManagePage();

        String keyword = "zzz_not_exist_" + System.currentTimeMillis();

        classPage.searchClassName(keyword);

        softAssert.assertEquals(
                classPage.getTotalClasses(),
                0,
                "Table is not empty after search"
        );
        softAssert.assertTrue(
                classPage.isNoDataDisplayed(),
                "No data message is not displayed"
        );

        softAssert.assertAll();
    }

    @Description("User can delete class when quantity = 0")
    @Test
    public void CLS_08_UserCanDeleteClass_WhenQuantityEqual0() throws InterruptedException {
        classPage.goToClassManagePage();

        String className = classPage.getClassNameHavingQuantityEqual(0);

        classPage.deleteClass(className);
        Thread.sleep(1000);
        classPage.searchClassName(className);
        Thread.sleep(1000);
        softAssert.assertTrue(
                classPage.isNoDataDisplayed(),
                "Deleted class still appears in search result"
        );

        softAssert.assertAll();
    }
//
@Test
public void CLS_011_VerifyClassDetailsMatchBetweenFormAndTable() {

    classPage.goToClassManagePage();

    ArrayList<ClassItem> expectedClasses = new ArrayList<>();

    classPage.addClass(
            "48",
            "K21 - Quản trị hệ thống thông tin",
            "Cao Thị Nhâm",
            "48"
    );

    classPage.waitForTableLoaded();

    ArrayList<ClassItem> actualClasses = classPage.getAllClasses();

    softAssert.assertTrue(
            actualClasses.containsAll(expectedClasses),
            "Class details do not match between Add form and table"
    );

    softAssert.assertAll();
}
    @Description("User cannot add class when class name is empty")
    @Test
    public void LOG021_UserCanNotAddClassWithClassNameEmpty() {

        classPage.goToClassManagePage();

        classPage.addClassWithNoCohort(
                "K21 - Quản trị hệ thống thông tin",
                "Cao Thị Nhâm",
                "48K21"
        );

        softAssert.assertEquals(
                classPage.getMessage(),
                "Khóa không được để trống",
                "The message does not match expected result"
        );

        softAssert.assertAll();
    }

    @Description("User cannot add class when major name is empty")
    @Test
    public void LOG031_UserCanNotAddClassWithMajorNameEmpty() {

        classPage.goToClassManagePage();

        classPage.addClassWithNoMajor(
                "48",
                "Cao Thị Nhâm",
                "48K21"
        );

        softAssert.assertEquals(
                classPage.getMessage(),
                "Chuyên ngành không được để trống",
                "The message does not match expected result"
        );

        softAssert.assertAll();
    }

    @Description("User cannot add class when teacher name is empty")
    @Test
    public void LOG041_UserCanNotAddClassWithTeacherNameEmpty() {

        classPage.goToClassManagePage();

        classPage.addClassWithNoTeacher(
                "48",
                "K21 - Quản trị hệ thống thông tin",
                "48K21.1"
        );

        softAssert.assertEquals(
                classPage.getMessage(),
                "Giáo viên phụ trách không được để trống",
                "The message does not match expected result"
        );

        softAssert.assertAll();
    }
}
