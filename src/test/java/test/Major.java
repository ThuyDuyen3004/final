package test;

import common.BaseTest;
import org.testng.annotations.Test;

public class Major extends BaseTest {

    private String majorCode;
    private String majorName;
    private String updatedMajorCode;
    private String updatedMajorName;

    @Test(priority = 1)
    public void MJ_01_UserCanSearchByMajorName() {

        majorPage.goToMajorPage();

        String keyword = majorPage.getRandomMajorName().trim();

        majorPage.searchMajor(keyword);
        majorPage.waitForSearchResult();

        softAssert.assertTrue(
                majorPage.verifyMajorSearchResultContainsKeyword(keyword),
                "Search result does not contain major name"
        );

        softAssert.assertAll();
    }

    @Test(priority = 2)
    public void MJ_02_VerifySearchWithNonExistingKeywordReturnsEmpty() {

        majorPage.goToMajorPage();

        String keyword = "zzz_not_exist_" + System.currentTimeMillis();

        majorPage.searchMajor(keyword);
        majorPage.waitForSearchResult();

        softAssert.assertEquals(
                majorPage.getNoDataMessage(),
                "Không có chuyên ngành nào",
                "No data message is incorrect"
        );

        softAssert.assertAll();
    }

    @Test(priority = 3)
    public void MJ_03_UserCanAddMajorSuccessfully() throws InterruptedException {

        majorPage.goToMajorPage();
        majorPage.openAddMajorForm();

        majorCode = majorPage.generateUniqueMajorCode();
        majorName = majorPage.generateMajorName();

        majorPage.addMajor(majorCode, majorName);

        Thread.sleep(1000);

        majorPage.searchMajor(majorName);
        majorPage.waitForSearchResult();

        softAssert.assertTrue(
                majorPage.verifyMajorContainsCode(majorCode),
                "Major not found after adding"
        );

        softAssert.assertAll();
    }

    @Test(priority = 4)
    public void MJ_04_UserCanEditMajorCode() throws InterruptedException{

        majorPage.goToMajorPage();

        Thread.sleep(2000);
        majorPage.searchMajor(majorName);
        majorPage.waitForSearchResult();

        majorPage.openEditMajorForm();

        updatedMajorCode = majorPage.generateUniqueMajorCode();

        majorPage.editMajorCode(updatedMajorCode);

        Thread.sleep(2000);
        majorPage.searchMajor(majorName);
        majorPage.waitForSearchResult();

        softAssert.assertTrue(
                majorPage.verifyMajorContainsCode(updatedMajorCode),
                "Major code was not updated"
        );

        softAssert.assertAll();
    }

    @Test(priority = 5)
    public void MJ_05_UserCanEditMajorName() throws InterruptedException {

        majorPage.goToMajorPage();

        majorPage.searchMajor(majorName);
        majorPage.waitForSearchResult();

        updatedMajorName = majorPage.generateMajorName();
        Thread.sleep(1000);
        majorPage.openEditMajorForm();
        majorPage.editMajorName(updatedMajorName);
        Thread.sleep(1000);

        majorPage.searchMajor(updatedMajorName);
        majorPage.waitForSearchResult();

        softAssert.assertTrue(
                majorPage.verifyMajorNameContains(updatedMajorName),
                "Major name was not updated"
        );

        softAssert.assertAll();
    }

    @Test(priority = 6)
    public void MJ_06_UserCanDeleteMajorSuccessfully() throws InterruptedException{

        majorPage.goToMajorPage();
        Thread.sleep(2000);
        majorPage.searchMajor(updatedMajorName);
        majorPage.waitForSearchResult();

        majorPage.deleteMajor(updatedMajorCode);
        Thread.sleep(5000);

        majorPage.searchMajor(updatedMajorName);

        softAssert.assertEquals(
                majorPage.getNoDataMessage(),
                "Không có chuyên ngành nào",
                "No data message is incorrect"
        );

        softAssert.assertAll();
    }
}