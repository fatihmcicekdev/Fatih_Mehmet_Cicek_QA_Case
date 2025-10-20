package insider.listeners;

import insider.driver.DriverManager;
import insider.reports.ExtentReportManager;
import insider.utils.ScreenshotUtils;
import org.openqa.selenium.WebDriver;
import org.testng.*;
import insider.utils.LoggerUtil;

public class TestListener implements ITestListener, ISuiteListener {

    @Override
    public void onStart(ISuite suite) {
        LoggerUtil.info("\n╔════════════════════════════════════════════════════════════╗");
        LoggerUtil.info("║       INSIDER TEST AUTOMATION - SUITE BAŞLIYOR            ║");
        LoggerUtil.info("║       Suite: " + suite.getName() + "                                  ║");
        LoggerUtil.info("╚════════════════════════════════════════════════════════════╝\n");
        ExtentReportManager.createInstance();
    }

    @Override
    public void onFinish(ISuite suite) {
        LoggerUtil.info("\n╔════════════════════════════════════════════════════════════╗");
        LoggerUtil.info("║       INSIDER TEST AUTOMATION - SUITE TAMAMLANDI         ║");
        LoggerUtil.info("║       Suite: " + suite.getName() + "                                  ║");
        LoggerUtil.info("╚════════════════════════════════════════════════════════════╝\n");
        ExtentReportManager.flush();
    }

    @Override
    public void onStart(ITestContext context) {
        LoggerUtil.info("\n>>> Test Context Başlıyor: " + context.getName() + " <<<");
    }

    @Override
    public void onFinish(ITestContext context) {
        LoggerUtil.info("\n>>> Test Context Bitti: " + context.getName() + " <<<");
        LoggerUtil.info("Toplam Test: " + context.getAllTestMethods().length);
        LoggerUtil.info("Başarılı: " + context.getPassedTests().size());
        LoggerUtil.info("Başarısız: " + context.getFailedTests().size());
        LoggerUtil.info("Atlanan: " + context.getSkippedTests().size());
    }

    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        String description = result.getMethod().getDescription();
        String className = result.getTestClass().getName();
        
        LoggerUtil.info("\n▶▶▶ Test Başladı: " + testName);
        
        ExtentReportManager.createTest(
            testName,
            description != null ? description : "Test açıklaması mevcut değil",
            className
        );
        ExtentReportManager.info("Test başlatıldı: " + testName);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        long duration = result.getEndMillis() - result.getStartMillis();
        
        LoggerUtil.success("✓✓✓ Test BAŞARILI: " + testName + " (Süre: " + duration + "ms)");
        
        ExtentReportManager.pass("Test başarıyla tamamlandı");
        ExtentReportManager.info("Test süresi: " + duration + " ms");
        ExtentReportManager.removeTest();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        Throwable throwable = result.getThrowable();
        long duration = result.getEndMillis() - result.getStartMillis();
        
        LoggerUtil.error("✗✗✗ Test BAŞARISIZ: " + testName + " (Süre: " + duration + "ms)");
        LoggerUtil.error("Hata: " + (throwable != null ? throwable.getMessage() : "Bilinmeyen hata"));
        
        // Extent Report'a hata bilgisi ekle
        ExtentReportManager.fail("Test başarısız oldu");
        ExtentReportManager.fail("Hata mesajı: " + (throwable != null ? throwable.getMessage() : "Bilinmeyen hata"));
        
        if (throwable != null) {
            ExtentReportManager.fail("<details><summary><b><font color=red>Stack Trace</font></b></summary>" +
                    "<pre>" + getStackTrace(throwable) + "</pre></details>");
        }
        
        ExtentReportManager.info("Test süresi: " + duration + " ms");
        
        // Screenshot al
        try {
            WebDriver driver = DriverManager.getDriver();
            if (driver != null) {
                String screenshotPath = ScreenshotUtils.takeScreenshot(driver, testName + "_FAILED");
                if (screenshotPath != null) {
                    ExtentReportManager.addScreenshot(screenshotPath);
                    ExtentReportManager.info("Screenshot eklendi");
                }
            }
        } catch (Exception e) {
            LoggerUtil.error("Screenshot alınamadı: " + e.getMessage());
            ExtentReportManager.warning("Screenshot alınamadı: " + e.getMessage());
        }
        
        ExtentReportManager.removeTest();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        Throwable throwable = result.getThrowable();
        
        LoggerUtil.warn("⊘⊘⊘ Test ATLANDI: " + testName);
        if (throwable != null) {
            LoggerUtil.warn("Sebep: " + throwable.getMessage());
        }
        
        ExtentReportManager.skip("Test atlandı");
        if (throwable != null) {
            ExtentReportManager.skip("Sebep: " + throwable.getMessage());
        }
        
        ExtentReportManager.removeTest();
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        LoggerUtil.warn("Test Kısmi Başarılı: " + testName);
        
        ExtentReportManager.warning("Test kısmi olarak başarılı");
        ExtentReportManager.removeTest();
    }

    private String getStackTrace(Throwable throwable) {
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement element : throwable.getStackTrace()) {
            sb.append(element.toString()).append("\n");
        }
        return sb.toString();
    }
}

