package com.poc.inventoryservice.controller;

import com.poc.inventoryservice.request.StockRequestDto;
import com.poc.inventoryservice.response.ApiResponse;
import com.poc.inventoryservice.service.StockService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stock")
public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createStock(@RequestBody StockRequestDto stockRequest) {
        return new ResponseEntity<>(stockService.createStock(stockRequest), HttpStatus.OK);

    }

    @GetMapping("/byId/{stockId}")
    public ResponseEntity<ApiResponse> getStockById(@PathVariable("stockId") Long stockId) {
        return new ResponseEntity<>(stockService.getStockById(stockId), HttpStatus.OK);
    }

    @GetMapping("/byName/{stockName}")
    public ResponseEntity<ApiResponse> getStockByName(@PathVariable("stockName") String stockName) {
        return new ResponseEntity<>(stockService.getStockByName(stockName), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getStocks() {
        return new ResponseEntity<>(stockService.getAllStocks(), HttpStatus.OK);
    }

    @DeleteMapping("/{stockId}")
    public ResponseEntity<ApiResponse> deleteStock(@PathVariable("stockId") Long stockId) {
        return new ResponseEntity<>(stockService.deleteStock(stockId), HttpStatus.OK);
    }

}
