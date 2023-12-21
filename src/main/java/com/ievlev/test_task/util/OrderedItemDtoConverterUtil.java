package com.ievlev.test_task.util;

import com.ievlev.test_task.dto.OrderedItemDto;
import com.ievlev.test_task.model.OrderedItem;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class OrderedItemDtoConverterUtil {
    public static OrderedItemDto convertItemToDto(OrderedItem orderedItem) {
        return new OrderedItemDto(orderedItem.getId(), orderedItem.getPrice(), orderedItem.getQuantity());
    }

    public static List<OrderedItemDto> convertItemListToDto(List<OrderedItem> orderedItemList) {
        List<OrderedItemDto> orderedItemDtoList = new ArrayList<>();
        for (OrderedItem orderedItem : orderedItemList) {
            orderedItemDtoList.add(convertItemToDto(orderedItem));
        }
        return orderedItemDtoList;
    }
}
