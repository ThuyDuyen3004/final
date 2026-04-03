package test;

import common.BaseTest;
import jdk.jfr.Description;
import models.Setting.ClassItem;
import org.testng.annotations.Test;
import utils.Constants;

public class Class extends BaseTest {

    @Description("User can add class successfully")
    @Test
    public void LOG03_UserCanAddClass() throws InterruptedException {

        Thread.sleep(2000);
        loginPage.login(Constants.EMAIL, Constants.PASSWORD);

        Thread.sleep(2000);
        classPage.goToUserManagePage();

        Thread.sleep(1000);
        classPage.addClass(
                "48K21.1",
                "Quản trị hệ thống thông tin",
                "Cao Thị Nhâm"
        );
    }

    @Description("User cannot add class when class name is empty")
    @Test
    public void LOG03_UserCanNotAddClassWithClassNameEmpty() throws InterruptedException {

        Thread.sleep(2000);
        loginPage.login(Constants.EMAIL, Constants.PASSWORD);

        Thread.sleep(2000);
        classPage.goToUserManagePage();

        Thread.sleep(1000);
        classPage.addClass(
                "",
                "Quản trị hệ thống thông tin",
                "Cao Thị Nhâm"
        );

        softAssert.assertEquals(
                classPage.getMessage(),
                "Tên lớp không được để trống",
                "The message does not match expected result"
        );

        softAssert.assertAll();
    }

    @Description("User cannot add class when major name is empty")
    @Test
    public void LOG04_UserCanNotAddClassWithMajorNameEmpty() throws InterruptedException {

        Thread.sleep(1000);
        loginPage.login(Constants.EMAIL, Constants.PASSWORD);

        Thread.sleep(2000);
        classPage.goToUserManagePage();

        Thread.sleep(1000);
        classPage.addClass(
                "48K21.1",
                "",
                "Cao Thị Nhâm"
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
    public void LOG05_UserCanNotAddClassWithTeacherNameEmpty() throws InterruptedException {

        Thread.sleep(1000);
        loginPage.login(Constants.EMAIL, Constants.PASSWORD);

        Thread.sleep(2000);
        classPage.goToUserManagePage();

        Thread.sleep(1000);
        classPage.addClass(
                "48K21.1",
                "Quản trị hệ thống thông tin",
                ""
        );

        softAssert.assertEquals(
                classPage.getMessage(),
                "Giáo viên phụ trách không được để trống",
                "The message does not match expected result"
        );

        softAssert.assertAll();
    }

    @Description("User can search class by class name")
    @Test
    public void CLS_01_UserCanSearchByClassName() throws InterruptedException {

        Thread.sleep(1000);
        loginPage.login(Constants.EMAIL, Constants.PASSWORD);

        Thread.sleep(2000);
        classPage.goToUserManagePage();

        Thread.sleep(1000);
        ClassItem randomClass = classPage.getRandomClass();
        String className = randomClass.getClassName();

        Thread.sleep(1000);
        classPage.searchClassName(className);

        Thread.sleep(1000);
        softAssert.assertTrue(
                classPage.verifySearchResultContainsKeyword(className),
                "Search result does not contain class name"
        );

        softAssert.assertAll();
    }

    @Description("The class is deleted successfully")
    @Test
    public void CLS_02_UserCanDeleteClass() throws InterruptedException {

        Thread.sleep(1000);
        loginPage.login(Constants.EMAIL, Constants.PASSWORD);

        Thread.sleep(2000);
        classPage.goToUserManagePage();

        Thread.sleep(1000);
        String className = classPage.randomClickIconAndDelete();

        Thread.sleep(1000);
        classPage.searchClassName(className);

        Thread.sleep(1000);
        softAssert.assertEquals(
                classPage.getNoDataMessageText(),
                "Hiển thị 0/0 bản ghi",
                "Deleted class still exists"
        );

        softAssert.assertAll();
    }
}
