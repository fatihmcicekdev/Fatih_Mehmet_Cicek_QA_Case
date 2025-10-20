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
                LoggerUtil.success("Screenshot dizini oluşturuldu: " + screenshotDir.getAbsolutePath());
            } else {
                LoggerUtil.error("Screenshot dizini oluşturulamadı!");
            }
        }
    }

    public static String takeScreenshot(WebDriver driver, String testName) {
        if (driver == null) {
            LoggerUtil.error("WebDriver null, screenshot alınamadı!");
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
            
            LoggerUtil.success("Screenshot kaydedildi: " + destFile.getAbsolutePath());
            return destFile.getAbsolutePath();
            
        } catch (IOException e) {
            LoggerUtil.error("Screenshot kaydedilemedi: " + e.getMessage());
            return null;
        }
    }
}

