package com.ievlev.test_task.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class OrderedItemDto {

    private long id;
    private int price;
    private int quantity;

    public OrderedItemDto(long id, int price, int quantity) {
        this.id = id;
        this.price = price;
        this.quantity = quantity;
    }
}
