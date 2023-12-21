package com.ievlev.test_task.util;

import com.ievlev.test_task.dto.AvailableItemDto;
import com.ievlev.test_task.model.AvailableItem;
import lombok.experimental.UtilityClass;

import javax.validation.Valid;

@UtilityClass
public class AvailableItemDtoConverterUtil {

    public static AvailableItem availableItemFromItemDtoWithId(@Valid AvailableItemDto availableItemDto) {
        checkInputAvailableItemDto(availableItemDto);
        if (availableItemDto.getId() == null) {
            throw new IllegalArgumentException("you can't pass availableItemDto without id there");
        }
        return new AvailableItem(availableItemDto.getId(), availableItemDto.getName(), availableItemDto.getPrice(), availableItemDto.getQuantity());
    }

    public static AvailableItem availableItemFromItemDtoWithoutId(@Valid AvailableItemDto availableItemDto) {
        checkInputAvailableItemDto(availableItemDto);
        return new AvailableItem(availableItemDto.getName(), availableItemDto.getPrice(), availableItemDto.getQuantity());
    }

    public static AvailableItemDto itemDtoFromAvailableItemWithId(@Valid AvailableItem availableItem) {
        if (availableItem == null) {
            throw new IllegalArgumentException("availableItem can't be null");
        }
        return new AvailableItemDto(availableItem.getId(), availableItem.getName(), availableItem.getQuantity(), availableItem.getPrice());
    }

    private static void checkInputAvailableItemDto(AvailableItemDto availableItemDto) {
        if (availableItemDto == null) {
            throw new IllegalArgumentException("availableItemDto can't be null");
        }
    }
}
