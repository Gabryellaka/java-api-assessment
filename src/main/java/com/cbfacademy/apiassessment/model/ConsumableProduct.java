package com.cbfacademy.apiassessment.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ConsumableProduct extends Product {
    private LocalDate expirationDate;
    private String consumptionInstructions;

    @JsonCreator
    public ConsumableProduct(
            @JsonProperty("id") UUID id,
            @JsonProperty("name") String name,
            @JsonProperty("description") String description,
            @JsonProperty("price") BigDecimal price,
            @JsonProperty("minimumQuantity") int minimumQuantity,
            @JsonProperty("maximumQuantity") int maximumQuantity,
            @JsonProperty("category") String category,
            @JsonProperty("supplier") String supplier,
            @JsonProperty("createdOn") LocalDateTime createdOn,
            @JsonProperty("expirationDate") LocalDate expirationDate,
            @JsonProperty("consumptionInstructions") String consumptionInstructions) {
        super(id, name, description, price, minimumQuantity, maximumQuantity, category, supplier, createdOn);
        this.expirationDate = expirationDate;
        this.consumptionInstructions = consumptionInstructions;
    }

    
    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getConsumptionInstructions() {
        return consumptionInstructions;
    }

    public void setConsumptionInstructions(String consumptionInstructions) {
        this.consumptionInstructions = consumptionInstructions;
    }

    
    @Override
    public String toString() {
        return "ConsumableProduct{" +
                ", name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", price=" + getPrice() +
                ", minimumQuantity=" + getMinimumQuantity() +
                ", maximumQuantity=" + getMaximumQuantity() +
                ", createdOn=" + getCreatedOn() +
                ", category='" + getCategory() + '\'' +
                ", supplier='" + getSupplier() + '\'' +
                ", expirationDate=" + expirationDate +
                ", consumptionInstructions='" + consumptionInstructions + '\'' +
                '}';
    }
}
