package tests;

import insider.base.BaseTest;
import insider.pages.CareersPage;
import insider.pages.HomePage;
import insider.pages.QaPage;
import insider.utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.*;

public class InsiderCareersTest extends BaseTest {

    private HomePage homePage;
    private CareersPage careersPage;
    private QaPage qaPage;

    @Test(priority = 1, description = "Verify home page is accessible and loads correctly")
    public void testHomePage() {
        homePage = new HomePage(driver);
        homePage.navigateToHomePage();
        Assert.assertTrue(homePage.isHomePageLoaded(), 
            "Home page URL does not match expected URL");
    }

    @Test(priority = 2, description = "Verify careers page has all required blocks")
    public void testCareersPage() {
        homePage = new HomePage(driver);
        homePage.navigateToHomePage();
        
        careersPage = homePage.navigateToCareers();
        Assert.assertTrue(careersPage.areAllBlocksDisplayed(), 
            "One or more career page blocks are not displayed");
    }

    @Test(priority = 3, description = "Navigate to QA careers page")
    public void testQaPage() {
        qaPage = new QaPage(driver);
        qaPage.navigateToQaPage();
        Assert.assertEquals(driver.getCurrentUrl(), ConfigReader.getCareersQaUrl(), 
            "QA page URL does not match expected URL");
    }

}
