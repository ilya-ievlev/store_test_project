package com.ievlev.test_task.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Data
@Jacksonized
@Builder
public class ItemInOrderDto {

    @NotNull
    @Min(1)
    private Long availableItemId;


    @Min(1)
    @Max(Integer.MAX_VALUE)
    private int quantity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemInOrderDto that = (ItemInOrderDto) o;
        return availableItemId.equals(that.availableItemId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(availableItemId);
    }

    public ItemInOrderDto(Long availableItemId, int quantity) {
        this.availableItemId = availableItemId;
        this.quantity = quantity;
    }
}
