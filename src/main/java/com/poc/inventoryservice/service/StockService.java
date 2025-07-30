package com.poc.inventoryservice.service;

import com.poc.inventoryservice.request.StockRequestDto;
import com.poc.inventoryservice.response.ApiResponse;

public interface StockService {

    ApiResponse createStock(StockRequestDto stockRequestDto);

    ApiResponse deleteStock(Long id);

    ApiResponse getStockById(Long id);

    ApiResponse getStockByName(String stockName);

    ApiResponse getAllStocks();

    Integer getStockQuantityById(Long stockId);

    void updateStockQuantity(Long stockId, Integer remeaningQuantity);
}
