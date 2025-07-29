package com.poc.inventoryservice.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockResponseDto {
    private Long stockId;
    private String stockName;
    private Double price;
    private Integer stockQuantity;
}