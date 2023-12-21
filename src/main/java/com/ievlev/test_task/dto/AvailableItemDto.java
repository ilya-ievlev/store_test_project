package com.ievlev.test_task.dto;


import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Jacksonized
@Builder
@Data
public class AvailableItemDto {

    @Min(1)
    private Long id;

    @NotBlank
    @NotNull
    private String name;

    @Min(1)
    @Max(Integer.MAX_VALUE)
    private int quantity;

    @Min(0)
    @Max(Integer.MAX_VALUE)
    private int price;

    public AvailableItemDto(String name, int quantity, int price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public AvailableItemDto(Long id, String name, int quantity, int price) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public AvailableItemDto() {
    }
}
