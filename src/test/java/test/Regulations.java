package test;

import com.github.javafaker.Faker;
import common.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Issue;
import models.Setting.RegulationItem;
import org.testng.annotations.Test;
import utils.Constants;

import static org.apache.commons.lang3.math.NumberUtils.toDouble;


//public class Regulations extends BaseTest {

//    @Test
//    public void UMG002_VerifyRegulationsDetailsMatchBetweenFormAndTable() throws InterruptedException {
//
//        loginPage.login(Constants.EMAIL, Constants.PASSWORD);
//        Thread.sleep(2000);
//
//        regulationsPage.goToRegulationsPage();
//        Thread.sleep(2000);
//
//        String regulationName = "Regulation Auto Test";
//
//        String requiredCredits = "91";
//        String electiveCredits = "38";
//        String gpa = "2.2";
//
//        String course = "48";
//        String major = "Quản trị hệ thống thông tin";
//
//        regulationsPage.addRegulation(
//                regulationName,
//                requiredCredits,
//                electiveCredits,
//                gpa,
//                course,
//                major
//        );
//
//        regulationsPage.refreshPage();
//        Thread.sleep(2000);
//
//        RegulationItem actual = regulationsPage.getRegulationByName(regulationName);
//        Thread.sleep(2000);
//        softAssert.assertTrue(actual.getName().equalsIgnoreCase(regulationName), "Name mismatch");
//        softAssert.assertTrue(actual.getMajor().equalsIgnoreCase(major), "Major mismatch");
//        softAssert.assertTrue(actual.getCourse().contains(course), "Course mismatch");
//
//        regulationsPage.openRegulationByName(regulationName);
//        Thread.sleep(2000);
//
//        softAssert.assertEquals(
//                toDouble(regulationsPage.getValueByCondition("Tín chỉ bắt buộc")),
//                toDouble(requiredCredits),
//                "Required credits mismatch"
//        );
//
//        softAssert.assertEquals(
//                toDouble(regulationsPage.getValueByCondition("Tín chỉ tự chọn")),
//                toDouble(electiveCredits),
//                "Elective credits mismatch"
//        );
//
//        softAssert.assertEquals(
//                toDouble(regulationsPage.getValueByCondition("GPA")),
//                toDouble(gpa),
//                "GPA mismatch"
//        );
//
//        softAssert.assertEquals(
//                regulationsPage.getValueByCondition("Chứng chỉ đầu ra").trim(),
//                "Đủ theo khoá",
//                "Certificate condition mismatch"
//        );
//
//        softAssert.assertAll();
//    }
//    @Test
//    public void UMG003_VerifyErrorWhenRegulationNameIsEmpty() throws InterruptedException {
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
//        String regulationName = "";
//        String totalCredits = String.valueOf(faker.number().numberBetween(100, 150));
//        String requiredCredits = String.valueOf(faker.number().numberBetween(50, 100));
//        String electiveCredits = String.valueOf(faker.number().numberBetween(20, 60));
//        String gpa = String.valueOf(faker.number().randomDouble(1, 2, 4));
//
//        String course = "48";
//        String major = "Quản trị hệ thống thông tin";
//
//        Thread.sleep(1000);
//
//        regulationsPage.addRegulation(
//                regulationName,
//                requiredCredits,
//                electiveCredits,
//                gpa,
//                course,
//                major
//        );
//
//        Thread.sleep(1000);
//
//        softAssert.assertEquals(
//                regulationsPage.getMessage(),
//                "Vui lòng nhập tên quy chế",
//                "Sai hoặc không hiển thị message lỗi"
//        );
//        softAssert.assertAll();
//    }
//    @Test
//    public void UMG004_VerifyErrorWhenCourseIsNotSelected() throws InterruptedException {
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
//
//        String regulationName = faker.educator().course();
//        String totalCredits = String.valueOf(faker.number().numberBetween(100, 150));
//        String requiredCredits = String.valueOf(faker.number().numberBetween(50, 100));
//        String electiveCredits = String.valueOf(faker.number().numberBetween(20, 60));
//        String gpa = String.valueOf(faker.number().randomDouble(1, 2, 4));
//
//        Thread.sleep(1000);
//
//        regulationsPage.fillToGpaAndSave(
//                regulationName,
//                totalCredits,
//                requiredCredits,
//                electiveCredits,
//                gpa
//        );
//
//        Thread.sleep(1000);
//
//        softAssert.assertEquals(
//                regulationsPage.getMessage().trim(),
//                "Vui lòng chọn ít nhất một khoá áp dụng",
//                "Sai hoặc không hiển thị message lỗi"
//        );
//
//        softAssert.assertAll();
//    }
//    @Test
//    public void UMG005_VerifyErrorWhenTotalCreditsIsNegative() throws InterruptedException {
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
//
//        String regulationName = faker.educator().course();
//        String requiredCredits = String.valueOf(faker.number().numberBetween(50, 100));
//        String electiveCredits = String.valueOf(faker.number().numberBetween(20, 60));
//        String gpa = String.valueOf(faker.number().randomDouble(1, 2, 4));
//
//        String course = "48";
//        String major = "Quản trị hệ thống thông tin";
//
//        Thread.sleep(1000);
//
//        regulationsPage.addRegulation(regulationName,requiredCredits, electiveCredits, gpa, course, major);
//
//        Thread.sleep(1000);
//
//        softAssert.assertTrue(regulationsPage.isErrorMessageDisplayed(), "Không hiển thị lỗi Total Credits âm");
//        softAssert.assertAll();
//    }
//    @Test
//    public void UMG006_VerifyErrorWhenRequiredCreditsIsNegative() throws InterruptedException {
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
//
//        String regulationName = faker.educator().course();
//        String totalCredits = String.valueOf(faker.number().numberBetween(100, 150));
//        String requiredCredits = "-" + faker.number().numberBetween(50, 100);
//        String electiveCredits = String.valueOf(faker.number().numberBetween(20, 60));
//        String gpa = String.valueOf(faker.number().randomDouble(1, 2, 4));
//
//        String course = "48";
//        String major = "Quản trị hệ thống thông tin";
//
//        Thread.sleep(1000);
//
//        regulationsPage.addRegulation(regulationName,requiredCredits, electiveCredits, gpa, course, major);
//
//        Thread.sleep(1000);
//
//        softAssert.assertTrue(regulationsPage.isErrorMessageDisplayed(), "Không hiển thị lỗi Required Credits âm");
//        softAssert.assertAll();
//    }
//    @Test
//    public void UMG007_VerifyErrorWhenElectiveCreditsIsNegative() throws InterruptedException {
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
//
//        String regulationName = faker.educator().course();
//        String totalCredits = String.valueOf(faker.number().numberBetween(100, 150));
//        String requiredCredits = String.valueOf(faker.number().numberBetween(50, 100));
//        String electiveCredits = "-" + faker.number().numberBetween(20, 60);
//        String gpa = String.valueOf(faker.number().randomDouble(1, 2, 4));
//
//        String course = "48";
//        String major = "Quản trị hệ thống thông tin";
//
//        Thread.sleep(1000);
//
//        regulationsPage.addRegulation(regulationName, requiredCredits, electiveCredits, gpa, course, major);
//
//        Thread.sleep(1000);
//
//        softAssert.assertTrue(regulationsPage.isErrorMessageDisplayed(), "Không hiển thị lỗi Elective Credits âm");
//        softAssert.assertAll();
//    }
//    @Test
//    public void UMG008_VerifyErrorWhenGpaIsNegative() throws InterruptedException {
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
//
//        String regulationName = faker.educator().course();
//        String totalCredits = String.valueOf(faker.number().numberBetween(100, 150));
//        String requiredCredits = String.valueOf(faker.number().numberBetween(50, 100));
//        String electiveCredits = String.valueOf(faker.number().numberBetween(20, 60));
//        String gpa = "-" + faker.number().numberBetween(1, 4);
//
//        String course = "48";
//        String major = "Quản trị hệ thống thông tin";
//
//        Thread.sleep(1000);
//
//        regulationsPage.addRegulation(regulationName,requiredCredits, electiveCredits, gpa, course, major);
//
//        Thread.sleep(1000);
//
//        softAssert.assertTrue(regulationsPage.isErrorMessageDisplayed(), "Không hiển thị lỗi GPA âm");
//        softAssert.assertAll();
//    }
//    @Description("The regulation is deleted successfully")
//    @Test
//    public void UMG009_VerifyRegulationCanBeDeleted() throws InterruptedException {
//
//            Thread.sleep(1000);
//            loginPage.login(Constants.EMAIL, Constants.PASSWORD);
//
//            Thread.sleep(3000);
//            regulationsPage.goToRegulationsPage();
//
//            Thread.sleep(1000);
//
//            String deletedName = regulationsPage.randomClickIconAndDeleteRegulation();
//
//            Thread.sleep(2000);
//
//            regulationsPage.searchRegulation(deletedName);
//
//            Thread.sleep(2000);
//
//        softAssert.assertFalse(
//                regulationsPage.verifySearchResultContainsKeyword(deletedName),
//                "Regulation vẫn còn sau khi delete"
//        );
//            softAssert.assertAll();
//        }
//
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
//    @Description("User can search regulation with non-existing name")
//    @Test(priority = 11)
//    public void UMG011_VerifySearchRegulation_NoDataDisplayed() throws InterruptedException {
//
//        Thread.sleep(1000);
//        loginPage.login(Constants.EMAIL, Constants.PASSWORD);
//
//        Thread.sleep(2000);
//        regulationsPage.goToRegulationsPage();
//
//        Thread.sleep(1000);
//
//        String invalidKeyword = regulationsPage.generateNonExistingKeyword();
//
//        regulationsPage.searchRegulation(invalidKeyword);
//
//        Thread.sleep(1000);
//
//        softAssert.assertTrue(
//                regulationsPage.isRegulationTableEmpty(),
//                "Regulation table should be empty but still has data"
//        );
//
//        softAssert.assertAll();
//    }
//}


import com.github.javafaker.Faker;
import common.BaseTest;
import io.qameta.allure.Description;
import models.Setting.RegulationItem;
import org.testng.annotations.Test;
import utils.Constants;

import static org.apache.commons.lang3.math.NumberUtils.toDouble;


public class Regulations extends BaseTest {

    private String regulationName;
    @Test(priority = 1)
    public void UMG002_VerifyRegulationsDetailsMatchBetweenFormAndTable() throws InterruptedException {

        loginPage.login(Constants.EMAIL, Constants.PASSWORD);
        Thread.sleep(2000);

        regulationsPage.goToRegulationsPage();
        Thread.sleep(2000);

        String regulationName = "Regulation Auto Test";
        this.regulationName = regulationName;

        String requiredCredits = "91";
        String electiveCredits = "38";
        String gpa = "2.2";

        String course = "48";
        String major = "Quản trị hệ thống thông tin";

        regulationsPage.addRegulation(
                regulationName,
                requiredCredits,
                electiveCredits,
                gpa,
                course,
                major
        );

        regulationsPage.refreshPage();
        Thread.sleep(2000);

        RegulationItem actual = regulationsPage.getRegulationByName(regulationName);
        Thread.sleep(2000);

        softAssert.assertTrue(actual.getName().equalsIgnoreCase(regulationName), "Name mismatch");
        softAssert.assertTrue(actual.getMajor().equalsIgnoreCase(major), "Major mismatch");
        softAssert.assertTrue(actual.getCourse().contains(course), "Course mismatch");

        regulationsPage.openRegulationByName(regulationName);
        Thread.sleep(2000);

        softAssert.assertEquals(
                toDouble(regulationsPage.getValueByCondition("Tín chỉ bắt buộc")),
                toDouble(requiredCredits),
                "Required credits mismatch"
        );

        softAssert.assertEquals(
                toDouble(regulationsPage.getValueByCondition("Tín chỉ tự chọn")),
                toDouble(electiveCredits),
                "Elective credits mismatch"
        );

        softAssert.assertEquals(
                toDouble(regulationsPage.getValueByCondition("GPA")),
                toDouble(gpa),
                "GPA mismatch"
        );

        softAssert.assertEquals(
                regulationsPage.getValueByCondition("Chứng chỉ đầu ra").trim(),
                "Đủ theo khoá",
                "Certificate condition mismatch"
        );

        softAssert.assertAll();
    }
    @Test(priority = 2)
    public void UMG012_VerifyUserCanEditRegulationNameOnly() throws InterruptedException {

        loginPage.login(Constants.EMAIL, Constants.PASSWORD);
        Thread.sleep(2000);

        regulationsPage.goToRegulationsPage();
        Thread.sleep(2000);

        String oldName = regulationName;
        String newName = oldName + "_UPDATED";

        regulationsPage.openEditFormByName(oldName);
        Thread.sleep(1000);

        regulationsPage.editRegulationName(newName);

        Thread.sleep(2000);

        regulationsPage.refreshPage();
        Thread.sleep(2000);

        regulationName = newName;
        RegulationItem actual = regulationsPage.getRegulationByName(newName);

        softAssert.assertTrue(
                actual.getName().equalsIgnoreCase(newName),
                "Name mismatch after edit"
        );

        softAssert.assertAll();
    }
    @Test(priority = 3)
    public void UMG014_VerifyUserCanEditRequiredCredits() throws InterruptedException {

        loginPage.login(Constants.EMAIL, Constants.PASSWORD);
        Thread.sleep(2000);

        regulationsPage.goToRegulationsPage();
        Thread.sleep(2000);

        String currentName = regulationName;

        String newRequiredCredits = "98";

        regulationsPage.openEditFormByName(currentName);
        Thread.sleep(2000);

        regulationsPage.editRequiredCredits(newRequiredCredits);

        Thread.sleep(2000);

        regulationsPage.refreshPage();
        Thread.sleep(2000);

        regulationsPage.openRegulationByName(currentName);
        Thread.sleep(2000);
        softAssert.assertEquals(
                toDouble(regulationsPage.getValueByCondition("Tín chỉ bắt buộc")),
                toDouble(newRequiredCredits),
                "Required credits mismatch"
        );

        softAssert.assertAll();
    }
    @Test(priority = 4)
    public void UMG015_VerifyUserCanEditElectiveCredits() throws InterruptedException {

        loginPage.login(Constants.EMAIL, Constants.PASSWORD);
        Thread.sleep(2000);

        regulationsPage.goToRegulationsPage();
        Thread.sleep(2000);

        String currentName = regulationName; // quan trọng

        String newElectiveCredits = "45";

        regulationsPage.openEditFormByName(currentName);
        Thread.sleep(1000);

        regulationsPage.editElectiveCredits(newElectiveCredits);

        Thread.sleep(2000);

        regulationsPage.refreshPage();
        Thread.sleep(2000);
        regulationsPage.openRegulationByName(currentName);

        softAssert.assertEquals(
                toDouble(regulationsPage.getValueByCondition("Tín chỉ tự chọn")),
                toDouble(newElectiveCredits),
                "Elective credits mismatch"
        );

        softAssert.assertAll();
    }
    @Test(priority = 5)
    public void UMG016_VerifyUserCanEditGPA() throws InterruptedException {

        loginPage.login(Constants.EMAIL, Constants.PASSWORD);
        Thread.sleep(2000);

        regulationsPage.goToRegulationsPage();
        Thread.sleep(2000);

        String currentName = regulationName; // quan trọng

        String newGpa = "3.5";

        regulationsPage.openEditFormByName(currentName);
        Thread.sleep(1000);

        regulationsPage.editGPA(newGpa);

        Thread.sleep(2000);

        regulationsPage.refreshPage();
        Thread.sleep(2000);

        regulationsPage.openRegulationByName(currentName);

        softAssert.assertEquals(
                toDouble(regulationsPage.getValueByCondition("GPA")),
                toDouble(newGpa),
                "GPA mismatch"
        );

        softAssert.assertAll();
    }

    @Description("The regulation is deleted successfully")
    @Test(priority =6)
    public void UMG009_VerifyRegulationCanBeDeleted() throws InterruptedException {

        Thread.sleep(1000);
        loginPage.login(Constants.EMAIL, Constants.PASSWORD);

        Thread.sleep(3000);
        regulationsPage.goToRegulationsPage();

        Thread.sleep(1000);

        String deletedName = regulationName;

        regulationsPage.searchRegulation(deletedName);
        Thread.sleep(2000);

        regulationsPage.deleteRegulationByName(deletedName);
        Thread.sleep(2000);

        regulationsPage.searchRegulation(deletedName);
        Thread.sleep(2000);

        softAssert.assertFalse(
                regulationsPage.verifySearchResultContainsKeyword(deletedName),
                "Regulation vẫn còn sau khi delete"
        );

        softAssert.assertAll();
    }
    @Test(priority = 3)
    public void UMG003_VerifyErrorWhenRegulationNameIsEmpty() throws InterruptedException {

        Faker faker = new Faker();

        Thread.sleep(1000);
        loginPage.login(Constants.EMAIL, Constants.PASSWORD);

        Thread.sleep(1000);
        regulationsPage.goToRegulationsPage();

        Thread.sleep(1000);

        String regulationName = "";

        String requiredCredits = String.valueOf(faker.number().numberBetween(50, 100));
        String electiveCredits = String.valueOf(faker.number().numberBetween(20, 60));
        String gpa = String.valueOf(faker.number().randomDouble(1, 2, 4));

        String course = "48";
        String major = "Quản trị hệ thống thông tin";

        regulationsPage.addRegulation(
                regulationName,
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


    @Test(priority = 4)
    public void UMG004_VerifyErrorWhenCourseIsNotSelected() throws InterruptedException {

        Faker faker = new Faker();

        Thread.sleep(1000);
        loginPage.login(Constants.EMAIL, Constants.PASSWORD);

        Thread.sleep(1000);
        regulationsPage.goToRegulationsPage();

        Thread.sleep(1000);

        String regulationName = faker.educator().course();

        String requiredCredits = String.valueOf(faker.number().numberBetween(50, 100));
        String electiveCredits = String.valueOf(faker.number().numberBetween(20, 60));
        String gpa = String.valueOf(faker.number().randomDouble(1, 2, 4));

        regulationsPage.fillToGpaAndSave(
                regulationName,
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

    @Issue("R001")
    @Test(priority = 5)
    public void UMG006_VerifyErrorWhenRequiredCreditsIsNegative() throws InterruptedException {

        Faker faker = new Faker();

        Thread.sleep(1000);
        loginPage.login(Constants.EMAIL, Constants.PASSWORD);

        Thread.sleep(1000);
        regulationsPage.goToRegulationsPage();

        Thread.sleep(1000);

        String regulationName = faker.educator().course();
        String requiredCredits = "-" + faker.number().numberBetween(50, 100);
        String electiveCredits = String.valueOf(faker.number().numberBetween(20, 60));
        String gpa = String.valueOf(faker.number().randomDouble(1, 0, 4));

        String course = "46";
        String major = "Thống kê";

        regulationsPage.addRegulation(
                regulationName,
                requiredCredits,
                electiveCredits,
                gpa,
                course,
                major
        );

        Thread.sleep(1000);

        softAssert.assertTrue(
                regulationsPage.isErrorMessageDisplayed(),
                "Không hiển thị lỗi Required Credits âm"
        );
        Thread.sleep(1000);
        regulationsPage.deleteRegulationByName(regulationName);

        softAssert.assertAll();
    }

    @Issue("R002")
    @Test(priority = 6)
    public void UMG007_VerifyErrorWhenElectiveCreditsIsNegative() throws InterruptedException {

        Faker faker = new Faker();

        Thread.sleep(1000);
        loginPage.login(Constants.EMAIL, Constants.PASSWORD);

        Thread.sleep(1000);
        regulationsPage.goToRegulationsPage();

        Thread.sleep(1000);

        String regulationName = faker.educator().course();

        String requiredCredits = String.valueOf(faker.number().numberBetween(50, 100));
        String electiveCredits = "-" + faker.number().numberBetween(20, 60);
        String gpa = String.valueOf(faker.number().randomDouble(1, 0, 4));

        String course = "46";
        String major = "Thống kê";

        regulationsPage.addRegulation(
                regulationName,
                requiredCredits,
                electiveCredits,
                gpa,
                course,
                major
        );

        Thread.sleep(1000);

        softAssert.assertTrue(
                regulationsPage.isErrorMessageDisplayed(),
                "Không hiển thị lỗi Elective Credits âm"
        );
        regulationsPage.deleteRegulationByName(regulationName);
        softAssert.assertAll();
    }

    @Issue("R003")
    @Test(priority = 7)
    public void UMG008_VerifyErrorWhenGpaIsNegative() throws InterruptedException {

        Faker faker = new Faker();

        Thread.sleep(1000);
        loginPage.login(Constants.EMAIL, Constants.PASSWORD);

        Thread.sleep(1000);
        regulationsPage.goToRegulationsPage();

        Thread.sleep(1000);
        String regulationName = faker.educator().course();
        String requiredCredits = String.valueOf(faker.number().numberBetween(50, 100));
        String electiveCredits = String.valueOf(faker.number().numberBetween(20, 60));
        String gpa = "-" + faker.number().numberBetween(1, 4);

        String course = "46";
        String major = "Thống kê";

        regulationsPage.addRegulation(
                regulationName,
                requiredCredits,
                electiveCredits,
                gpa,
                course,
                major
        );

        Thread.sleep(1000);

        softAssert.assertTrue(
                regulationsPage.isErrorMessageDisplayed(),
                "Không hiển thị lỗi GPA âm"
        );
        regulationsPage.deleteRegulationByName(regulationName);
        softAssert.assertAll();
    }


    @Description("User can search regulation by name")
    @Test(priority = 8)
    public void UMG010_VerifyUserCanSearchRegulationByName() throws InterruptedException {

        Thread.sleep(1000);
        loginPage.login(Constants.EMAIL, Constants.PASSWORD);

        Thread.sleep(1000);
        regulationsPage.goToRegulationsPage();

        Thread.sleep(1000);


        RegulationItem randomRegulation = regulationsPage.getRandomRegulation();
        String name = randomRegulation.getName();

        regulationsPage.searchRegulation(name);

        Thread.sleep(1000);

        softAssert.assertTrue(
                regulationsPage.verifySearchResultContainsKeyword(name),
                "Search result không chứa tên quy chế"
        );

        softAssert.assertAll();
    }
    @Description("User can search regulation with non-existing name")
    @Test(priority = 9)
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
