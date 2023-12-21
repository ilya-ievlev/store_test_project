package com.ievlev.test_task.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.Map;

@Data
@Jacksonized
@Builder
public class AppErrorMapDto {
    private int statusCode;
    private Map<String, String> errors;
    private String message;

    public AppErrorMapDto(int statusCode, Map<String, String> errors, String message) {
        this.statusCode = statusCode;
        this.errors = errors;
        this.message = message;
    }
}
