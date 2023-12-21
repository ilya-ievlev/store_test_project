package com.ievlev.test_task.repository;

import com.ievlev.test_task.initializer.IntegrationTestBase;
import com.ievlev.test_task.model.AvailableItem;
import com.ievlev.test_task.model.Order;
import com.ievlev.test_task.model.OrderedItem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@DataJpaTest
@Testcontainers
public class OrderedItemRepositoryTest extends IntegrationTestBase {
    private final OrderedItemRepository orderedItemRepository;
    private final AvailableItemRepository availableItemRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public OrderedItemRepositoryTest(OrderedItemRepository orderedItemRepository,
                                     AvailableItemRepository availableItemRepository, OrderRepository orderRepository) {
        this.orderedItemRepository = orderedItemRepository;
        this.availableItemRepository = availableItemRepository;
        this.orderRepository = orderRepository;
    }


    @Sql("classpath:sql/data_with_ordered_item.sql")
    @Test
    public void findByAvailableItemAndOrder() {
        AvailableItem availableItem = availableItemRepository.getReferenceById(1L);
        Order order = orderRepository.getReferenceById(1L);
        Optional<OrderedItem> orderedItemOptional = orderedItemRepository.findByAvailableItemAndOrder(availableItem, order);
        if (orderedItemOptional.isPresent()) {
            OrderedItem orderedItem = orderedItemOptional.get();
            assertThat(orderedItem.getPrice() == 12
                    && orderedItem.getQuantity() == 12
                    && orderedItem.getOrder().equals(order)
                    && orderedItem.getAvailableItem().equals(availableItem));
        } else{
            fail("orderedItem is not available");
        }
    }
}
