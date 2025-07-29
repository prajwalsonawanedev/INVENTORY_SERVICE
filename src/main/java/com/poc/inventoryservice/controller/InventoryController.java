package com.poc.inventoryservice.controller;

import com.poc.inventoryservice.request.InventoryRequest;
import com.poc.inventoryservice.response.ApiResponse;
import com.poc.inventoryservice.service.InventoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping("/create-inventory")
    public ResponseEntity<ApiResponse> createInventory(@RequestBody InventoryRequest inventoryRequestDto) {
        return new ResponseEntity<>(inventoryService.createInventory(inventoryRequestDto), HttpStatus.OK);
    }

    @GetMapping("/getInventoryDetailById")
    public ResponseEntity<ApiResponse> getInventoryDetailById(@RequestParam Long inventoryId) {
        return new ResponseEntity<>(inventoryService.getInventoryById(inventoryId), HttpStatus.OK);
    }

    @GetMapping("/getAllInventoryDetails")
    public ResponseEntity<ApiResponse> getInventoryDetailById() {
        return new ResponseEntity<>(inventoryService.getAllInventories(), HttpStatus.OK);
    }
}
