package insider.base;

import insider.driver.DriverManager;
import insider.utils.ConfigReader;
import insider.utils.ScreenshotUtils;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

public class BaseTest {

    protected WebDriver driver;
    protected String browser;

    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() {
        System.out.println("=== Test Suite Başlıyor ===");
        System.out.println("Test Ortamı: " + ConfigReader.getBaseUrl());
        System.out.println("Browser: " + (browser != null ? browser : ConfigReader.getDefaultBrowser()));
        ScreenshotUtils.createScreenshotDirectory();
    }

    @Parameters({"browser"})
    @BeforeClass(alwaysRun = true)
    public void setUp(@Optional String browser) {
        this.browser = (browser != null && !browser.isEmpty()) 
                        ? browser 
                        : ConfigReader.getDefaultBrowser();
        
        System.out.println("\n>>> Test Class Başlıyor <<<");
        System.out.println("Browser: " + this.browser);
        
        driver = DriverManager.initializeDriver(this.browser);
        System.out.println("WebDriver başlatıldı ve yapılandırıldı");
    }

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(java.lang.reflect.Method method) {
        System.out.println("\n▶ Test Başlıyor: " + method.getName());
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        
        if (result.getStatus() == ITestResult.FAILURE) {
            System.out.println("✗ Test Başarısız: " + testName);
            ScreenshotUtils.takeScreenshot(driver, testName);
            System.out.println("Hata: " + result.getThrowable().getMessage());
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            System.out.println("✓ Test Başarılı: " + testName);
        } else if (result.getStatus() == ITestResult.SKIP) {
            System.out.println("⊘ Test Atlandı: " + testName);
        }
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        System.out.println("\n>>> Test Class Bitti <<<");
        DriverManager.quitDriver();
        System.out.println("WebDriver kapatıldı");
    }

    @AfterSuite(alwaysRun = true)
    public void afterSuite() {
        System.out.println("\n=== Test Suite Tamamlandı ===");
    }

    protected WebDriver getDriver() {
        return driver;
    }
}

