package com.alberto.gesresfamily.exception;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class ErrorResponse {

    private int internalError;
    private String message;
    private Map<String, String> errors;

    private ErrorResponse(int internalError, String message) {
        this.internalError = internalError;
        this.message = message;
        errors = new HashMap<>();
    }

    private ErrorResponse(int internalError, String message, Map<String, String> errors) {
        this.internalError = internalError;
        this.message = message;
        this.errors = errors;
    }

    public static ErrorResponse validationError(Map<String, String> errors) {
        return new ErrorResponse(400, "Validation error", errors);
    }

    public static ErrorResponse badRequest(String message) {
        return new ErrorResponse(400, message);
    }

    public static ErrorResponse resourceNotFound(String message) {
        return new ErrorResponse(404, message);
    }

    public static ErrorResponse internalServerError(String message) {
        return new ErrorResponse(500, message);
    }

}
