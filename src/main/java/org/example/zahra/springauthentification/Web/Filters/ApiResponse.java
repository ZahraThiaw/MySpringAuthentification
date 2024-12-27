package org.example.zahra.springauthentification.Web.Filters;

import lombok.Data;

// Utilisation de Lombok pour générer les getters, setters, toString, etc.
@Data
public class ApiResponse<T> {

    private String status;
    private int statusCode;
    private String message;
    private T data;

    // Constructeur vide
    public ApiResponse() {}

    // Constructeur avec paramètres
    public ApiResponse(String status, int statusCode, String message, T data) {
        this.status = status;
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }

    // Setters et Getters (si vous ne souhaitez pas utiliser Lombok)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
