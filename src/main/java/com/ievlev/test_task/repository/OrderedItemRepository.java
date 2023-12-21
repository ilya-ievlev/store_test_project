package com.ievlev.test_task.repository;

import com.ievlev.test_task.model.AvailableItem;
import com.ievlev.test_task.model.Order;
import com.ievlev.test_task.model.OrderedItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Optional;

@Repository
@Validated
public interface OrderedItemRepository extends CrudRepository<OrderedItem, Long> {
    Optional<OrderedItem> findByAvailableItemAndOrder(@Valid AvailableItem availableItem, @Valid Order order);
}
