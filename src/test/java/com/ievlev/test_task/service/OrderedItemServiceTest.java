package com.ievlev.test_task.service;

import com.ievlev.test_task.exceptions.UnableToUpdateEntityException;
import com.ievlev.test_task.model.AvailableItem;
import com.ievlev.test_task.model.Order;
import com.ievlev.test_task.model.OrderedItem;
import com.ievlev.test_task.repository.OrderedItemRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class OrderedItemServiceTest {
    @InjectMocks
    private OrderedItemService orderedItemService;
    @Mock
    private OrderedItemRepository orderedItemRepository;


    @Test
    public void findByAvailableItemAndOrderShouldReturnCorrectItem() {
        AvailableItem availableItem = new AvailableItem();
        Order order = new Order();
        Optional<OrderedItem> orderedItemOptional = Optional.of(new OrderedItem(1L, 1232, 1232, order, availableItem));
        Mockito.doReturn(orderedItemOptional).when(orderedItemRepository).findByAvailableItemAndOrder(availableItem, order);
        Optional<OrderedItem> orderedItemOptionalToCheck = orderedItemService.findByAvailableItemAndOrder(availableItem, order);
        assertThat(orderedItemOptionalToCheck.isPresent()
                && orderedItemOptionalToCheck.get().getOrder().equals(order)
                && orderedItemOptionalToCheck.get().getAvailableItem().equals(availableItem));
    }


    @Test
    public void updateThrowsExceptionIfOrderedItemHasNoId() {
        OrderedItem orderedItem = new OrderedItem(123, 12, new Order(), new AvailableItem());
        Assertions.assertThrows(UnableToUpdateEntityException.class, () -> orderedItemService.update(orderedItem));
    }


    @Test
    public void updateShouldCallSaveOnce() {
        OrderedItem orderedItem = new OrderedItem(1L, 123, 123, new Order(), new AvailableItem());
        orderedItemService.update(orderedItem);
        Mockito.verify(orderedItemRepository, Mockito.times(1)).save(orderedItem);
    }

    @Test
    public void saveNewShouldPassItemToSaveMethodWithoutIdIfPassedWithId() {
        ArgumentCaptor<OrderedItem> orderedItemArgumentCaptor = ArgumentCaptor.forClass(OrderedItem.class);
        OrderedItem orderedItem = new OrderedItem(1L, 123, 123, new Order(), new AvailableItem());
        orderedItemService.saveNew(orderedItem);
        Mockito.verify(orderedItemRepository).save(orderedItemArgumentCaptor.capture());
        OrderedItem orderedItemToCheck = orderedItemArgumentCaptor.getValue();
        assertThat(orderedItemToCheck.getId() == null);
    }

    @Test
    public void findByAvailableItemAndOrderShouldThrowExceptionIfAvailableItemIsNull(){
        Assertions.assertThrows(IllegalArgumentException.class, ()->orderedItemService.findByAvailableItemAndOrder(null, new Order()));
    }

    @Test
    public void findByAvailableItemAndOrderShouldThrowExceptionIfOrderIsNull(){
        Assertions.assertThrows(IllegalArgumentException.class, ()->orderedItemService.findByAvailableItemAndOrder(new AvailableItem(), null));
    }

    @Test
    public void deleteShouldCallDeleteMethodFromRepositoryWithCorrectArgument(){
        OrderedItem orderedItem = new OrderedItem(1L, 123, 123, new Order(), new AvailableItem());
        ArgumentCaptor<OrderedItem> orderedItemArgumentCaptor = ArgumentCaptor.forClass(OrderedItem.class);
        orderedItemService.delete(orderedItem);
        Mockito.verify(orderedItemRepository).delete(orderedItemArgumentCaptor.capture());
        OrderedItem orderedItemToCheck = orderedItemArgumentCaptor.getValue();
        assertThat(orderedItemToCheck.equals(orderedItem));
    }
//
//    @Sql("classpath:sql/data_with_3_available_items.sql")
//    @Test
//    public void updateMethodShouldUpdateItemNotCreateNew() {
//        User user = userService.getUserById(1);
//        CustomSet<ItemInOrderDto> itemInOrderDtoCustomSet = new CustomSet<>();
//        ItemInOrderDto itemInOrderDto = new ItemInOrderDto(1L, 1);
//        itemInOrderDtoCustomSet.add(itemInOrderDto);
//        AddToOrderDto addToOrderDto = new AddToOrderDto(itemInOrderDtoCustomSet);
//        orderService.addRequestToOrder(user.getId(), addToOrderDto);
//        AvailableItem availableItem = AvailableItemDtoConverterUtil.availableItemFromItemDtoWithId(availableItemService.getById(1L));
//        user = userService.getUserById(1);
//        Order order = user.getOrder();
//        OrderedItem orderedItem = orderedItemService.findByAvailableItemAndOrder(availableItem, order).get();
//        orderedItem.setQuantity(2);
//        orderedItemService.update(orderedItem);
//        assertThat(orderedItemService.findByAvailableItemAndOrder(availableItem, order).get().getQuantity() == 2);
//    }
//



}
