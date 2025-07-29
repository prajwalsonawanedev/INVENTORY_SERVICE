package com.poc.inventoryservice.serviceimpl;

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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public ApiResponse deleteStock(Long stockId) {

        if (!stockRepository.existsById(stockId)) {
            throw new ResourceNotFoundException("Invalid stock Id: " + stockId);
        }
        stockRepository.deleteById(stockId);

        return ApiResponse.response("Stock deleted successfully: " + stockId, true, null, null);
    }

    @Override
    public ApiResponse getStockById(Long id) {
        StockResponseDto stock = stockRepository.findById(id)
                .map(stk -> genericMapper.convert(stk, StockResponseDto.class))
                .orElseThrow(() -> new ResourceNotFoundException("stock Not found with id:" + id));

        return ApiResponse.response("Stock details found", true, "", stock);
    }

    @Override
    public ApiResponse getStockByName(String stockName) {
        StockResponseDto stock = stockRepository.findByStockName(stockName)
                .map(stk -> genericMapper.convert(stk, StockResponseDto.class))
                .orElseThrow(() -> new ResourceNotFoundException("stock Not found with stock name:" + stockName));

        return ApiResponse.response("Stock details found", true, "", stock);
    }

    @Override
    public ApiResponse getAllStocks() {
        List<StockResponseDto> stockList = stockRepository.findAll()
                .stream()
                .map(stk -> genericMapper.convert(stk, StockResponseDto.class))
                .toList();

        return ApiResponse.response("Stock details found", true, "", stockList);
    }

    @Override
    public Integer getStockQuantityById(Long stockId) {
        if (!ObjectUtils.isEmpty(stockId)) {
            return stockRepository.findStockQuantityByStockId(stockId);
        }
        return 0;
    }

    @Override
    public void updateStockQuantity(Long stockId, Integer remainingQuantity) {
        Stock stock = stockRepository.findById(stockId)
                .orElseThrow(() -> new RuntimeException("Stock not found with ID: " + stockId));

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
