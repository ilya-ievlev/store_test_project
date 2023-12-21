package com.ievlev.test_task.util;

import com.ievlev.test_task.dto.OrderDto;
import com.ievlev.test_task.model.Order;
import lombok.experimental.UtilityClass;

@UtilityClass
public class OrderDtoConverterUtil {

    public static OrderDto orderDtoFromOrder(Order order) {
        return new OrderDto(order.isPaid(), order.getModificationTime(), order.getUser().getUsername(), OrderedItemDtoConverterUtil.convertItemListToDto(order.getOrderedItemList()));
    }

}
