package com.poc.inventoryservice.validation;


import com.poc.inventoryservice.request.StockRequestDto;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public interface StockValidation {

    static List<String> validateStockRequest(StockRequestDto stockRequestDto) {

        List<String> errorList = new ArrayList<>();

        if (ObjectUtils.isEmpty(stockRequestDto)) {
            errorList.add("stock request body is missing");
            return errorList;
        }

        if (!StringUtils.hasText(stockRequestDto.getStockName())) {
            errorList.add("Please provide stockname:");
        }

        if (ObjectUtils.isEmpty(stockRequestDto.getPrice()) || stockRequestDto.getPrice() < 0) {
            errorList.add("Please provide valid stock price:");
        }


        if (stockRequestDto.getStockQuantity() == null || stockRequestDto.getStockQuantity() < 1) {
            errorList.add("Please provide a valid quantity (must be >= 1)");
        }

        return errorList;
    }
}
