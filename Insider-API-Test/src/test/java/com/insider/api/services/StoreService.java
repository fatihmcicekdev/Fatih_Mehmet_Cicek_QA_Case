package com.insider.api.services;

import com.insider.api.constants.Endpoints;
import com.insider.api.models.request.OrderRequest;
import com.insider.api.utilities.RestAssuredSpecs;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.restassured.RestAssured.given;

public class StoreService {
    
    private static final Logger logger = LoggerFactory.getLogger(StoreService.class);
    
    public Response getInventory() {
        logger.debug("GET {}", Endpoints.STORE_INVENTORY);
        
        return given()
                .spec(RestAssuredSpecs.baseRequestSpec())
            .when()
                .get(Endpoints.STORE_INVENTORY)
            .then()
                .extract()
                .response();
    }
    
    public Response createOrder(OrderRequest orderRequest) {
        logger.debug("POST {} with ID: {}", Endpoints.STORE_ORDER, orderRequest.getId());
        
        return given()
                .spec(RestAssuredSpecs.baseRequestSpec())
                .body(orderRequest)
            .when()
                .post(Endpoints.STORE_ORDER)
            .then()
                .extract()
                .response();
    }
    
    public Response getOrderById(Long orderId) {
        logger.debug("GET {} with ID: {}", Endpoints.STORE_ORDER_BY_ID, orderId);
        
        return given()
                .spec(RestAssuredSpecs.baseRequestSpec())
                .pathParam("orderId", orderId)
            .when()
                .get(Endpoints.STORE_ORDER_BY_ID)
            .then()
                .extract()
                .response();
    }
    
    public Response deleteOrder(Long orderId) {
        logger.debug("DELETE {} with ID: {}", Endpoints.STORE_ORDER_BY_ID, orderId);
        
        return given()
                .spec(RestAssuredSpecs.baseRequestSpec())
                .pathParam("orderId", orderId)
            .when()
                .delete(Endpoints.STORE_ORDER_BY_ID)
            .then()
                .extract()
                .response();
    }
    
}