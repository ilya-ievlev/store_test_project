package com.ievlev.test_task.dto;

import lombok.Data;

@Data
public class AppErrorStatusDto {
    private int status;
    private String message;

    public AppErrorStatusDto(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
