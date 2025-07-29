package com.poc.inventoryservice.controller;

import com.poc.inventoryservice.request.InventoryRequest;
import com.poc.inventoryservice.response.ApiResponse;
import com.poc.inventoryservice.service.InventoryService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
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

    @PostMapping
    public ResponseEntity<ApiResponse> createInventory(@RequestBody InventoryRequest inventoryRequestDto) {
        return new ResponseEntity<>(inventoryService.createInventory(inventoryRequestDto), HttpStatus.OK);
    }

    @GetMapping("/{inventoryId}")
    public ResponseEntity<ApiResponse> getInventoryDetailById(@PathVariable("inventoryId") Long inventoryId) {
        return new ResponseEntity<>(inventoryService.getInventoryById(inventoryId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getInventoryDetails(@PageableDefault(page = 0, size = 100)
                                                           @SortDefault.SortDefaults({
                                                                   @SortDefault(sort = "inventoryId", direction = Sort.Direction.DESC)})
                                                           Pageable pageable) {
        return new ResponseEntity<>(inventoryService.getAllInventories(pageable), HttpStatus.OK);
    }
}
