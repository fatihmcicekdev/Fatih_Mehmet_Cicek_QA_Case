package com.insider.api.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * ApiResponse - Generic API Response Model
 * Used for standard API responses (create, update, delete operations)
 */
public class ApiResponse {
    
    @JsonProperty("code")
    private Integer code;
    
    @JsonProperty("type")
    private String type;
    
    @JsonProperty("message")
    private String message;
    
    // Constructors
    public ApiResponse() {
    }
    
    // Getters and Setters
    public Integer getCode() {
        return code;
    }
    
    public void setCode(Integer code) {
        this.code = code;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    @Override
    public String toString() {
        return "ApiResponse{" +
                "code=" + code +
                ", type='" + type + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}

