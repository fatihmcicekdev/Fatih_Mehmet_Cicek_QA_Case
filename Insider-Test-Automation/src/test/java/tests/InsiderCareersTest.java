package tests;

import insider.base.BaseTest;
import insider.pages.CareersPage;
import insider.pages.HomePage;
import insider.pages.OpenPositionsPage;
import insider.utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.*;

public class InsiderCareersTest extends BaseTest {

    private HomePage homePage;
    private CareersPage careersPage;
    private OpenPositionsPage openPositionsPage;

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
        
        // 1. URL kontrolü
        Assert.assertTrue(careersPage.isCareersPageUrl(), 
            "Careers page URL does not match expected: " + ConfigReader.getCareersUrl());
        
        // 2. Title kontrolü
        Assert.assertEquals(careersPage.getCareersPageTitle(), careersPage.getExpectedCareersPageTitle(),
            "Careers page title does not match expected title");
        
        // 3. Dream job link görünürlük kontrolü (0. index)
        Assert.assertTrue(careersPage.isDreamJobLinkVisible(),
            "Dream job link (index 0) is not visible");
        
        // 4. Blokların görünürlük kontrolü
        Assert.assertTrue(careersPage.areAllBlocksDisplayed(), 
            "One or more career page blocks are not displayed");
    }


    @Test(priority = 3, description = "Navigate to Open Positions and filter by location")
    public void testOpenPositionsAndLocationFilter() {
        homePage = new HomePage(driver);
        homePage.navigateToHomePage();
        
        // Careers sayfasına git
        careersPage = homePage.navigateToCareers();
        
        // Dream job linkine tıkla ve Open Positions sayfasına git
        openPositionsPage = careersPage.clickDreamJobLink();
        
        // 1. Open Positions sayfasına gittiğini kontrol et
        Assert.assertTrue(openPositionsPage.isOpenPositionsPageLoaded(),
            "Open Positions page URL does not match expected: " + ConfigReader.getOpenPositionsUrl());
        
        // 2. Total Result yüklenene kadar bekle (NaN yerine gerçek sayı gelsin)
        Assert.assertTrue(openPositionsPage.waitForTotalResultsToLoad(),
            "Total Results did not load properly");
        
        // 3. Location filter dropdown'ına tıkla
        openPositionsPage.clickLocationFilter();
        
        // 4. Dropdown'ın açıldığını kontrol et
        Assert.assertTrue(openPositionsPage.isLocationDropdownVisible(),
            "Location dropdown is not visible");
        
        // 5. Istanbul, Turkiye'yi seç
        openPositionsPage.selectIstanbulLocation();
        
        // 6. Seçimin doğru yapıldığını kontrol et
        Assert.assertTrue(openPositionsPage.isIstanbulLocationSelected(),
            ConfigReader.getJobLocation() + " location is not selected");
        
        // 7. Department filter dropdown'ına tıkla
        openPositionsPage.clickDepartmentFilter();
        
        // 8. Dropdown'ın açıldığını kontrol et
        Assert.assertTrue(openPositionsPage.isLocationDropdownVisible(),
            "Department dropdown is not visible");
        
        // 9. Quality Assurance'ı seç
        openPositionsPage.selectQualityAssuranceDepartment();
        
        // 10. Seçimin doğru yapıldığını kontrol et
        Assert.assertTrue(openPositionsPage.isQualityAssuranceDepartmentSelected(),
            ConfigReader.getJobDepartment() + " department is not selected");

        // 12. Tüm job kartlarının filtrelere uygun olduğunu kontrol et
        Assert.assertTrue(openPositionsPage.verifyAllJobsMatchFilters(),
            "Not all jobs match the selected filters (" + ConfigReader.getJobDepartment() + " + " + ConfigReader.getJobLocation() + ")");
        
        // 13. İlk job kartına hover yap ve View Role butonuna tıkla
        openPositionsPage.clickFirstViewRoleButton();
        
        // 14. Yeni sekmede Lever.co job sayfasına yönlendirildiğini kontrol et
        Assert.assertTrue(openPositionsPage.switchToLeverJobPage(),
            "Did not redirect to Lever.co job page");
    }

}
