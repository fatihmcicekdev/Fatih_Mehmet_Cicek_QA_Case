package com.insider.api.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * OrderResponse - Order Response Model
 * POJO for order response from API
 */
public class OrderResponse {
    
    @JsonProperty("id")
    private Long id;
    
    @JsonProperty("petId")
    private Long petId;
    
    @JsonProperty("quantity")
    private Integer quantity;
    
    @JsonProperty("shipDate")
    private String shipDate;
    
    @JsonProperty("status")
    private String status;
    
    @JsonProperty("complete")
    private Boolean complete;
    
    // Constructors
    public OrderResponse() {
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getPetId() {
        return petId;
    }
    
    public void setPetId(Long petId) {
        this.petId = petId;
    }
    
    public Integer getQuantity() {
        return quantity;
    }
    
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    
    public String getShipDate() {
        return shipDate;
    }
    
    public void setShipDate(String shipDate) {
        this.shipDate = shipDate;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public Boolean getComplete() {
        return complete;
    }
    
    public void setComplete(Boolean complete) {
        this.complete = complete;
    }
    
    @Override
    public String toString() {
        return "OrderResponse{" +
                "id=" + id +
                ", petId=" + petId +
                ", quantity=" + quantity +
                ", shipDate='" + shipDate + '\'' +
                ", status='" + status + '\'' +
                ", complete=" + complete +
                '}';
    }
}

