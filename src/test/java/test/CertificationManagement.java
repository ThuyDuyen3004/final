package test;

import common.BaseTest;
import models.CertificationManagementItem;
import org.testng.annotations.Test;

import java.util.ArrayList;

public class CertificationManagement extends BaseTest {
    private String certificationName;

    @Test(priority = 1)
    public void CM001_AddCertification() {

        certificationManagementPage.goToCertificationManagementPage();

        String name = "chứng chỉ tiếng pháp";
        String course = "48";

        this.certificationName = name;

        certificationManagementPage.addCertification(name, course);

        certificationManagementPage.refreshPage();
        certificationManagementPage.waitForTableLoaded();
        certificationManagementPage.waitForCertificationAppear(name);

        ArrayList<CertificationManagementItem> actualList =
                certificationManagementPage.getAllCertifications();

        boolean found = actualList.stream().anyMatch(item ->
                item.getCertificationName().equalsIgnoreCase(name.trim())
                        && item.getCourse().equalsIgnoreCase(course.trim())
        );

        softAssert.assertTrue(found, "Add certification failed");
        softAssert.assertAll();
    }


//    @Test(priority = 2, dependsOnMethods = "CM001_AddCertification")
//    public void CM002_EditCertificationName() {
//
//        certificationManagementPage.goToCertificationManagementPage();
//
//        String oldName = certificationName;
//        String newName = "chứng chỉ TOEIC123";
//
//        certificationManagementPage.editCertificationName(oldName, newName);
//
//        certificationManagementPage.refreshPage();
//        certificationManagementPage.waitForTableLoaded();
//
//        certificationName = newName;
//
//        ArrayList<CertificationManagementItem> actualList =
//                certificationManagementPage.getAllCertifications();
//
//        boolean found = actualList.stream().anyMatch(item ->
//                item.getCertificationName().equalsIgnoreCase(newName.trim())
//        );
//
//        softAssert.assertTrue(found, "Edit name failed");
//        softAssert.assertAll();
//    }
//
//
//    @Test(priority = 3, dependsOnMethods = "CM002_EditCertificationName")
//    public void CM003_EditCertificationCourse() {
//
//        certificationManagementPage.goToCertificationManagementPage();
//
//        String name = certificationName; // đã là NEW NAME từ TC2
//        String newCourse = "49";
//
//        certificationManagementPage.editCertificationCourse(name, newCourse);
//
//        certificationManagementPage.refreshPage();
//        certificationManagementPage.waitForTableLoaded();
//
//        ArrayList<CertificationManagementItem> actualList =
//                certificationManagementPage.getAllCertifications();
//
//        boolean found = actualList.stream().anyMatch(item ->
//                item.getCertificationName().equalsIgnoreCase(name.trim())
//                        && item.getCourse().equalsIgnoreCase(newCourse.trim())
//        );
//
//        softAssert.assertTrue(found, "Edit course failed");
//        softAssert.assertAll();
//    }
    @Test(priority = 4)
    public void CM004_VerifyUserCanSearchCertification() {


        certificationManagementPage.goToCertificationManagementPage();

        certificationManagementPage.waitForTableLoaded();

        // 🔥 lấy random certification
        CertificationManagementItem item =
                certificationManagementPage.getRandomCertification();

        String name = item.getCertificationName();

        certificationManagementPage.searchCertification(name);

        // 🔥 wait table update sau search
        certificationManagementPage.waitForTableLoaded();

        softAssert.assertTrue(
                certificationManagementPage.verifySearchResultContainsName(name),
                "Search result does not contain certification name"
        );

        softAssert.assertAll();
    }
    @Test(priority = 5)
    public void CM005_VerifySearchCertification_NoDataDisplayed() {


        certificationManagementPage.goToCertificationManagementPage();

        certificationManagementPage.waitForTableLoaded();

        String invalidKeyword =
                certificationManagementPage.generateNonExistingKeyword();

        certificationManagementPage.searchCertification(invalidKeyword);

        softAssert.assertTrue(
                certificationManagementPage.isCertificationTableEmpty(),
                "Certification table should be empty but still has data"
        );

        softAssert.assertAll();
    }
}