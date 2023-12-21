package com.ievlev.test_task.dto;

import com.ievlev.test_task.custom_collection.CustomSet;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@Jacksonized
@Builder
@Data
public class AddToOrderDto {
    @NotEmpty(message = "addToOrderDto can't be empty")
    private CustomSet<@Valid ItemInOrderDto> itemsFromOrderDto;
    // I make it set, because I don't want users to put for example 2 pieces of item1 and another 3 pieces of item1 at a same time in same dto


    public AddToOrderDto(CustomSet<@Valid ItemInOrderDto> itemsFromOrderDto) {
        this.itemsFromOrderDto = itemsFromOrderDto;
    }
}
