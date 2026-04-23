//package test;
//
//import common.BaseTest;
//import org.testng.annotations.Test;
//import utils.Constants;
//
//public class Score extends BaseTest {
//    @Test(priority = 1)
//    public void SCM002_VerifyAddSingleSubjectScoreDisplayedCorrectly() throws InterruptedException {
//
//        Thread.sleep(1000);
//        loginPage.login(Constants.EMAIL, Constants.PASSWORD);
//
//        Thread.sleep(2000);
//        scorePage.goToScorePage();
//
//        String mssv = "SV" + System.currentTimeMillis();
//        String subject = "Cơ sở dữ liệu (3)";
//        String score = "8.5";
//        String lop = "48K21.1";
//
//        scorePage.openAddScoreForm();
//
//        scorePage.selectStudent(mssv);
//        scorePage.selectSubject(subject);
//        scorePage.enterScore(score);
//        scorePage.clickSave();
//
//        Thread.sleep(2000);
//
//        // 👉 filter + scroll
//        scorePage.filterByClass(lop);
//        scorePage.scrollToStudent(mssv);
//
//        // 👉 lấy điểm từ table theo MSSV + subject
//        String actualScore = scorePage.getScoreByMSSVAndSubject(mssv, subject);
//
//        // 👉 verify
//        softAssert.assertEquals(
//                actualScore,
//                score,
//                "Score is not displayed correctly in table"
//        );
//
//        softAssert.assertAll();
//    }
//}
