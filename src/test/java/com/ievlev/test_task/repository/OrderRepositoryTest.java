package com.ievlev.test_task.repository;

import com.ievlev.test_task.initializer.IntegrationTestBase;
import com.ievlev.test_task.model.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

@Sql("classpath:sql/data_with_ordered_item.sql")
@DataJpaTest
@Testcontainers
public class OrderRepositoryTest extends IntegrationTestBase {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderRepositoryTest(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    @Test
    public void findByModificationTimeBeforeAndPaidShouldReturnCorrectItem() {
        List<Order> orderList = orderRepository.findByModificationTimeBeforeAndPaid(new Date(), false);
        if (orderList.isEmpty()) {
            fail("order list is empty");
        }
        Order order = orderList.get(0);
        assertThat(order.getModificationTime().getTime() < System.currentTimeMillis()
                && order.getId().equals(1)
                && order.isPaid() == false
                && order.getUser().getId().equals(1));
    }

    @Test
    public void findByUserIdShouldReturnCorrectOrder(){
        Order order = orderRepository.findByUserId(1);
        assertThat(order.getModificationTime().getTime() < System.currentTimeMillis()
                && order.getId().equals(1)
                && order.isPaid() == false
                && order.getUser().getId().equals(1));
    }
}
