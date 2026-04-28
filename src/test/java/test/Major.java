package test;

import common.BaseTest;
import jdk.jfr.Description;
import org.testng.annotations.Test;

public class Major extends BaseTest {
    private String majorCode;
    private String majorName;
    private String updatedMajorCode;
    private String updatedMajorName;
    @Test
    public void MJ_01_UserCanSearchByMajorName() {

        majorPage.goToMajorPage();

        String majorName = majorPage.getRandomMajorName();

        majorPage.searchMajor(majorName);

        softAssert.assertTrue(
                majorPage.verifyMajorSearchResultContainsKeyword(majorName),
                "Search result does not contain major name"
        );

        softAssert.assertAll();
    }
    @Test
    public void CLS_02_VerifySearchWithNonExistingKeywordReturnsEmpty() throws InterruptedException {

        majorPage.goToMajorPage();

        String keyword = "zzz_not_exist_" + System.currentTimeMillis();
        Thread.sleep(1000);
        majorPage.searchMajor(keyword);

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
    @Test
    public void LOG01_UserCanAddMajorSuccessfully() throws InterruptedException{

        majorPage.goToMajorPage();

        majorCode = majorPage.generateUniqueMajorCode();
        majorName = majorPage.generateMajorName();

        majorPage.addMajor(majorCode, majorName);
        Thread.sleep(1000);
        softAssert.assertTrue(
                majorPage.verifyMajorContainsCode(majorCode),
                "Major is not displayed after adding"
        );

        softAssert.assertAll();
    }
    @Test(dependsOnMethods = "LOG01_UserCanAddMajorSuccessfully")
    public void MJ_02_UserCanEditMajorCode() throws InterruptedException{

        majorPage.goToMajorPage();

        updatedMajorCode = majorPage.generateUniqueMajorCode();

        majorPage.openEditMajorForm(majorCode);
        majorPage.editMajorCode(updatedMajorCode);
        Thread.sleep(1000);
        softAssert.assertTrue(
                majorPage.verifyMajorContainsCode(updatedMajorCode),
                "Major code was not updated"
        );

        softAssert.assertAll();
    }
    @Test(dependsOnMethods = "MJ_02_UserCanEditMajorCode")
    public void MJ_03_UserCanEditMajorName() throws InterruptedException{

        majorPage.goToMajorPage();

        updatedMajorName = "Updated_" + majorPage.generateMajorName();

        majorPage.openEditMajorForm(updatedMajorCode);
        majorPage.editMajorName(updatedMajorName);
        Thread.sleep(1000);


        softAssert.assertTrue(
                majorPage.verifyMajorNameContains(updatedMajorName),
                "Major name was not updated"
        );

        softAssert.assertAll();
    }
//    @Description("User can delete major successfully")
//    @Test(dependsOnMethods = "LOG01_UserCanAddMajorSuccessfully")
//    public void MJ_04_UserCanDeleteMajorSuccessfully() throws InterruptedException {
//
//        majorPage.goToMajorPage();
//
//        majorPage.deleteMajor(updatedMajorCode);
//        Thread.sleep(1000);
//        majorPage.searchMajor(updatedMajorCode);
//
//        softAssert.assertTrue(
//                majorPage.isNoDataDisplayed() ||
//                        !majorPage.verifyMajorContainsCode(majorCode),
//                "Major is still displayed after deletion"
//        );
//
//        softAssert.assertAll();
//    }
}
