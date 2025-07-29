package com.poc.inventoryservice.serviceimpl;

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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public ApiResponse createInventory(InventoryRequest inventoryRequestDto) {

        if (validateInventory(inventoryRequestDto)) {
            inventoryRequestDto.setStatus(InventoryStatus.SUCCESS.getValue());

            Inventory inventory = genericMapper.convert(inventoryRequestDto, Inventory.class);
            inventoryRepository.save(inventory);

            InventoryResponseDto inventoryResponseDto = genericMapper.convert(inventory, InventoryResponseDto.class);
            return ApiResponse.response("Inventory Succesfully Created", true, "Done", inventoryResponseDto);
        }

        inventoryRequestDto.setStatus(InventoryStatus.REJECTED.getValue());

        Inventory inventory = genericMapper.convert(inventoryRequestDto, Inventory.class);
        inventoryRepository.save(inventory);

        return ApiResponse.response("Inventory Not Creatad", false, "ValidationFailed", inventoryRequestDto);
    }

    @Override
    public ApiResponse getInventoryById(Long id) {
        InventoryResponseDto inventoryResponseDto = inventoryRepository.findById(id)
                .map(ivnt -> genericMapper.convert(ivnt, InventoryResponseDto.class))
                .orElseThrow(() -> new ResourceNotFoundException("Inventory Details Not found with Id :" + id));

        return ApiResponse.response("Inventory Details Found", true, "Success", inventoryResponseDto);
    }

    @Override
    public ApiResponse getAllInventories() {
        List<InventoryResponseDto> inventoryResponseDtoList = inventoryRepository.findAll()
                .stream()
                .map(ivnt -> genericMapper.convert(ivnt, InventoryResponseDto.class))
                .toList();
        if (!CollectionUtils.isEmpty(inventoryResponseDtoList)) {
            return ApiResponse.response("Inventory Details Found", true, "Success", inventoryResponseDtoList);
        }
        return ApiResponse.response("Inventory Details Not  Found", true, "Failed", inventoryResponseDtoList);
    }

    @Override
    public ApiResponse deleteInventory(Long id) {
        return null;
    }

    public boolean validateInventory(InventoryRequest inventoryRequest) {

        errorList = InventoryValidation.validateInventoryRequest(inventoryRequest);
        Integer stockQuantity = stockService.getStockQuantityById(inventoryRequest.getStockId());

        if (CollectionUtils.isEmpty(errorList) && inventoryRequest.getQuantity() < stockQuantity) {
            stockService.updateStockQuantity(inventoryRequest.getStockId(), stockQuantity - inventoryRequest.getQuantity());
            return true;
        }

        return false;
    }
}
