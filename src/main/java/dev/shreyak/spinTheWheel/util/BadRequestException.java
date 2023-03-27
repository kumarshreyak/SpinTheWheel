package dev.shreyak.spinTheWheel.util;

public class BadRequestException extends Exception {
    private Integer statusCode;
    private String message;

    public BadRequestException() {
    }

    public BadRequestException(Integer statusCode, String message) {
        super(message);
        this.message = message;
        this.statusCode = statusCode;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }
}