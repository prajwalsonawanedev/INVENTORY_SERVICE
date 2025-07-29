package com.poc.inventoryservice.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockRequestDto {


    @NotBlank(message = "Stock name is required")
    private String stockName;

    @Min(value = 1, message = "Price must be greater than 0")
    private Double price;

    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer stockQuantity;

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }
}
