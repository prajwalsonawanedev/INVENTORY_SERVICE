package com.poc.inventoryservice.service.impl;

import com.poc.inventoryservice.entity.Inventory;
import com.poc.inventoryservice.enums.InventoryStatus;
import com.poc.inventoryservice.exception.ResourceNotFoundException;
import com.poc.inventoryservice.repository.InventoryRepository;
import com.poc.inventoryservice.request.InventoryRequest;
import com.poc.inventoryservice.response.ApiResponse;
import com.poc.inventoryservice.response.InventoryResponseDto;
import com.poc.inventoryservice.service.InventoryService;
import com.poc.inventoryservice.service.StockService;
import com.poc.inventoryservice.util.GenericMapper;
import com.poc.inventoryservice.validation.InventoryValidation;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    private final GenericMapper genericMapper;

    private final StockService stockService;

    private List<String> errorList;

    public InventoryServiceImpl(InventoryRepository inventoryRepository, GenericMapper genericMapper, StockService stockService) {
        this.inventoryRepository = inventoryRepository;
        this.genericMapper = genericMapper;
        this.stockService = stockService;
        this.errorList = new ArrayList<>();
    }

    @Override
    @Transactional
    public ApiResponse createInventory(InventoryRequest inventoryRequestDto) {

        if (validateInventory(inventoryRequestDto)) {
            inventoryRequestDto.setStatus(InventoryStatus.SUCCESS.getValue());

            Inventory inventory = genericMapper.convert(inventoryRequestDto, Inventory.class);
            inventory = inventoryRepository.save(inventory);

            InventoryResponseDto inventoryResponseDto = genericMapper.convert(inventory, InventoryResponseDto.class);
            return ApiResponse.response("Inventory Successfully Created", true, "Done", inventoryResponseDto);
        }

        inventoryRequestDto.setStatus(InventoryStatus.REJECTED.getValue());

        Inventory inventory = genericMapper.convert(inventoryRequestDto, Inventory.class);
        inventory = inventoryRepository.save(inventory);
        InventoryResponseDto inventoryResponseDto = genericMapper.convert(inventory, InventoryResponseDto.class);

        return ApiResponse.response("Inventory Not Created", false, "ValidationFailed", inventoryResponseDto);
    }

    @Override
    @Transactional
    public ApiResponse getInventoryById(Long id) {
        InventoryResponseDto inventoryResponseDto = inventoryRepository.findById(id)
                .map(ivnt -> genericMapper.convert(ivnt, InventoryResponseDto.class))
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Inventory Details Not found with Id : %s", id)));

        return ApiResponse.response("Inventory Details Found", true, "Success", inventoryResponseDto);
    }

    @Override
    @Transactional
    public ApiResponse getAllInventories(Pageable pageable) {
        List<InventoryResponseDto> inventoryResponseDtoList = inventoryRepository.findAll(pageable)
                .stream()
                .map(ivnt -> genericMapper.convert(ivnt, InventoryResponseDto.class))
                .toList();
        if (!inventoryResponseDtoList.isEmpty()) {
            return ApiResponse.response("Inventory Details Found", true, "Success", inventoryResponseDtoList);
        }
        return ApiResponse.response("Inventory Details Not  Found", true, "Failed", inventoryResponseDtoList);
    }

    @Override
    @Transactional
    public ApiResponse deleteInventory(Long id) {
        return null;
    }

    public boolean validateInventory(InventoryRequest inventoryRequest) {

        errorList = InventoryValidation.validateInventoryRequest(inventoryRequest);
        Integer stockQuantity = stockService.getStockQuantityById(inventoryRequest.getStockId());

        if (errorList.isEmpty() && inventoryRequest.getQuantity() < stockQuantity) {
            stockService.updateStockQuantity(inventoryRequest.getStockId(), stockQuantity - inventoryRequest.getQuantity());
            return true;
        }

        return false;
    }
}
