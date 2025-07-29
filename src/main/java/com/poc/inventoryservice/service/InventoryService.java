package com.poc.inventoryservice.service;

import com.poc.inventoryservice.request.InventoryRequest;
import com.poc.inventoryservice.response.ApiResponse;

public interface InventoryService {

    ApiResponse createInventory(InventoryRequest dto);

    ApiResponse getInventoryById(Long id);

    ApiResponse getAllInventories();

    ApiResponse deleteInventory(Long id);
}
