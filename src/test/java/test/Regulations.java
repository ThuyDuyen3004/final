package test;

import com.github.javafaker.Faker;
import common.BaseTest;
import io.qameta.allure.Description;
import models.Setting.RegulationCondition;
import models.Setting.RegulationDetail;
import models.Setting.RegulationItem;
import org.testng.annotations.Test;
import utils.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Regulations extends BaseTest {

//    @Test
//    public void UMG002_VerifyRegulationsDetailsMatchBetweenFormAndTable() throws InterruptedException {
//
//        Faker faker = new Faker();
//
//        Thread.sleep(1000);
//        loginPage.login(Constants.EMAIL, Constants.PASSWORD);
//
//        Thread.sleep(1000);
//        regulationsPage.goToRegulationsPage();
//
//        Thread.sleep(1000);
//        String regulationName = faker.educator().course();
//        String totalCredits = String.valueOf(faker.number().numberBetween(100, 150));
//        String requiredCredits = String.valueOf(faker.number().numberBetween(50, 100));
//        String electiveCredits = String.valueOf(faker.number().numberBetween(20, 60));
//        String gpa = String.valueOf(faker.number().randomDouble(1, 2, 4));
//
//        String course = "48";
//        String major = "Quản trị hệ thống thông tin";
//
//        Thread.sleep(1000);
//        regulationsPage.addRegulation(
//                regulationName,
//                totalCredits,
//                requiredCredits,
//                electiveCredits,
//                gpa,
//                course,
//                major
//        );
//
//        Thread.sleep(3000);
//
//        RegulationItem actual = regulationsPage.getRegulationByName(regulationName);
//        String actualCourse = actual.getCourse();
//
//        softAssert.assertEquals(actual.getName().trim(), regulationName.trim(), "Name mismatch");
//
//        softAssert.assertTrue(
//                actualCourse.contains(course),
//                "Course mismatch: " + actualCourse
//        );
//
//        softAssert.assertEquals(
//                actual.getMajor().trim(),
//                major.trim(),
//                "Major mismatch"
//        );
//
//        softAssert.assertAll();
//    }
private String normalize(String s) {
    return s == null ? "" : s.trim().replace(".0", "");
}
    @Test
    public void UMG002_VerifyRegulationsDetailsMatchBetweenFormAndTable() throws InterruptedException {

        Faker faker = new Faker();

        loginPage.login(Constants.EMAIL, Constants.PASSWORD);
        Thread.sleep(2000);

        regulationsPage.goToRegulationsPage();
        Thread.sleep(2000);

        String regulationName = faker.educator().course();
        String totalCredits = String.valueOf(faker.number().numberBetween(120, 150));
        String requiredCredits = String.valueOf(faker.number().numberBetween(80, 110));
        String electiveCredits = String.valueOf(faker.number().numberBetween(20, 60));
        String gpa = String.format("%.1f", faker.number().randomDouble(1, 2, 4));

        String course = "48";
        String major = "Quản trị hệ thống thông tin";

        // ===== ADD =====
        regulationsPage.addRegulation(
                regulationName,
                totalCredits,
                requiredCredits,
                electiveCredits,
                gpa,
                course,
                major
        );
        regulationsPage.refreshPage();
        Thread.sleep(2000);

        // ===== VERIFY TABLE =====
        RegulationItem actual = regulationsPage.getRegulationByName(regulationName);

        softAssert.assertTrue(
                actual.getName().equalsIgnoreCase(regulationName),
                "Name mismatch"
        );

        softAssert.assertTrue(
                actual.getMajor().equalsIgnoreCase(major),
                "Major mismatch"
        );
        softAssert.assertTrue(actual.getCourse().contains(course), "Course mismatch");
      

        // ===== CLICK DETAIL =====
        regulationsPage.openRegulationByName(regulationName);
        Thread.sleep(2000);

        // ===== VERIFY GIÁ TRỊ TRONG BẢNG =====
        softAssert.assertEquals(
                normalize(regulationsPage.getValueByCondition("Tổng số tín chỉ")),
                normalize(totalCredits),
                "Total credits mismatch"
        );

        softAssert.assertEquals(
                normalize(regulationsPage.getValueByCondition("Tín chỉ bắt buộc")),
                normalize(requiredCredits),
                "Required credits mismatch"
        );

        softAssert.assertEquals(
                normalize(regulationsPage.getValueByCondition("Tín chỉ tự chọn")),
                normalize(electiveCredits),
                "Elective credits mismatch"
        );

        softAssert.assertEquals(
                normalize(regulationsPage.getValueByCondition("GPA")),
                normalize(gpa),
                "GPA mismatch"
        );

        // nếu cần verify dòng cuối
        softAssert.assertEquals(
                regulationsPage.getValueByCondition("Chứng chỉ đầu ra").trim(),
                "Đủ theo khoá",
                "Certificate condition mismatch"
        );

        softAssert.assertAll();
    }
    @Test
    public void UMG003_VerifyErrorWhenRegulationNameIsEmpty() throws InterruptedException {

        Faker faker = new Faker();

        Thread.sleep(1000);
        loginPage.login(Constants.EMAIL, Constants.PASSWORD);

        Thread.sleep(1000);
        regulationsPage.goToRegulationsPage();

        Thread.sleep(1000);
        String regulationName = "";
        String totalCredits = String.valueOf(faker.number().numberBetween(100, 150));
        String requiredCredits = String.valueOf(faker.number().numberBetween(50, 100));
        String electiveCredits = String.valueOf(faker.number().numberBetween(20, 60));
        String gpa = String.valueOf(faker.number().randomDouble(1, 2, 4));

        String course = "48";
        String major = "Quản trị hệ thống thông tin";

        Thread.sleep(1000);

        regulationsPage.addRegulation(
                regulationName,
                totalCredits,
                requiredCredits,
                electiveCredits,
                gpa,
                course,
                major
        );

        Thread.sleep(1000);

        softAssert.assertEquals(
                regulationsPage.getMessage(),
                "Vui lòng nhập tên quy chế",
                "Sai hoặc không hiển thị message lỗi"
        );
        softAssert.assertAll();
    }
    @Test
    public void UMG004_VerifyErrorWhenCourseIsNotSelected() throws InterruptedException {

        Faker faker = new Faker();

        Thread.sleep(1000);
        loginPage.login(Constants.EMAIL, Constants.PASSWORD);

        Thread.sleep(1000);
        regulationsPage.goToRegulationsPage();

        Thread.sleep(1000);

        String regulationName = faker.educator().course();
        String totalCredits = String.valueOf(faker.number().numberBetween(100, 150));
        String requiredCredits = String.valueOf(faker.number().numberBetween(50, 100));
        String electiveCredits = String.valueOf(faker.number().numberBetween(20, 60));
        String gpa = String.valueOf(faker.number().randomDouble(1, 2, 4));

        Thread.sleep(1000);

        regulationsPage.fillToGpaAndSave(
                regulationName,
                totalCredits,
                requiredCredits,
                electiveCredits,
                gpa
        );

        Thread.sleep(1000);

        softAssert.assertEquals(
                regulationsPage.getMessage().trim(),
                "Vui lòng chọn ít nhất một khoá áp dụng",
                "Sai hoặc không hiển thị message lỗi"
        );

        softAssert.assertAll();
    }
    @Test
    public void UMG005_VerifyErrorWhenTotalCreditsIsNegative() throws InterruptedException {

        Faker faker = new Faker();

        Thread.sleep(1000);
        loginPage.login(Constants.EMAIL, Constants.PASSWORD);

        Thread.sleep(1000);
        regulationsPage.goToRegulationsPage();

        Thread.sleep(1000);

        String regulationName = faker.educator().course();
        String totalCredits = "-" + faker.number().numberBetween(100, 150);
        String requiredCredits = String.valueOf(faker.number().numberBetween(50, 100));
        String electiveCredits = String.valueOf(faker.number().numberBetween(20, 60));
        String gpa = String.valueOf(faker.number().randomDouble(1, 2, 4));

        String course = "48";
        String major = "Quản trị hệ thống thông tin";

        Thread.sleep(1000);

        regulationsPage.addRegulation(regulationName, totalCredits, requiredCredits, electiveCredits, gpa, course, major);

        Thread.sleep(1000);

        softAssert.assertTrue(regulationsPage.isErrorMessageDisplayed(), "Không hiển thị lỗi Total Credits âm");
        softAssert.assertAll();
    }
    @Test
    public void UMG006_VerifyErrorWhenRequiredCreditsIsNegative() throws InterruptedException {

        Faker faker = new Faker();

        Thread.sleep(1000);
        loginPage.login(Constants.EMAIL, Constants.PASSWORD);

        Thread.sleep(1000);
        regulationsPage.goToRegulationsPage();

        Thread.sleep(1000);

        String regulationName = faker.educator().course();
        String totalCredits = String.valueOf(faker.number().numberBetween(100, 150));
        String requiredCredits = "-" + faker.number().numberBetween(50, 100);
        String electiveCredits = String.valueOf(faker.number().numberBetween(20, 60));
        String gpa = String.valueOf(faker.number().randomDouble(1, 2, 4));

        String course = "48";
        String major = "Quản trị hệ thống thông tin";

        Thread.sleep(1000);

        regulationsPage.addRegulation(regulationName, totalCredits, requiredCredits, electiveCredits, gpa, course, major);

        Thread.sleep(1000);

        softAssert.assertTrue(regulationsPage.isErrorMessageDisplayed(), "Không hiển thị lỗi Required Credits âm");
        softAssert.assertAll();
    }
    @Test
    public void UMG007_VerifyErrorWhenElectiveCreditsIsNegative() throws InterruptedException {

        Faker faker = new Faker();

        Thread.sleep(1000);
        loginPage.login(Constants.EMAIL, Constants.PASSWORD);

        Thread.sleep(1000);
        regulationsPage.goToRegulationsPage();

        Thread.sleep(1000);

        String regulationName = faker.educator().course();
        String totalCredits = String.valueOf(faker.number().numberBetween(100, 150));
        String requiredCredits = String.valueOf(faker.number().numberBetween(50, 100));
        String electiveCredits = "-" + faker.number().numberBetween(20, 60);
        String gpa = String.valueOf(faker.number().randomDouble(1, 2, 4));

        String course = "48";
        String major = "Quản trị hệ thống thông tin";

        Thread.sleep(1000);

        regulationsPage.addRegulation(regulationName, totalCredits, requiredCredits, electiveCredits, gpa, course, major);

        Thread.sleep(1000);

        softAssert.assertTrue(regulationsPage.isErrorMessageDisplayed(), "Không hiển thị lỗi Elective Credits âm");
        softAssert.assertAll();
    }
    @Test
    public void UMG008_VerifyErrorWhenGpaIsNegative() throws InterruptedException {

        Faker faker = new Faker();

        Thread.sleep(1000);
        loginPage.login(Constants.EMAIL, Constants.PASSWORD);

        Thread.sleep(1000);
        regulationsPage.goToRegulationsPage();

        Thread.sleep(1000);

        String regulationName = faker.educator().course();
        String totalCredits = String.valueOf(faker.number().numberBetween(100, 150));
        String requiredCredits = String.valueOf(faker.number().numberBetween(50, 100));
        String electiveCredits = String.valueOf(faker.number().numberBetween(20, 60));
        String gpa = "-" + faker.number().numberBetween(1, 4);

        String course = "48";
        String major = "Quản trị hệ thống thông tin";

        Thread.sleep(1000);

        regulationsPage.addRegulation(regulationName, totalCredits, requiredCredits, electiveCredits, gpa, course, major);

        Thread.sleep(1000);

        softAssert.assertTrue(regulationsPage.isErrorMessageDisplayed(), "Không hiển thị lỗi GPA âm");
        softAssert.assertAll();
    }
    @Description("The regulation is deleted successfully")
    @Test
    public void UMG009_VerifyRegulationCanBeDeleted() throws InterruptedException {

            Thread.sleep(1000);
            loginPage.login(Constants.EMAIL, Constants.PASSWORD);

            Thread.sleep(3000);
            regulationsPage.goToRegulationsPage();

            Thread.sleep(1000);

            String deletedName = regulationsPage.randomClickIconAndDeleteRegulation();

            Thread.sleep(2000);

            regulationsPage.searchRegulation(deletedName);

            Thread.sleep(2000);

        softAssert.assertFalse(
                regulationsPage.verifySearchResultContainsKeyword(deletedName),
                "Regulation vẫn còn sau khi delete"
        );
            softAssert.assertAll();
        }

//    @Description("User can search regulation by name")
//    @Test(priority = 10)
//    public void UMG010_VerifyUserCanSearchRegulationByName() throws InterruptedException {
//
//        Thread.sleep(1000);
//        loginPage.login(Constants.EMAIL, Constants.PASSWORD);
//
//        Thread.sleep(1000);
//        regulationsPage.goToRegulationsPage();
//
//        Thread.sleep(1000);
//
//
//        RegulationItem randomRegulation = regulationsPage.getRandomRegulation();
//        String name = randomRegulation.getName();
//
//        regulationsPage.searchRegulation(name);
//
//        Thread.sleep(1000);
//
//        softAssert.assertTrue(
//                regulationsPage.verifySearchResultContainsKeyword(name),
//                "Search result không chứa tên quy chế"
//        );
//
//        softAssert.assertAll();
//    }
    @Description("User can search regulation with non-existing name")
    @Test(priority = 11)
    public void UMG011_VerifySearchRegulation_NoDataDisplayed() throws InterruptedException {

        Thread.sleep(1000);
        loginPage.login(Constants.EMAIL, Constants.PASSWORD);

        Thread.sleep(2000);
        regulationsPage.goToRegulationsPage();

        Thread.sleep(1000);

        String invalidKeyword = regulationsPage.generateNonExistingKeyword();

        regulationsPage.searchRegulation(invalidKeyword);

        Thread.sleep(1000);

        softAssert.assertTrue(
                regulationsPage.isRegulationTableEmpty(),
                "Regulation table should be empty but still has data"
        );

        softAssert.assertAll();
    }
}