package insider.base;

import insider.driver.DriverManager;
import insider.utils.ConfigReader;
import insider.utils.LoggerUtil;
import insider.utils.ScreenshotUtils;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

public class BaseTest {

    protected WebDriver driver;
    protected String browser;

    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() {
        LoggerUtil.separator();
        LoggerUtil.info("TEST SUITE BAŞLIYOR");
        LoggerUtil.info("Test Ortamı: " + ConfigReader.getBaseUrl());
        LoggerUtil.info("Browser: " + (browser != null ? browser : ConfigReader.getDefaultBrowser()));
        LoggerUtil.separator();
        ScreenshotUtils.createScreenshotDirectory();
    }

    @Parameters({"browser"})
    @BeforeClass(alwaysRun = true)
    public void setUp(@Optional String browser) {
        this.browser = (browser != null && !browser.isEmpty()) 
                        ? browser 
                        : ConfigReader.getDefaultBrowser();
        
        LoggerUtil.info("TEST CLASS BAŞLIYOR");
        LoggerUtil.info("Browser: " + this.browser);
        
        driver = DriverManager.initializeDriver(this.browser);
        LoggerUtil.success("WebDriver başlatıldı ve yapılandırıldı");
    }

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(java.lang.reflect.Method method) {
        LoggerUtil.testStart(method.getName());
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        boolean passed = result.getStatus() == ITestResult.SUCCESS;
        
        if (result.getStatus() == ITestResult.FAILURE) {
            LoggerUtil.fail("Test Başarısız: " + testName);
            ScreenshotUtils.takeScreenshot(driver, testName);
            LoggerUtil.error("Hata: " + result.getThrowable().getMessage());
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            LoggerUtil.success("Test Başarılı: " + testName);
        } else if (result.getStatus() == ITestResult.SKIP) {
            LoggerUtil.warn("Test Atlandı: " + testName);
        }
        
        LoggerUtil.testEnd(testName, passed);
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        LoggerUtil.info("TEST CLASS BİTTİ");
        DriverManager.quitDriver();
        LoggerUtil.success("WebDriver kapatıldı");
    }

    @AfterSuite(alwaysRun = true)
    public void afterSuite() {
        LoggerUtil.separator();
        LoggerUtil.info("TEST SUITE TAMAMLANDI");
        LoggerUtil.separator();
    }

    protected WebDriver getDriver() {
        return driver;
    }
}

