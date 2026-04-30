package test;

import common.BaseTest;
import org.testng.annotations.Test;

public class Cohort extends BaseTest {

    private String cohortCode;
    private String startYear;
    private String endYear;

    private String updatedStartYear;
    private String updatedEndYear;

    /* ================= SEARCH ================= */

    @Test(priority = 1)
    public void CH_01_UserCanSearchByCohortCode() {

        cohortPage.goToCohortPage();

        String keyword = cohortPage.getRandomCohortCode().trim();

        cohortPage.searchCohort(keyword);
        cohortPage.waitForTableLoad();

        softAssert.assertTrue(
                cohortPage.verifyCohortContainsCode(keyword),
                "Search result does not contain cohort code"
        );

        softAssert.assertAll();
    }

    @Test(priority = 2)
    public void CH_02_SearchWithNonExistingKeywordReturnsEmpty() throws InterruptedException {

        cohortPage.goToCohortPage();

        String keyword = "zzz_not_exist_" + System.currentTimeMillis();

        cohortPage.searchCohort(keyword);
        Thread.sleep(2000);

        softAssert.assertEquals(
                cohortPage.getNoDataMessage(),
                "Không có khóa nào",
                "No data message is incorrect"
        );

        softAssert.assertAll();
    }

    /* ================= ADD ================= */

    @Test(priority = 3)
    public void CH_03_UserCanAddCohortSuccessfully() throws InterruptedException {

        cohortPage.goToCohortPage();
        cohortPage.openAddCohortForm();

        cohortCode = cohortPage.generateUniqueCohortCode();

        startYear = "2020";
        endYear = "2025";

        cohortPage.addCohort(cohortCode, startYear, endYear);

        Thread.sleep(1500);

        cohortPage.refreshPage();
        cohortPage.waitForTableLoad();

        cohortPage.searchCohort(cohortCode);
        cohortPage.waitForCohortAppear(cohortCode);

        softAssert.assertEquals(
                cohortPage.getStartYearByCode(cohortCode),
                startYear,
                "Start year mismatch"
        );

        softAssert.assertEquals(
                cohortPage.getEndYearByCode(cohortCode),
                endYear,
                "End year mismatch"
        );

        softAssert.assertAll();
    }

    /* ================= NEGATIVE TEST ================= */

    @Test(priority = 4)
    public void CH_04_AddCohortWithDuplicateStartYear_ShouldShowErrorMessage() {

        cohortPage.goToCohortPage();
        cohortPage.openAddCohortForm();

        String startYear = cohortPage.getRandomExistingStartYear();
        String cohortCode = "COH_" + System.currentTimeMillis();
        String endYear = String.valueOf(Integer.parseInt(startYear) + 4);

        cohortPage.addCohort(cohortCode, startYear, endYear);

        cohortPage.clickOutsideForm();

        softAssert.assertTrue(
                cohortPage.isToastMessageDisplayed(),
                "Error message is not displayed"
        );

        softAssert.assertAll();
    }

    @Test(priority = 5)
    public void CH_05_AddCohortWithDuplicateCode_ShouldShowErrorMessage() {

        cohortPage.goToCohortPage();
        cohortPage.openAddCohortForm();

        String existingCode = cohortPage.getRandomExistingCohortCode();
        String startYear = cohortPage.getRandomExistingStartYear();
        String endYear = String.valueOf(Integer.parseInt(startYear) + 4);

        cohortPage.addCohort(existingCode, startYear, endYear);

        cohortPage.clickOutsideForm();

        softAssert.assertTrue(
                cohortPage.isToastMessageDisplayed(),
                "Error message is not displayed"
        );

        softAssert.assertAll();
    }

    /* ================= EDIT ================= */

    @Test(priority = 6, dependsOnMethods = "CH_03_UserCanAddCohortSuccessfully")
    public void CH_06_UserCanEditStartYear() throws InterruptedException {

        cohortPage.goToCohortPage();

        cohortPage.searchCohort(cohortCode);
        cohortPage.waitForTableLoad();
        cohortPage.waitForCohortAppear(cohortCode);

        cohortPage.openEditCohortForm(cohortCode);

        String currentEndYear = cohortPage.getEndYearByCode(cohortCode);

        updatedStartYear = cohortPage.generateStartYear(currentEndYear);

        cohortPage.editStartYear(updatedStartYear);

        Thread.sleep(1500);

        cohortPage.refreshPage();
        cohortPage.waitForTableLoad();

        cohortPage.searchCohort(cohortCode);
        cohortPage.waitForCohortAppear(cohortCode);

        softAssert.assertEquals(
                cohortPage.getStartYearByCode(cohortCode),
                updatedStartYear,
                "Start year was not updated correctly"
        );

        softAssert.assertAll();
    }

    @Test(priority = 7, dependsOnMethods = "CH_06_UserCanEditStartYear")
    public void CH_06_UserCanEditEndYear() throws InterruptedException {

        cohortPage.goToCohortPage();

        cohortPage.searchCohort(cohortCode);
        cohortPage.waitForTableLoad();
        cohortPage.waitForCohortAppear(cohortCode);

        cohortPage.openEditCohortForm(cohortCode);

        Thread.sleep(500);

        String latestStartYear = cohortPage.getStartYearByCode(cohortCode);

        updatedEndYear = cohortPage.generateEndYear(latestStartYear);

        assert Integer.parseInt(updatedEndYear) > Integer.parseInt(latestStartYear)
                : "End year must be greater than start year";

        cohortPage.editEndYear(updatedEndYear);

        Thread.sleep(1500);

        cohortPage.refreshPage();
        cohortPage.waitForTableLoad();

        cohortPage.searchCohort(cohortCode);
        cohortPage.waitForCohortAppear(cohortCode);

        softAssert.assertEquals(
                cohortPage.getEndYearByCode(cohortCode),
                updatedEndYear,
                "End year was not updated correctly"
        );

        softAssert.assertAll();
    }

    /* ================= DELETE ================= */

    @Test(priority = 8, dependsOnMethods = "CH_03_UserCanAddCohortSuccessfully")
    public void CH_07_UserCanDeleteCohortSuccessfully() throws InterruptedException {

        cohortPage.goToCohortPage();

        cohortPage.searchCohort(cohortCode);
        cohortPage.waitForTableLoad();
        cohortPage.waitForCohortAppear(cohortCode);

        cohortPage.deleteCohort(cohortCode);

        Thread.sleep(2000);

        cohortPage.refreshPage();
        cohortPage.waitForTableLoad();

        cohortPage.searchCohort(cohortCode);

        softAssert.assertEquals(
                cohortPage.getNoDataMessage(),
                "Không có khóa nào",
                "No data message is incorrect"
        );

        softAssert.assertAll();
    }
}