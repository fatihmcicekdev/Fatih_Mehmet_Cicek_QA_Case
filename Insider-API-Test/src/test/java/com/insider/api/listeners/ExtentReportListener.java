package com.insider.api.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentReportListener implements ITestListener {
    
    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    
    @Override
    public void onStart(ITestContext context) {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String reportPath = "target/extent-reports/API-Test-Report_" + timestamp + ".html";
        
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
        
        // Spark Reporter Configuration
        sparkReporter.config().setDocumentTitle("Insider API Test Report");
        sparkReporter.config().setReportName("API Test Automation Results");
        sparkReporter.config().setTheme(Theme.DARK);
        sparkReporter.config().setTimeStampFormat("dd/MM/yyyy HH:mm:ss");
        sparkReporter.config().setEncoding("UTF-8");
        
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        
        // System Information
        extent.setSystemInfo("Environment", "Test");
        extent.setSystemInfo("Base URL", "https://petstore.swagger.io/v2");
        extent.setSystemInfo("Framework", "Rest-Assured + TestNG");
        extent.setSystemInfo("Java Version", System.getProperty("java.version"));
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("User", System.getProperty("user.name"));
        extent.setSystemInfo("Tested By", "Insider QA Team");
    }
    
    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest extentTest = extent.createTest(
            result.getMethod().getMethodName(),
            result.getMethod().getDescription()
        );
        
        // Add categories/tags
        extentTest.assignCategory(result.getTestClass().getRealClass().getSimpleName());
        extentTest.assignAuthor("Insider QA");
        
        test.set(extentTest);
    }
    
    @Override
    public void onTestSuccess(ITestResult result) {
        test.get().log(Status.PASS, "✅ Test PASSED: " + result.getMethod().getMethodName());
        test.get().pass("Test completed successfully");
    }
    
    @Override
    public void onTestFailure(ITestResult result) {
        test.get().log(Status.FAIL, "❌ Test FAILED: " + result.getMethod().getMethodName());
        test.get().fail(result.getThrowable());
    }
    
    @Override
    public void onTestSkipped(ITestResult result) {
        test.get().log(Status.SKIP, "⏭️ Test SKIPPED: " + result.getMethod().getMethodName());
        test.get().skip(result.getThrowable());
    }
    
    @Override
    public void onFinish(ITestContext context) {
        if (extent != null) {
            extent.flush();
        }
        
        System.out.println("\n" + "=".repeat(60));
        System.out.println("📊 EXTENT REPORT GENERATED");
        System.out.println("=".repeat(60));
        System.out.println("📁 Location: target/extent-reports/");
        System.out.println("🌐 Open the HTML file in your browser to view the report");
        System.out.println("=".repeat(60) + "\n");
    }
}

