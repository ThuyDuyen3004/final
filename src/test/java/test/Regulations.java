//package test;
//
//import com.github.javafaker.Faker;
//import common.BaseTest;
//import io.qameta.allure.Description;
//import io.qameta.allure.Issue;
//import models.Setting.RegulationItem;
//import org.testng.annotations.Test;
//
//import static org.apache.commons.lang3.math.NumberUtils.toDouble;
//
//public class Regulations extends BaseTest {
//
//    private String regulationName;
//
//    @Test
//    public void UMG002_VerifyRegulationsDetailsMatchBetweenFormAndTable() throws InterruptedException {
//
//        regulationsPage.goToRegulationsPage();
//        Thread.sleep(2000);
//
//        Faker faker = new Faker();
//
//        String regulationName = "Regulation " + faker.lorem().word();
//        this.regulationName = regulationName;
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
//
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
//
//    @Test
//    public void UMG012_VerifyUserCanEditRegulationNameOnly() throws InterruptedException {
//
//        regulationsPage.goToRegulationsPage();
//
//        String oldName = regulationName;
//        String newName = oldName + "_UPDATED";
//
//        regulationsPage.openEditFormByName(oldName);
//        Thread.sleep(1000);
//
//        regulationsPage.editRegulationName(newName);
//
//        regulationsPage.refreshPage();
//
//        regulationName = newName;
//
//        RegulationItem actual = regulationsPage.getRegulationByName(newName);
//
//        softAssert.assertTrue(
//                actual.getName().equalsIgnoreCase(newName),
//                "Name mismatch after edit"
//        );
//
//        softAssert.assertAll();
//    }
//
//    @Test
//    public void UMG014_VerifyUserCanEditRequiredCredits() throws InterruptedException {
//
//        regulationsPage.goToRegulationsPage();
//        Thread.sleep(2000);
//
//        String currentName = regulationName;
//        String newRequiredCredits = "98";
//
//        regulationsPage.openEditFormByName(currentName);
//
//        regulationsPage.editRequiredCredits(newRequiredCredits);
//
//        regulationsPage.refreshPage();
//
//        regulationsPage.openRegulationByName(currentName);
//
//        softAssert.assertEquals(
//                toDouble(regulationsPage.getValueByCondition("Tín chỉ bắt buộc")),
//                toDouble(newRequiredCredits),
//                "Required credits mismatch"
//        );
//
//        softAssert.assertAll();
//    }
//
//    @Test
//    public void UMG015_VerifyUserCanEditElectiveCredits() throws InterruptedException {
//
//        regulationsPage.goToRegulationsPage();
//
//        String currentName = regulationName;
//        String newElectiveCredits = "45";
//
//        regulationsPage.openEditFormByName(currentName);
//
//        regulationsPage.editElectiveCredits(newElectiveCredits);
//
//        regulationsPage.refreshPage();
//
//        regulationsPage.openRegulationByName(currentName);
//
//        softAssert.assertEquals(
//                toDouble(regulationsPage.getValueByCondition("Tín chỉ tự chọn")),
//                toDouble(newElectiveCredits),
//                "Elective credits mismatch"
//        );
//
//        softAssert.assertAll();
//    }
//
//    @Test
//    public void UMG016_VerifyUserCanEditGPA() throws InterruptedException {
//
//        regulationsPage.goToRegulationsPage();
//
//        String currentName = regulationName;
//        String newGpa = "3.5";
//
//        regulationsPage.openEditFormByName(currentName);
//
//        regulationsPage.editGPA(newGpa);
//
//        regulationsPage.refreshPage();
//        regulationsPage.openRegulationByName(currentName);
//
//        softAssert.assertEquals(
//                toDouble(regulationsPage.getValueByCondition("GPA")),
//                toDouble(newGpa),
//                "GPA mismatch"
//        );
//
//        softAssert.assertAll();
//    }
//
//    @Description("The regulation is deleted successfully")
//    @Test
//    public void UMG009_VerifyRegulationCanBeDeleted() throws InterruptedException {
//
//        regulationsPage.goToRegulationsPage();
//
//        String deletedName = regulationName;
//
//        regulationsPage.searchRegulation(deletedName);
//
//        regulationsPage.deleteRegulationByName(deletedName);
//
//        regulationsPage.searchRegulation(deletedName);
//
//        softAssert.assertFalse(
//                regulationsPage.verifySearchResultContainsKeyword(deletedName),
//                "Regulation vẫn còn sau khi delete"
//        );
//
//        softAssert.assertAll();
//    }
//
//    @Test
//    public void UMG003_VerifyErrorWhenRegulationNameIsEmpty() throws InterruptedException {
//
//        Faker faker = new Faker();
//
//        regulationsPage.goToRegulationsPage();
//
//        String regulationName = "";
//
//        String requiredCredits = String.valueOf(faker.number().numberBetween(50, 100));
//        String electiveCredits = String.valueOf(faker.number().numberBetween(20, 60));
//        String gpa = String.valueOf(faker.number().randomDouble(1, 2, 4));
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
//        softAssert.assertEquals(
//                regulationsPage.getMessage(),
//                "Vui lòng nhập tên quy chế",
//                "Sai hoặc không hiển thị message lỗi"
//        );
//
//        softAssert.assertAll();
//    }
//
//    @Test
//    public void UMG004_VerifyErrorWhenCourseIsNotSelected() throws InterruptedException {
//
//        Faker faker = new Faker();
//
//        regulationsPage.goToRegulationsPage();
//
//        String regulationName = faker.educator().course();
//
//        String requiredCredits = String.valueOf(faker.number().numberBetween(50, 100));
//        String electiveCredits = String.valueOf(faker.number().numberBetween(20, 60));
//        String gpa = String.valueOf(faker.number().randomDouble(1, 2, 4));
//
//        regulationsPage.fillToGpaAndSave(
//                regulationName,
//                requiredCredits,
//                electiveCredits,
//                gpa
//        );
//
//        softAssert.assertEquals(
//                regulationsPage.getMessage().trim(),
//                "Vui lòng chọn ít nhất một khoá áp dụng",
//                "Sai hoặc không hiển thị message lỗi"
//        );
//
//        softAssert.assertAll();
//    }
//
//    @Issue("R001")
//    @Test
//    public void UMG006_VerifyErrorWhenRequiredCreditsIsNegative() throws InterruptedException {
//
//        Faker faker = new Faker();
//
//        regulationsPage.goToRegulationsPage();
//
//        String regulationName = faker.educator().course();
//        String requiredCredits = "-" + faker.number().numberBetween(50, 100);
//        String electiveCredits = String.valueOf(faker.number().numberBetween(20, 60));
//        String gpa = String.valueOf(faker.number().randomDouble(1, 0, 4));
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
//        softAssert.assertTrue(
//                regulationsPage.isErrorMessageDisplayed(),
//                "Không hiển thị lỗi Required Credits âm"
//        );
//
//        regulationsPage.deleteRegulationByName(regulationName);
//
//        softAssert.assertAll();
//    }
//
//    @Issue("R002")
//    @Test
//    public void UMG007_VerifyErrorWhenElectiveCreditsIsNegative() throws InterruptedException {
//
//        Faker faker = new Faker();
//
//        regulationsPage.goToRegulationsPage();
//
//        String regulationName = faker.educator().course();
//
//        String requiredCredits = String.valueOf(faker.number().numberBetween(50, 100));
//        String electiveCredits = "-" + faker.number().numberBetween(20, 60);
//        String gpa = String.valueOf(faker.number().randomDouble(1, 0, 4));
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
//        softAssert.assertTrue(
//                regulationsPage.isErrorMessageDisplayed(),
//                "Không hiển thị lỗi Elective Credits âm"
//        );
//
//        regulationsPage.deleteRegulationByName(regulationName);
//
//        softAssert.assertAll();
//    }
//
//    @Issue("R003")
//    @Test
//    public void UMG008_VerifyErrorWhenGpaIsNegative() throws InterruptedException {
//
//        Faker faker = new Faker();
//
//        regulationsPage.goToRegulationsPage();
//
//        String regulationName = faker.educator().course();
//        String requiredCredits = String.valueOf(faker.number().numberBetween(50, 100));
//        String electiveCredits = String.valueOf(faker.number().numberBetween(20, 60));
//        String gpa = "-" + faker.number().numberBetween(1, 4);
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
//        softAssert.assertTrue(
//                regulationsPage.isErrorMessageDisplayed(),
//                "Không hiển thị lỗi GPA âm"
//        );
//
//        regulationsPage.deleteRegulationByName(regulationName);
//
//        softAssert.assertAll();
//    }
//
//    @Description("User can search regulation by name")
//    @Test
//    public void UMG010_VerifyUserCanSearchRegulationByName() throws InterruptedException {
//
//        regulationsPage.goToRegulationsPage();
//
//        RegulationItem randomRegulation = regulationsPage.getRandomRegulation();
//        String name = randomRegulation.getName();
//
//        regulationsPage.searchRegulation(name);
//
//        softAssert.assertTrue(
//                regulationsPage.verifySearchResultContainsKeyword(name),
//                "Search result không chứa tên quy chế"
//        );
//
//        softAssert.assertAll();
//    }
//
//    @Description("User can search regulation with non-existing name")
//    @Test
//    public void UMG011_VerifySearchRegulation_NoDataDisplayed() throws InterruptedException {
//
//        regulationsPage.goToRegulationsPage();
//
//        String invalidKeyword = regulationsPage.generateNonExistingKeyword();
//
//        regulationsPage.searchRegulation(invalidKeyword);
//
//        softAssert.assertTrue(
//                regulationsPage.isRegulationTableEmpty(),
//                "Regulation table should be empty but still has data"
//        );
//
//        softAssert.assertAll();
//    }
//}
package test;

import com.github.javafaker.Faker;
import common.BaseTest;
import io.qameta.allure.Description;
import models.RegulationItem;
import org.testng.annotations.Test;

import static org.apache.commons.lang3.math.NumberUtils.toDouble;

public class Regulations extends BaseTest {

    private String regulationName;

//    // ================= CREATE =================
//    @Test(priority = 1)
//    public void UMG002_VerifyRegulationsDetailsMatchBetweenFormAndTable() throws InterruptedException {
//
//        regulationsPage.goToRegulationsPage();
//
//        Faker faker = new Faker();
//
//        String regulationName = "Regulation " + faker.lorem().word();
//        this.regulationName = regulationName;
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
//
//        RegulationItem actual = regulationsPage.getRegulationByName(regulationName);
//
//        softAssert.assertTrue(actual.getName().equalsIgnoreCase(regulationName));
//
//        softAssert.assertAll();
//    }

    // ================= EDIT NAME =================
    @Test(priority = 2)
    public void UMG012_VerifyUserCanEditRegulationNameOnly() throws InterruptedException {

        regulationsPage.goToRegulationsPage();

        String oldName = regulationName;
        String newName = oldName + "_UPDATED";

        regulationsPage.openEditFormByName("Name");
        regulationsPage.editRegulationName(newName);

        regulationsPage.refreshPage();

        regulationName = newName;
        Thread.sleep(1000);

        RegulationItem actual = regulationsPage.getRegulationByName(newName);

        softAssert.assertTrue(actual.getName().equalsIgnoreCase(newName));

        softAssert.assertAll();
    }

    // ================= EDIT REQUIRED =================
    @Test(priority = 3)
    public void UMG014_VerifyUserCanEditRequiredCredits() throws InterruptedException {

        regulationsPage.goToRegulationsPage();

        String newValue = "98";

        regulationsPage.openEditFormByName(regulationName);
        regulationsPage.editRequiredCredits(newValue);

        regulationsPage.refreshPage();
        regulationsPage.openRegulationByName(regulationName);
        Thread.sleep(1000);
        softAssert.assertEquals(
                toDouble(regulationsPage.getValueByCondition("Tín chỉ bắt buộc")),
                toDouble(newValue)
        );
        Thread.sleep(1000);
        softAssert.assertAll();
    }

    // ================= EDIT ELECTIVE =================
    @Test(priority = 4)
    public void UMG015_VerifyUserCanEditElectiveCredits() throws InterruptedException {

        regulationsPage.goToRegulationsPage();

        String newValue = "45";

        regulationsPage.openEditFormByName(regulationName);
        regulationsPage.editElectiveCredits(newValue);

        regulationsPage.refreshPage();
        regulationsPage.openRegulationByName(regulationName);
        Thread.sleep(1000);
        softAssert.assertEquals(
                toDouble(regulationsPage.getValueByCondition("Tín chỉ tự chọn")),
                toDouble(newValue)
        );
        Thread.sleep(1000);
        softAssert.assertAll();
    }

    // ================= EDIT GPA =================
    @Test(priority = 5)
    public void UMG016_VerifyUserCanEditGPA() throws InterruptedException {

        regulationsPage.goToRegulationsPage();

        String newValue = "3.5";

        regulationsPage.openEditFormByName(regulationName);
        regulationsPage.editGPA(newValue);

        regulationsPage.refreshPage();
        regulationsPage.openRegulationByName(regulationName);
        Thread.sleep(1000);
        softAssert.assertEquals(
                toDouble(regulationsPage.getValueByCondition("GPA")),
                toDouble(newValue)
        );
        Thread.sleep(1000);
        softAssert.assertAll();
    }

    // ================= SEARCH VALID =================
    @Test(priority = 6)
    public void UMG010_VerifyUserCanSearchRegulationByName() throws InterruptedException {

        regulationsPage.goToRegulationsPage();

        RegulationItem random = regulationsPage.getRandomRegulation();

        regulationsPage.searchRegulation(random.getName());

        softAssert.assertTrue(
                regulationsPage.verifySearchResultContainsKeyword(random.getName())
        );

        softAssert.assertAll();
    }

    // ================= SEARCH INVALID =================
    @Test(priority = 7)
    public void UMG011_VerifySearchRegulation_NoDataDisplayed() throws InterruptedException {

        regulationsPage.goToRegulationsPage();

        String invalid = regulationsPage.generateNonExistingKeyword();

        regulationsPage.searchRegulation(invalid);

        softAssert.assertTrue(regulationsPage.isRegulationTableEmpty());

        softAssert.assertAll();
    }

    // ================= EMPTY NAME =================
    @Test(priority = 8)
    public void UMG003_VerifyErrorWhenRegulationNameIsEmpty() throws InterruptedException {

        Faker faker = new Faker();

        regulationsPage.goToRegulationsPage();

        regulationsPage.addRegulation(
                "",
                String.valueOf(faker.number().numberBetween(50, 100)),
                String.valueOf(faker.number().numberBetween(20, 60)),
                String.valueOf(faker.number().randomDouble(1, 2, 4)),
                "48",
                "Quản trị hệ thống thông tin"
        );

        softAssert.assertEquals(
                regulationsPage.getMessage(),
                "Vui lòng nhập tên quy chế"
        );

        softAssert.assertAll();
    }

    // ================= COURSE NOT SELECTED =================
    @Test(priority = 9)
    public void UMG004_VerifyErrorWhenCourseIsNotSelected() throws InterruptedException {

        Faker faker = new Faker();

        regulationsPage.goToRegulationsPage();

        regulationsPage.fillToGpaAndSave(
                faker.educator().course(),
                String.valueOf(faker.number().numberBetween(50, 100)),
                String.valueOf(faker.number().numberBetween(20, 60)),
                String.valueOf(faker.number().randomDouble(1, 2, 4))
        );

        softAssert.assertEquals(
                regulationsPage.getMessage().trim(),
                "Vui lòng chọn ít nhất một khoá áp dụng"
        );

        softAssert.assertAll();
    }


    // ================= DELETE LAST =================
    @Description("Delete regulation successfully")
    @Test(priority = 13)
    public void UMG009_VerifyRegulationCanBeDeleted() throws InterruptedException {

        regulationsPage.goToRegulationsPage();

        String deletedName = regulationName;

        regulationsPage.searchRegulation(deletedName);
        regulationsPage.deleteRegulationByName(deletedName);
        Thread.sleep(1000);
        regulationsPage.searchRegulation(deletedName);
        softAssert.assertFalse(
                regulationsPage.verifySearchResultContainsKeyword(deletedName)
        );

        softAssert.assertAll();
    }
}