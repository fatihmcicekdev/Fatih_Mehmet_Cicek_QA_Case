package insider.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerUtil {
    
    private static final Logger logger = LogManager.getLogger(LoggerUtil.class);
    
    // ANSI Color Codes for Console
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_CYAN = "\u001B[36m";
    
    // Success Log (Green)
    public static void success(String message) {
        logger.info(ANSI_GREEN + "✓ " + message + ANSI_RESET);
    }
    
    // Info Log (Blue)
    public static void info(String message) {
        logger.info(ANSI_BLUE + "→ " + message + ANSI_RESET);
    }
    
    // Warning Log (Yellow)
    public static void warn(String message) {
        logger.warn(ANSI_YELLOW + "⚠ " + message + ANSI_RESET);
    }
    
    // Error/Fail Log (Red)
    public static void error(String message) {
        logger.error(ANSI_RED + "✗ " + message + ANSI_RESET);
    }
    
    public static void fail(String message) {
        logger.error(ANSI_RED + "✗ " + message + ANSI_RESET);
    }
    
    // Debug Log (Cyan)
    public static void debug(String message) {
        logger.debug(ANSI_CYAN + "⚙ " + message + ANSI_RESET);
    }
    
    // Test Başlangıç/Bitiş
    public static void testStart(String testName) {
        logger.info("\n" + "=".repeat(80));
        logger.info(ANSI_CYAN + "▶ TEST BAŞLIYOR: " + testName + ANSI_RESET);
        logger.info("=".repeat(80));
    }
    
    public static void testEnd(String testName, boolean passed) {
        logger.info("=".repeat(80));
        if (passed) {
            logger.info(ANSI_GREEN + "✓ TEST BAŞARILI: " + testName + ANSI_RESET);
        } else {
            logger.error(ANSI_RED + "✗ TEST BAŞARISIZ: " + testName + ANSI_RESET);
        }
        logger.info("=".repeat(80) + "\n");
    }
    
    // Exception Logging
    public static void exception(String message, Throwable throwable) {
        logger.error(ANSI_RED + "✗ HATA: " + message + ANSI_RESET, throwable);
    }
    
    // Step Logging (Test adımları için)
    public static void step(String stepDescription) {
        logger.info(ANSI_CYAN + "→ ADIM: " + stepDescription + ANSI_RESET);
    }
    
    // Separator
    public static void separator() {
        logger.info("-".repeat(80));
    }
}

