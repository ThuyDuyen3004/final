package test;

import common.BaseTest;
import org.testng.annotations.Test;

public class Cohort extends BaseTest {

    private String cohortCode;
    private String startYear;
    private String endYear;

    private String updatedCohortCode;
    private String updatedStartYear;
    private String updatedEndYear;

    /* ================= SEARCH ================= */

    @Test
    public void CH_01_UserCanSearchByCohortCode() {

        cohortPage.goToCohortPage();

        String code = cohortPage.getAllCohortCodes()
                .stream()
                .findAny()
                .orElse("K10");

        cohortPage.searchCohort(code);

        softAssert.assertTrue(
                cohortPage.verifySearchResult(code),
                "Search result does not contain cohort code"
        );

        softAssert.assertAll();
    }

    @Test
    public void CH_02_SearchWithNonExistingKeywordReturnsEmpty() throws InterruptedException {

        cohortPage.goToCohortPage();

        String keyword = "zzz_not_exist_" + System.currentTimeMillis();

        cohortPage.searchCohort(keyword);

        Thread.sleep(1000);

        softAssert.assertTrue(
                cohortPage.getTotalCohorts() == 0 ||
                        cohortPage.verifySearchResult(keyword),
                "Table is not empty after search"
        );

        softAssert.assertTrue(
                cohortPage.getTotalCohorts() == 0,
                "Expected empty result but still has data"
        );

        softAssert.assertAll();
    }

    /* ================= ADD ================= */

    @Test
    public void CH_03_UserCanAddCohortSuccessfully() throws InterruptedException {

        cohortPage.goToCohortPage();

        cohortCode = cohortPage.generateUniqueCohortCode();
        startYear = cohortPage.generateStartYear();
        endYear = cohortPage.generateEndYear(startYear);

        cohortPage.addCohort(cohortCode, startYear, endYear);

        Thread.sleep(1000);

        softAssert.assertTrue(
                cohortPage.getAllCohortCodes().contains(cohortCode),
                "Cohort is not displayed after adding"
        );
        Thread.sleep(1000);
        softAssert.assertAll();
    }


    /* ================= DELETE (optional) ================= */
//
//    @Test(dependsOnMethods = "CH_03_UserCanAddCohortSuccessfully")
//    public void CH_05_UserCanDeleteCohort() throws InterruptedException {
//
//        cohortPage.goToCohortPage();
//
//        // nếu bạn có delete method thì bật lên
//        // cohortPage.deleteCohort(updatedCohortCode);
//
//        Thread.sleep(1000);
//
//        cohortPage.searchCohort(cohortCode);
//
//        softAssert.assertTrue(
//                cohortPage.getTotalCohorts() == 0 ||
//                        cohortPage.isNoDataDisplayed(),
//                "Cohort is still displayed after deletion"
//        );
//
//        softAssert.assertAll();
//    }
}