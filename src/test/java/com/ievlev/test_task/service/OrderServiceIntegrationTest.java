package com.ievlev.test_task.service;

import com.ievlev.test_task.custom_collection.CustomSet;
import com.ievlev.test_task.dto.AddToOrderDto;
import com.ievlev.test_task.dto.ItemInOrderDto;
import com.ievlev.test_task.initializer.IntegrationTestBase;
import com.ievlev.test_task.model.User;
import org.awaitility.Duration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.awaitility.Awaitility.await;

@SpringBootTest
@Testcontainers
public class OrderServiceIntegrationTest extends IntegrationTestBase {
    private final UserService userService;
    private final OrderService orderService;

    @Autowired
    public OrderServiceIntegrationTest(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    @Sql("classpath:sql/data_with_2_available_items.sql")
    @Test
    public void deleteUnpaidOrdersMustRemoveCreatedOrderAfterTenMinutes() {
        User user = userService.getUserById(1);
        CustomSet<ItemInOrderDto> itemInOrderDtoCustomSet = new CustomSet<>();
        ItemInOrderDto itemInOrderDto = new ItemInOrderDto(1L, 1);
        itemInOrderDtoCustomSet.add(itemInOrderDto);
        AddToOrderDto addToOrderDto = new AddToOrderDto(itemInOrderDtoCustomSet);
        orderService.addRequestToOrder(user.getId(), addToOrderDto);
        await().atMost(Duration.TEN_MINUTES).until(() -> user.getOrder() == null);
    }
}
