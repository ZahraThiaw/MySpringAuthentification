package org.example.zahra.springauthentification.filters;

import lombok.Data;

import java.util.List;

@Data
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;
    private List<String> errors;
    private String status;
    private int statusCode;

    public ApiResponse() {}

    public ApiResponse(boolean success, String message, T data, List<String> errors, String status, int statusCode) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.errors = errors;
        this.status = status;
        this.statusCode = statusCode;
    }
}
