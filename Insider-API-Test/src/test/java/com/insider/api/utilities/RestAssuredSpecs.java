package com.insider.api.utilities;

import com.insider.api.config.ConfigReader;
import com.insider.api.constants.StatusCodes;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class RestAssuredSpecs {
    
    public static RequestSpecification baseRequestSpec() {
        String baseUrl = ConfigReader.getBaseUrl();
        
        RequestSpecBuilder builder = new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON);
        
        if (ConfigReader.isRequestLoggingEnabled()) {
            builder.log(LogDetail.ALL);
        }
        
        return builder.build();
    }
    
    public static ResponseSpecification successResponseSpec() {
        ResponseSpecBuilder builder = new ResponseSpecBuilder()
                .expectStatusCode(StatusCodes.OK);
        
        if (ConfigReader.isResponseLoggingEnabled()) {
            builder.log(LogDetail.ALL);
        }
        
        return builder.build();
    }
    
    public static ResponseSpecification createdResponseSpec() {
        ResponseSpecBuilder builder = new ResponseSpecBuilder()
                .expectStatusCode(StatusCodes.CREATED);
        
        if (ConfigReader.isResponseLoggingEnabled()) {
            builder.log(LogDetail.ALL);
        }
        
        return builder.build();
    }
}

