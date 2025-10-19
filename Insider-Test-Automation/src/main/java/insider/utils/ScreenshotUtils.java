package insider.utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScreenshotUtils {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

    public static void createScreenshotDirectory() {
        File screenshotDir = new File(ConfigReader.getScreenshotPath());
        if (!screenshotDir.exists()) {
            boolean created = screenshotDir.mkdirs();
            if (created) {
                System.out.println("Screenshot dizini oluşturuldu: " + screenshotDir.getAbsolutePath());
            } else {
                System.err.println("Screenshot dizini oluşturulamadı!");
            }
        }
    }

    public static String takeScreenshot(WebDriver driver, String testName) {
        if (driver == null) {
            System.err.println("WebDriver null, screenshot alınamadı!");
            return null;
        }

        try {
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            File srcFile = screenshot.getScreenshotAs(OutputType.FILE);
            
            String timestamp = LocalDateTime.now().format(FORMATTER);
            String fileName = testName + "_" + timestamp + ".png";
            String filePath = ConfigReader.getScreenshotPath() + fileName;
            
            File destFile = new File(filePath);
            FileUtils.copyFile(srcFile, destFile);
            
            System.out.println("Screenshot kaydedildi: " + destFile.getAbsolutePath());
            return destFile.getAbsolutePath();
            
        } catch (IOException e) {
            System.err.println("Screenshot kaydedilemedi: " + e.getMessage());
            return null;
        }
    }

    public static String takeScreenshotWithCustomName(WebDriver driver, String customName) {
        return takeScreenshot(driver, customName);
    }

    public static String getBase64Screenshot(WebDriver driver) {
        if (driver == null) {
            return null;
        }
        
        try {
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            return screenshot.getScreenshotAs(OutputType.BASE64);
        } catch (Exception e) {
            System.err.println("Base64 screenshot alınamadı: " + e.getMessage());
            return null;
        }
    }
}

