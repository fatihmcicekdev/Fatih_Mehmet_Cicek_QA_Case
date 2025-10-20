package com.insider.api.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    
    private static final Logger logger = LoggerFactory.getLogger(ConfigReader.class);
    private static Properties properties;
    private static final String DEFAULT_ENV = "test";
    
    static {
        loadProperties();
    }
    
    private static void loadProperties() {
        String env = System.getProperty("env", DEFAULT_ENV);
        String configFile = String.format("config/%s.properties", env);
        
        properties = new Properties();
        
        try (InputStream input = ConfigReader.class.getClassLoader().getResourceAsStream(configFile)) {
            if (input == null) {
                logger.error("Unable to find configuration file: {}", configFile);
                throw new RuntimeException("Configuration file not found: " + configFile);
            }
            
            properties.load(input);
            logger.debug("Configuration loaded from: {}", configFile);
            
        } catch (IOException e) {
            logger.error("Error loading configuration file: {}", configFile, e);
            throw new RuntimeException("Failed to load configuration", e);
        }
    }
    
    public static String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            logger.warn("Property not found: {}", key);
        }
        return value;
    }
    
    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
    
    public static String getBaseUrl() {
        return getProperty("base.url");
    }

    public static boolean isRequestLoggingEnabled() {
        return Boolean.parseBoolean(getProperty("log.requests", "true"));
    }
    
    public static boolean isResponseLoggingEnabled() {
        return Boolean.parseBoolean(getProperty("log.responses", "true"));
    }
}

