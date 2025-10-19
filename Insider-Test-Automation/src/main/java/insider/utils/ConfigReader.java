package insider.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties = new Properties();

    static {
        try {
            InputStream input = ConfigReader.class.getClassLoader()
                    .getResourceAsStream("config.properties");
            if (input != null) {
                properties.load(input);
            } else {
                throw new RuntimeException("config.properties file not found");
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties: " + e.getMessage());
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static String getBaseUrl() {
        return getProperty("base.url");
    }

    public static String getCareersQaUrl() {
        return getProperty("careers.qa.url");
    }

    public static String getJobPosition() {
        return getProperty("job.position");
    }

    public static String getJobDepartment() {
        return getProperty("job.department");
    }

    public static String getJobLocation() {
        return getProperty("job.location");
    }

    public static String getScreenshotPath() {
        return getProperty("screenshot.path");
    }

    public static String getDefaultBrowser() {
        String browser = getProperty("default.browser");
        return browser != null ? browser : "chrome";
    }

    public static int getImplicitWait() {
        String wait = getProperty("implicit.wait");
        return wait != null ? Integer.parseInt(wait) : 10;
    }

    public static int getExplicitWait() {
        String wait = getProperty("explicit.wait");
        return wait != null ? Integer.parseInt(wait) : 20;
    }

    public static int getPageLoadTimeout() {
        String timeout = getProperty("page.load.timeout");
        return timeout != null ? Integer.parseInt(timeout) : 30;
    }
}

