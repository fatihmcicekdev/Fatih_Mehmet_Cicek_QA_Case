package com.insider.api.tests;

import com.insider.api.constants.StatusCodes;
import com.insider.api.services.StoreService;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Map;

import static org.testng.Assert.*;

public class StoreInventoryTest {
    
    private static final Logger logger = LoggerFactory.getLogger(StoreInventoryTest.class);
    private StoreService storeService;
    
    @BeforeClass
    public void setup() {
        storeService = new StoreService();
    }
    
    @Test(description = "Get store inventory and verify basic response structure")
    public void testGetStoreInventory_BasicCheck() {
        logger.info("▶ TEST: Get Store Inventory");
        
        Response response = storeService.getInventory();
        
        int statusCode = response.getStatusCode();
        assertEquals(statusCode, StatusCodes.OK, "Status code should be 200 OK");
        
        Map<String, Object> inventory = response.jsonPath().getMap("$");
        
        assertNotNull(inventory, "Inventory map should not be null");
        assertFalse(inventory.isEmpty(), "Inventory map should not be empty");
        assertTrue(inventory.size() > 0, "Inventory should contain at least one status");
        
        // Verify all values are numeric
        for (Map.Entry<String, Object> entry : inventory.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            
            assertNotNull(value, "Value for key '" + key + "' should not be null");
            
            if (value instanceof Integer) {
                logger.debug("  • {} = {}", key, value);
            } else if (value instanceof Number) {
                logger.debug("  • {} = {}", key, ((Number) value).intValue());
            } else if (value instanceof String) {
                try {
                    Integer.parseInt((String) value);
                    logger.debug("  • {} = {}", key, value);
                } catch (NumberFormatException e) {
                    fail("Value for key '" + key + "' is not a valid number: " + value);
                }
            } else {
                fail("Value for key '" + key + "' is not a numeric type");
            }
        }
        
        logger.info("✅ PASS | Inventory verified: {} statuses", inventory.size());
    }
}
