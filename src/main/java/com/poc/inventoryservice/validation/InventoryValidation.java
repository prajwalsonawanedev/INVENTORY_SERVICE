package com.poc.inventoryservice.validation;


import com.poc.inventoryservice.request.InventoryRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public interface InventoryValidation {


    static List<String> validateInventoryRequest(InventoryRequest inventoryRequestDto) {

        List<String> errorList = new ArrayList<>();

        if (ObjectUtils.isEmpty(inventoryRequestDto)) {
            errorList.add("Request body is missing");
            return errorList;
        }

        if (!StringUtils.hasText(inventoryRequestDto.getUserId())) {
            errorList.add("Please provide Valid userId :");
        }

        if (inventoryRequestDto.getStockId() == null || inventoryRequestDto.getStockId() <= 0) {
            errorList.add("Please provide a valid stockId (must be > 0)");
        }

        if (inventoryRequestDto.getTotalAmount() == null || inventoryRequestDto.getTotalAmount() < 1) {
            errorList.add("Please provide a valid totalAmount (must be >= 1)");
        }

        if (inventoryRequestDto.getQuantity() == null || inventoryRequestDto.getQuantity() < 1) {
            errorList.add("Please provide a valid quantity (must be >= 1)");
        }

        return errorList;
    }

}
