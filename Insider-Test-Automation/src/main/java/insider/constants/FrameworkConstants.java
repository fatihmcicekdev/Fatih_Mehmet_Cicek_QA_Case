package insider.constants;

import insider.utils.ConfigReader;

public final class FrameworkConstants {
    
    private FrameworkConstants() {
        // Utility class, instantiation prevented
    }

    // Paths
    public static final String CONFIG_FILE_PATH = "src/main/resources/config.properties";
    public static final String SCREENSHOT_PATH = ConfigReader.getScreenshotPath();
    public static final String EXTENT_REPORT_PATH = "test-output/extent-reports/";
    public static final String EXTENT_CONFIG_PATH = "src/main/resources/extent-config.xml";
    
    // Report Names
    public static final String EXTENT_REPORT_NAME = "InsiderTestReport";
    public static final String EXTENT_REPORT_TITLE = "Insider Test Automation Report";
    public static final String EXTENT_DOCUMENT_TITLE = "Insider Automation Results";
    
    // Test Info
    public static final String TESTER_NAME = "Fatih Mehmet Cicek";
    public static final String PROJECT_NAME = "Insider UI Test Automation";
    public static final String ENVIRONMENT = "Production";
    
    // Wait Messages
    public static final String ELEMENT_NOT_FOUND = "Element bulunamadı";
    public static final String ELEMENT_NOT_CLICKABLE = "Element tıklanabilir değil";
    public static final String PAGE_NOT_LOADED = "Sayfa yüklenemedi";
}

