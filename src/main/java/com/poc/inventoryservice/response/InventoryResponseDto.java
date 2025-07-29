package com.poc.inventoryservice.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class InventoryResponseDto {

    private String userId;

    private Long stockId;

    private Double totalAmount;

    private Integer quantity;

    private String status;

}
