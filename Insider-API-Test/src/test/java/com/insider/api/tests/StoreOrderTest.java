package com.insider.api.tests;

import com.insider.api.constants.StatusCodes;
import com.insider.api.models.request.OrderRequest;
import com.insider.api.models.response.ApiResponse;
import com.insider.api.models.response.OrderResponse;
import com.insider.api.services.StoreService;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class StoreOrderTest {
    
    private static final Logger logger = LoggerFactory.getLogger(StoreOrderTest.class);
    private StoreService storeService;
    private OrderRequest testOrderRequest;
    
    @BeforeClass
    public void setup() {
        storeService = new StoreService();
        
        testOrderRequest = OrderRequest.builder()
                .id(1L)
                .petId(1L)
                .quantity(100)
                .shipDate("2025-10-20T10:16:36.894Z")
                .status("placed")
                .complete(true)
                .build();
    }
    
    @Test(priority = 1, description = "Create store order and verify response")
    public void testCreateStoreOrder_Success() {
        logger.info("▶ TEST: Create Store Order (POST)");
        
        Response response = storeService.createOrder(testOrderRequest);
        
        int statusCode = response.getStatusCode();
        assertEquals(statusCode, StatusCodes.OK, "Status code should be 200 OK");
        
        OrderResponse orderResponse = response.as(OrderResponse.class);
        
        assertNotNull(orderResponse.getId(), "Order ID should not be null");
        assertEquals(orderResponse.getId(), testOrderRequest.getId(), "Order ID should match");
        
        assertNotNull(orderResponse.getPetId(), "Pet ID should not be null");
        assertEquals(orderResponse.getPetId(), testOrderRequest.getPetId(), "Pet ID should match");
        
        assertNotNull(orderResponse.getQuantity(), "Quantity should not be null");
        assertEquals(orderResponse.getQuantity(), testOrderRequest.getQuantity(), "Quantity should match");
        
        assertNotNull(orderResponse.getShipDate(), "Ship date should not be null");
        assertTrue(orderResponse.getShipDate().contains("2025-10-20"), "Ship date should contain correct date");
        
        assertNotNull(orderResponse.getStatus(), "Status should not be null");
        assertEquals(orderResponse.getStatus(), testOrderRequest.getStatus(), "Status should match");
        
        assertNotNull(orderResponse.getComplete(), "Complete flag should not be null");
        assertEquals(orderResponse.getComplete(), testOrderRequest.getComplete(), "Complete flag should match");
        
        logger.info("✅ PASS | Order created → ID={}, PetID={}, Qty={}", 
                orderResponse.getId(), orderResponse.getPetId(), orderResponse.getQuantity());
    }
    
    @Test(priority = 2, description = "Get store order by ID and verify response")
    public void testGetStoreOrderById_Success() {
        logger.info("▶ TEST: Get Store Order by ID (GET)");
        
        Long orderId = 10L;
        Response response = storeService.getOrderById(orderId);
        
        int statusCode = response.getStatusCode();
        assertEquals(statusCode, StatusCodes.OK, "Status code should be 200 OK");
        
        OrderResponse orderResponse = response.as(OrderResponse.class);
        
        assertNotNull(orderResponse.getId(), "Order ID should not be null");
        assertEquals(orderResponse.getId(), orderId, "Order ID should match requested ID");
        
        assertNotNull(orderResponse.getPetId(), "Pet ID should not be null");
        assertNotNull(orderResponse.getQuantity(), "Quantity should not be null");
        assertNotNull(orderResponse.getShipDate(), "Ship date should not be null");
        
        assertNotNull(orderResponse.getStatus(), "Status should not be null");
        assertEquals(orderResponse.getStatus(), "placed", "Status should be 'placed'");
        
        assertNotNull(orderResponse.getComplete(), "Complete flag should not be null");
        assertTrue(orderResponse.getComplete(), "Complete flag should be true");
        
        logger.info("✅ PASS | Order retrieved → ID={}, Status={}", 
                orderResponse.getId(), orderResponse.getStatus());
    }
    
    @Test(priority = 3, description = "Delete store order by ID and verify response")
    public void testDeleteStoreOrderById_Success() {
        logger.info("▶ TEST: Delete Store Order by ID (DELETE)");
        
        Long orderId = 1L;
        Response response = storeService.deleteOrder(orderId);
        
        int statusCode = response.getStatusCode();
        assertTrue(statusCode == StatusCodes.OK || statusCode == StatusCodes.NOT_FOUND, 
                "Status code should be 200 or 404");
        
        ApiResponse apiResponse = response.as(ApiResponse.class);
        
        assertNotNull(apiResponse.getCode(), "Response code should not be null");
        assertTrue(apiResponse.getCode() == StatusCodes.OK || apiResponse.getCode() == StatusCodes.NOT_FOUND, 
                "Response code should be 200 or 404");
        
        assertNotNull(apiResponse.getType(), "Response type should not be null");
        assertEquals(apiResponse.getType(), "unknown", "Response type should be 'unknown'");
        
        assertNotNull(apiResponse.getMessage(), "Response message should not be null");
        
        String emoji = (statusCode == StatusCodes.OK) ? "✅" : "⚠️";
        logger.info("{} PASS | Delete completed → Status={}, Message={}", 
                emoji, statusCode, apiResponse.getMessage());
    }
}
