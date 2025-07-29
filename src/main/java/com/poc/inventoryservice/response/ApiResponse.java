package com.poc.inventoryservice.response;

public class ApiResponse<T> {

    private String message;

    private boolean success;

    private T data;

    private String additionalMessage;

    public ApiResponse() {

    }

    public ApiResponse(String message, boolean success, String additionalMessage, T data) {
        this.message = message;
        this.success = success;
        this.additionalMessage = additionalMessage;
        this.data = data;
    }

    public static <T> ApiResponse<T> response(String message, boolean success, String additionalMessage, T data) {
        return new ApiResponse<>(message, success, additionalMessage, data);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
