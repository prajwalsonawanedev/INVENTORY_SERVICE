package com.poc.inventoryservice.enums;

public enum InventoryStatus {
    CREATED("CREATED"),
    VALIDATED("VALIDATED"),
    REJECTED("REJECTED"),
    SUCCESS("SUCCESS"),
    FAILED("FAILED"),
    VALIDATION_FAILED("VALIDATION_FAILED"),
    INVENTORY_FAILED("INVENTORY_FAILED");

    private final String value;

    InventoryStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
