package insider.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import insider.constants.FrameworkConstants;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import insider.utils.LoggerUtil;

public class ExtentReportManager {

    private static ExtentReports extent;
    private static final ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

    private ExtentReportManager() {
        // Private constructor
    }

    public static ExtentReports createInstance() {
        if (extent == null) {
            String timestamp = LocalDateTime.now().format(FORMATTER);
            String reportPath = FrameworkConstants.EXTENT_REPORT_PATH + 
                                FrameworkConstants.EXTENT_REPORT_NAME + "_" + timestamp + ".html";
            
            // Dizin yoksa oluştur
            File reportDir = new File(FrameworkConstants.EXTENT_REPORT_PATH);
            if (!reportDir.exists()) {
                reportDir.mkdirs();
            }

            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
            sparkReporter.config().setTheme(Theme.STANDARD);
            sparkReporter.config().setDocumentTitle(FrameworkConstants.EXTENT_DOCUMENT_TITLE);
            sparkReporter.config().setReportName(FrameworkConstants.EXTENT_REPORT_TITLE);
            sparkReporter.config().setEncoding("UTF-8");
            sparkReporter.config().setTimeStampFormat("dd-MM-yyyy HH:mm:ss");

            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);
            
            // System bilgileri
            extent.setSystemInfo("Tester", FrameworkConstants.TESTER_NAME);
            extent.setSystemInfo("Project", FrameworkConstants.PROJECT_NAME);
            extent.setSystemInfo("Environment", FrameworkConstants.ENVIRONMENT);
            extent.setSystemInfo("OS", System.getProperty("os.name"));
            extent.setSystemInfo("Java Version", System.getProperty("java.version"));
            extent.setSystemInfo("User", System.getProperty("user.name"));

            LoggerUtil.success("Extent Report oluşturuldu: " + reportPath);
        }
        return extent;
    }

    public static ExtentReports getInstance() {
        if (extent == null) {
            createInstance();
        }
        return extent;
    }

    public static synchronized ExtentTest createTest(String testName) {
        ExtentTest test = getInstance().createTest(testName);
        extentTest.set(test);
        return test;
    }

    public static synchronized ExtentTest createTest(String testName, String description, String category) {
        ExtentTest test = getInstance().createTest(testName, description);
        test.assignCategory(category);
        extentTest.set(test);
        return test;
    }

    public static ExtentTest getTest() {
        return extentTest.get();
    }


    public static void removeTest() {
        extentTest.remove();
    }


    public static void log(Status status, String message) {
        if (getTest() != null) {
            getTest().log(status, message);
        }
    }


    public static void info(String message) {
        log(Status.INFO, message);
    }

 
    public static void pass(String message) {
        log(Status.PASS, message);
    }

    public static void fail(String message) {
        log(Status.FAIL, message);
    }

    public static void skip(String message) {
        log(Status.SKIP, message);
    }

    public static void warning(String message) {
        log(Status.WARNING, message);
    }

    public static void addScreenshot(String screenshotPath) {
        if (getTest() != null && screenshotPath != null) {
            try {
                getTest().addScreenCaptureFromPath(screenshotPath);
            } catch (Exception e) {
                LoggerUtil.error("Screenshot eklenemedi: " + e.getMessage());
            }
        }
    }

    public static synchronized void flush() {
        if (extent != null) {
            extent.flush();
            LoggerUtil.info("Extent Report kaydedildi");
        }
    }
}

