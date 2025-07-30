package com.poc.inventoryservice.service.impl;

import com.poc.inventoryservice.entity.Stock;
import com.poc.inventoryservice.exception.ResourceNotFoundException;
import com.poc.inventoryservice.repository.StockRepository;
import com.poc.inventoryservice.request.StockRequestDto;
import com.poc.inventoryservice.response.ApiResponse;
import com.poc.inventoryservice.response.StockResponseDto;
import com.poc.inventoryservice.service.StockService;
import com.poc.inventoryservice.util.GenericMapper;
import com.poc.inventoryservice.util.JsonUtil;
import com.poc.inventoryservice.validation.StockValidation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;

    private final JsonUtil jsonUtil;

    private final GenericMapper genericMapper;

    public StockServiceImpl(StockRepository stockRepository, JsonUtil jsonUtil, GenericMapper genericMapper) {
        this.stockRepository = stockRepository;
        this.jsonUtil = jsonUtil;
        this.genericMapper = genericMapper;
    }

    private List<String> errorList = new ArrayList<>();


    @Override
    @Transactional
    public ApiResponse createStock(StockRequestDto stockRequestDto) {

        if (validate(stockRequestDto)) {

            Stock stock = genericMapper.convert(stockRequestDto, Stock.class);
            stock = stockRepository.save(stock);

            StockResponseDto stockResponseDto = genericMapper.convert(stock, StockResponseDto.class);

            return ApiResponse.response("Stock added successfully", true, "Stock Added", stockResponseDto);
        }
        return ApiResponse.response("Invalid Stock Details", true, "Invalid", null);
    }

    @Override
    @Transactional
    public ApiResponse deleteStock(Long stockId) {

        if (!stockRepository.existsById(stockId)) {
            throw new ResourceNotFoundException(String.format("Invalid stock Id: %s", stockId));
        }
        stockRepository.deleteById(stockId);

        return ApiResponse.response(String.format("Stock deleted successfully: %s", stockId), true, null, null);
    }

    @Override
    @Transactional
    public ApiResponse getStockById(Long id) {
        StockResponseDto stock = stockRepository.findById(id)
                .map(stk -> genericMapper.convert(stk, StockResponseDto.class))
                .orElseThrow(() -> new ResourceNotFoundException(String.format("stock Not found with id: %s", id)));

        return ApiResponse.response("Stock details found", true, "", stock);
    }

    @Override
    @Transactional
    public ApiResponse getStockByName(String stockName) {
        StockResponseDto stock = stockRepository.findByStockName(stockName)
                .map(stk -> genericMapper.convert(stk, StockResponseDto.class))
                .orElseThrow(() -> new ResourceNotFoundException(String.format("stock Not found with stock name: %s", stockName)));

        return ApiResponse.response("Stock details found", true, "", stock);
    }

    @Override
    @Transactional
    public ApiResponse getAllStocks() {
        List<StockResponseDto> stockList = stockRepository.findAll()
                .stream()
                .map(stk -> genericMapper.convert(stk, StockResponseDto.class))
                .toList();

        return ApiResponse.response("Stock details found", true, "Stock Details", stockList);
    }

    @Override
    public Integer getStockQuantityById(Long stockId) {
        int qty = 0;
        try {
            if (!ObjectUtils.isEmpty(stockId)) {
                qty = stockRepository.findStockQuantityByStockId(stockId);
            }
        } catch (Exception e) {
            return qty;
        }
        return qty;
    }

    @Override
    @Transactional
    public void updateStockQuantity(Long stockId, Integer remainingQuantity) {
        Stock stock = stockRepository.findById(stockId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Stock not found with ID: %s", stockId)));

        stock.setStockQuantity(remainingQuantity);
        stockRepository.save(stock);
    }

    public boolean validate(StockRequestDto stockRequestDto) {
        errorList = StockValidation.validateStockRequest(stockRequestDto);

        if (!CollectionUtils.isEmpty(errorList)) {
            return false;
        }
        return true;
    }
}
