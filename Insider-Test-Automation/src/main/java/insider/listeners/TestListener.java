package insider.listeners;

import insider.driver.DriverManager;
import insider.reports.ExtentReportManager;
import insider.utils.ScreenshotUtils;
import org.openqa.selenium.WebDriver;
import org.testng.*;

public class TestListener implements ITestListener, ISuiteListener {

    @Override
    public void onStart(ISuite suite) {
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║       INSIDER TEST AUTOMATION - SUITE BAŞLIYOR            ║");
        System.out.println("║       Suite: " + suite.getName() + "                                  ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");
        ExtentReportManager.createInstance();
    }

    @Override
    public void onFinish(ISuite suite) {
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║       INSIDER TEST AUTOMATION - SUITE TAMAMLANDI         ║");
        System.out.println("║       Suite: " + suite.getName() + "                                  ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");
        ExtentReportManager.flush();
    }

    @Override
    public void onStart(ITestContext context) {
        System.out.println("\n>>> Test Context Başlıyor: " + context.getName() + " <<<");
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("\n>>> Test Context Bitti: " + context.getName() + " <<<");
        System.out.println("Toplam Test: " + context.getAllTestMethods().length);
        System.out.println("Başarılı: " + context.getPassedTests().size());
        System.out.println("Başarısız: " + context.getFailedTests().size());
        System.out.println("Atlanan: " + context.getSkippedTests().size());
    }

    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        String description = result.getMethod().getDescription();
        String className = result.getTestClass().getName();
        
        System.out.println("\n▶▶▶ Test Başladı: " + testName);
        
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
        
        System.out.println("✓✓✓ Test BAŞARILI: " + testName + " (Süre: " + duration + "ms)");
        
        ExtentReportManager.pass("Test başarıyla tamamlandı");
        ExtentReportManager.info("Test süresi: " + duration + " ms");
        ExtentReportManager.removeTest();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        Throwable throwable = result.getThrowable();
        long duration = result.getEndMillis() - result.getStartMillis();
        
        System.err.println("✗✗✗ Test BAŞARISIZ: " + testName + " (Süre: " + duration + "ms)");
        System.err.println("Hata: " + (throwable != null ? throwable.getMessage() : "Bilinmeyen hata"));
        
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
            System.err.println("Screenshot alınamadı: " + e.getMessage());
            ExtentReportManager.warning("Screenshot alınamadı: " + e.getMessage());
        }
        
        ExtentReportManager.removeTest();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        Throwable throwable = result.getThrowable();
        
        System.out.println("⊘⊘⊘ Test ATLANDI: " + testName);
        if (throwable != null) {
            System.out.println("Sebep: " + throwable.getMessage());
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
        System.out.println("⚠ Test Kısmi Başarılı: " + testName);
        
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

