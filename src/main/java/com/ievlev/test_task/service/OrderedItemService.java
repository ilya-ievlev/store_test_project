package com.ievlev.test_task.service;

import com.ievlev.test_task.exceptions.UnableToUpdateEntityException;
import com.ievlev.test_task.model.AvailableItem;
import com.ievlev.test_task.model.Order;
import com.ievlev.test_task.model.OrderedItem;
import com.ievlev.test_task.repository.OrderedItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderedItemService {
    private final OrderedItemRepository orderedItemRepository;

    public Optional<OrderedItem> findByAvailableItemAndOrder(AvailableItem availableItem, Order order) {
        if (availableItem == null || order == null) {
            throw new IllegalArgumentException("availableItem and order can't be null");
        }
        return orderedItemRepository.findByAvailableItemAndOrder(availableItem, order);
    }

    public void update(OrderedItem orderedItem) {
        if (orderedItem == null) {
            throw new IllegalArgumentException("orderedItem can't be null");
        }
        if (orderedItem.getId() == null) {
            throw new UnableToUpdateEntityException("you can't update item without id");
        }
        orderedItemRepository.save(orderedItem);
    }

    public void saveNew(OrderedItem orderedItem) {
        if (orderedItem == null) {
            throw new IllegalArgumentException("orderedItem can't be null");
        }
        orderedItem.setId(null); //just in case someone will send item with id
        orderedItemRepository.save(orderedItem);
    }


    public void delete(OrderedItem orderedItem) {
        if (orderedItem == null) {
            throw new IllegalArgumentException("orderedItem can't be null");
        }
        orderedItemRepository.delete(orderedItem);
    }

}
