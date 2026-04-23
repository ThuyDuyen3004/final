package test;

import common.BaseTest;
import models.Setting.CertificationManagementItem;
import org.testng.annotations.Test;
import utils.Constants;

import java.util.ArrayList;

public class CertificationManagement extends BaseTest {

    private String certificationName;

    @Test(priority = 1)
    public void CM001_AddCertification() {

        loginPage.login(Constants.EMAIL, Constants.PASSWORD);

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
                        && item.getCourse().contains(course)
        );

        softAssert.assertTrue(found, "Add certification failed");
        softAssert.assertAll();
    }

    @Test(priority = 2, dependsOnMethods = "CM001_AddCertification")
    public void CM002_EditCertificationName() {

        loginPage.login(Constants.EMAIL, Constants.PASSWORD);

        certificationManagementPage.goToCertificationManagementPage();

        String oldName = certificationName;
        String newName = "chứng chỉ TOEIC";

        certificationManagementPage.editCertificationName(oldName, newName);

        certificationManagementPage.refreshPage();
        certificationManagementPage.waitForTableLoaded();
        certificationManagementPage.waitForCertificationAppear(newName);

        certificationName = newName;

        ArrayList<CertificationManagementItem> actualList =
                certificationManagementPage.getAllCertifications();

        boolean found = actualList.stream().anyMatch(item ->
                item.getCertificationName().equalsIgnoreCase(newName.trim())
        );

        softAssert.assertTrue(found, "Edit name failed");
        softAssert.assertAll();
    }

    @Test(priority = 3, dependsOnMethods = "CM002_EditCertificationName")
    public void CM003_EditCertificationCourse() {

        loginPage.login(Constants.EMAIL, Constants.PASSWORD);

        certificationManagementPage.goToCertificationManagementPage();

        String name = certificationName;

        String oldCourse = "48";
        String newCourse = "46";

        certificationManagementPage.editCertificationCourse(name, oldCourse, newCourse);

        certificationManagementPage.refreshPage();
        certificationManagementPage.waitForTableLoaded();
        certificationManagementPage.waitForCertificationAppear(name);

        ArrayList<CertificationManagementItem> actualList =
                certificationManagementPage.getAllCertifications();

        boolean found = actualList.stream().anyMatch(item ->
                item.getCertificationName().equalsIgnoreCase(name.trim())
                        && item.getCourse().contains(newCourse)
        );

        softAssert.assertTrue(found, "Edit course failed");
        softAssert.assertAll();
    }
    @Test(priority = 4)
    public void CM004_VerifyUserCanSearchCertification() {

        loginPage.login(Constants.EMAIL, Constants.PASSWORD);

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

        loginPage.login(Constants.EMAIL, Constants.PASSWORD);

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