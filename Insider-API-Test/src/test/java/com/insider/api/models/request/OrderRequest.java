package com.insider.api.models.request;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * OrderRequest - Order Request Model
 * POJO for creating/updating orders
 */
public class OrderRequest {
    
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
    public OrderRequest() {
    }
    
    public OrderRequest(Long id, Long petId, Integer quantity, String shipDate, String status, Boolean complete) {
        this.id = id;
        this.petId = petId;
        this.quantity = quantity;
        this.shipDate = shipDate;
        this.status = status;
        this.complete = complete;
    }
    
    // Builder pattern for easy object creation
    public static Builder builder() {
        return new Builder();
    }
    
    public static class Builder {
        private Long id;
        private Long petId;
        private Integer quantity;
        private String shipDate;
        private String status;
        private Boolean complete;
        
        public Builder id(Long id) {
            this.id = id;
            return this;
        }
        
        public Builder petId(Long petId) {
            this.petId = petId;
            return this;
        }
        
        public Builder quantity(Integer quantity) {
            this.quantity = quantity;
            return this;
        }
        
        public Builder shipDate(String shipDate) {
            this.shipDate = shipDate;
            return this;
        }
        
        public Builder status(String status) {
            this.status = status;
            return this;
        }
        
        public Builder complete(Boolean complete) {
            this.complete = complete;
            return this;
        }
        
        public OrderRequest build() {
            return new OrderRequest(id, petId, quantity, shipDate, status, complete);
        }
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
        return "OrderRequest{" +
                "id=" + id +
                ", petId=" + petId +
                ", quantity=" + quantity +
                ", shipDate='" + shipDate + '\'' +
                ", status='" + status + '\'' +
                ", complete=" + complete +
                '}';
    }
}

