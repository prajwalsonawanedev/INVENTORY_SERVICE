package com.poc.inventoryservice.controller;

import com.poc.inventoryservice.request.StockRequestDto;
import com.poc.inventoryservice.response.ApiResponse;
import com.poc.inventoryservice.service.StockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

    @PostMapping("/create-stocks")
    public ResponseEntity<ApiResponse> createStock(@RequestBody StockRequestDto stockRequest) {
        return new ResponseEntity<>(stockService.createStock(stockRequest), HttpStatus.OK);

    }

    @GetMapping("/get-stockById")
    public ResponseEntity<ApiResponse> getStockById(@RequestParam Long stockId) {
        return new ResponseEntity<>(stockService.getStockById(stockId), HttpStatus.OK);

    }

    @GetMapping("/get-stockByName")
    public ResponseEntity<ApiResponse> getStockById(@RequestParam String stockName) {
        return new ResponseEntity<>(stockService.getStockByName(stockName), HttpStatus.OK);
    }

    @GetMapping("/get-allstocks")
    public ResponseEntity<ApiResponse> getStocks() {
        return new ResponseEntity<>(stockService.getAllStocks(), HttpStatus.OK);
    }

    @DeleteMapping("/delete-stock")
    public ResponseEntity<ApiResponse> deleteStock(@RequestParam Long stockId) {
        return new ResponseEntity<>(stockService.deleteStock(stockId), HttpStatus.OK);
    }

}
