package com.ievlev.test_task.repository;

import com.ievlev.test_task.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Repository
@Validated
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByModificationTimeBeforeAndPaid(@NotNull Date date, boolean paid);

    Order findByUserId(@Min(1) long userId);
}
