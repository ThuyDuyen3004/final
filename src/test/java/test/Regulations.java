package test;

import com.github.javafaker.Faker;
import common.BaseTest;
import models.RegulationItem;
import org.testng.annotations.Test;

public class Regulations extends BaseTest {

    private String regulationName;

    // ================= CREATE =================
    @Test(priority = 1)
    public void UMG01_UserCanCreateRegulationSuccessfully() throws InterruptedException {

        regulationsPage.goToRegulationsPage();

        Faker faker = new Faker();

        String regulationName = "Regulation " + faker.lorem().word();
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
        Thread.sleep(2000);
        regulationsPage.searchRegulation(regulationName);
        Thread.sleep(2000);

        softAssert.assertTrue(
                regulationsPage.verifySearchResultContainsKeyword(regulationName)
        );

        softAssert.assertAll();
    }

    // ================= DUPLICATE =================
    @Test(priority = 2)
    public void UMG02_CannotAddDuplicateRegulation() throws InterruptedException {

        regulationsPage.goToRegulationsPage();

        Faker faker = new Faker();

        String regulationName = "Regulation " + faker.lorem().word();

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

        softAssert.assertTrue(
                regulationsPage.isErrorMessageDisplayed(),
                "Error message should be displayed when duplicate regulation"
        );

        softAssert.assertAll();
    }

    // ================= VALIDATION =================
    @Test(priority = 3)
    public void UMG03_CannotAddRegulationWithEmptyName() throws InterruptedException {

        regulationsPage.goToRegulationsPage();

        String regulationName = "";

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

        softAssert.assertEquals(
                regulationsPage.getErrorMsg(),
                "Vui lòng nhập tên quy chế"
        );

        softAssert.assertAll();
    }

    @Test(priority = 4)
    public void UMG04_CannotAddRegulationWithEmptyMajor() {

        Faker faker = new Faker();

        regulationsPage.goToRegulationsPage();

        regulationsPage.addRegulationWithoutMajor(
                faker.name().title(),
                String.valueOf(faker.number().numberBetween(50, 100)),
                String.valueOf(faker.number().numberBetween(20, 60)),
                String.valueOf(faker.number().randomDouble(1, 2, 4)),
                "48"
        );

        String actualMessage = regulationsPage.getMessage();

        softAssert.assertEquals(
                actualMessage,
                "Vui lòng chọn chuyên ngành cho từng khóa áp dụng"
        );

        softAssert.assertAll();
    }

    // ================= SEARCH =================
    @Test(priority = 5)
    public void UMG05_UserCanSearchRegulationByName() throws InterruptedException {

        regulationsPage.goToRegulationsPage();
        Thread.sleep(2000);

        RegulationItem random = regulationsPage.getRandomRegulation();

        regulationsPage.searchRegulation(random.getName());

        softAssert.assertTrue(
                regulationsPage.verifySearchResultContainsKeyword(random.getName())
        );

        softAssert.assertAll();
    }

    @Test(priority = 6)
    public void UMG06_SearchRegulationWithNoResult() throws InterruptedException {

        regulationsPage.goToRegulationsPage();
        Thread.sleep(2000);

        String invalid = regulationsPage.generateNonExistingKeyword();

        regulationsPage.searchRegulation(invalid);
        regulationsPage.waitForSearchResult();

        softAssert.assertEquals(
                regulationsPage.getMsg(),
                "Không có quy chế nào",
                "Regulation table should be empty but still has data"
        );

        softAssert.assertAll();
    }

    // ================= EDIT =================
    @Test(priority = 7)
    public void UMG07_UserCanEditRegulationName() throws InterruptedException{

        regulationsPage.goToRegulationsPage();

        regulationsPage.searchRegulation(regulationName);
        regulationsPage.waitForSearchResult();

        String newName = regulationName + "_UPDATED";
        regulationsPage.getRegulationByName(regulationName);

        regulationsPage.openEditFormByName(regulationName);
        regulationsPage.editRegulationName(newName);

        regulationsPage.refreshPage();
        regulationsPage.waitForTableLoaded();

        regulationName = newName;

        regulationsPage.searchRegulation(newName);
        regulationsPage.waitForSearchResult();
        Thread.sleep(2000);
        RegulationItem actual = regulationsPage.getRegulationByName(newName);

        softAssert.assertTrue(actual.getName().equalsIgnoreCase(newName));
        softAssert.assertAll();
    }

    @Test(priority = 8)
    public void UMG08_UserCanEditRequiredCredits() throws InterruptedException {

        regulationsPage.goToRegulationsPage();
        regulationsPage.searchRegulation(regulationName);
        regulationsPage.waitForSearchResult();

        String newValue = "98";

        regulationsPage.getRegulationByName(regulationName);

        regulationsPage.openEditFormByName(regulationName);
        regulationsPage.editRequiredCredits(newValue);

        regulationsPage.refreshPage();
        regulationsPage.waitForTableLoaded();
    }

    // ================= DELETE =================
    @Test(priority = 9)
    public void UMG09_UserCanDeleteRegulation() throws InterruptedException {

        regulationsPage.goToRegulationsPage();
        Thread.sleep(2000);

        String regulationName = regulationsPage.getRandomRegulationName();

        regulationsPage.deleteRegulation(regulationName);

        regulationsPage.waitForTableLoaded();

        Thread.sleep(2000);

        regulationsPage.searchRegulation(regulationName);
        regulationsPage.waitForSearchResult();

        softAssert.assertEquals(
                regulationsPage.getMsg(),
                "Không có quy chế nào",
                "Regulation table should be empty but still has data"
        );

        softAssert.assertAll();
    }
}