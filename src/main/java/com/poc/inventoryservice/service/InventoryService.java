package com.poc.inventoryservice.service;

import com.poc.inventoryservice.request.InventoryRequest;
import com.poc.inventoryservice.response.ApiResponse;
import org.springframework.data.domain.Pageable;

public interface InventoryService {

    ApiResponse createInventory(InventoryRequest dto);

    ApiResponse getInventoryById(Long id);

    ApiResponse getAllInventories(Pageable pageable);

    ApiResponse deleteInventory(Long id);
}
