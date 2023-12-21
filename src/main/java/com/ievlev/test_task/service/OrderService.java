package com.ievlev.test_task.service;

import com.ievlev.test_task.dto.AddToOrderDto;
import com.ievlev.test_task.dto.ItemInOrderDto;
import com.ievlev.test_task.dto.OrderDto;
import com.ievlev.test_task.exceptions.AddToOrderException;
import com.ievlev.test_task.exceptions.EmptyOrderCannotBePaidException;
import com.ievlev.test_task.exceptions.OrderNotFoundException;
import com.ievlev.test_task.model.AvailableItem;
import com.ievlev.test_task.model.Order;
import com.ievlev.test_task.model.OrderedItem;
import com.ievlev.test_task.model.User;
import com.ievlev.test_task.repository.OrderRepository;
import com.ievlev.test_task.util.OrderDtoConverterUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.*;


@Slf4j
@Service
@RequiredArgsConstructor
@Validated
public class OrderService {
    private static final String CAN_T_ADD_NOTHING_TO_ORDER = "can't add nothing to order";
    private static final String ID_D_QUANTITY_D_PATTERN = "id:%d, quantity:%d ; ";
    private static final String YOU_CAN_T_PAY_FOR_EMPTY_ORDER = "you can't pay for empty order";
    private static final String ADD_TO_ORDER_DTO_MUST_NOT_BE_NULL = "AddToOrderDto must not be null";
    private static final String CAN_T_ADD_ITEMS_TO_ORDER1 = "Can't add items to order: ";
    private static final String THE_USER_DOES_NOT_HAVE_AN_ORDER = "the user does not have an order";
    private static final String ORDER_CAN_T_BE_NULL = "order can't be null";
    private static final String YOU_CAN_T_UPDATE_ITEM_WITHOUT_ID = "you can't update item without id";
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final AvailableItemService availableItemService;
    private final OrderedItemService orderedItemService;


    @Transactional
    public void addRequestToOrder(@Min(1) long userId, @NotNull AddToOrderDto addToOrderDto) {
        validateAddToOrderDto(addToOrderDto);
        User user = userService.getUserById(userId);
        Order order = user.getOrder();
        if (order != null) {
            updateOrder(order, addToOrderDto);
        } else {
            createAndAddToOrder(user, addToOrderDto);
        }
    }

    private void updateOrder(Order order, AddToOrderDto addToOrderDto) {
        addItemsToOrder(order, addToOrderDto);
    }

    private void validateAddToOrderDto(AddToOrderDto addToOrderDto) {
        if (addToOrderDto == null) {
            throw new IllegalArgumentException(ADD_TO_ORDER_DTO_MUST_NOT_BE_NULL);
        }
        if (addToOrderDto.getItemsFromOrderDto().isEmpty()) {
            throw new IllegalArgumentException(CAN_T_ADD_NOTHING_TO_ORDER);
        }
    }


    private void createAndAddToOrder(User user, AddToOrderDto addToOrderDto) {
        Order newOrder = new Order(false, new Date(), user);
        saveNew(newOrder);
        addItemsToOrder(newOrder, addToOrderDto);
    }


    public OrderDto getOrder(@Min(1) long userId) {
        Order order = userService.getUserById(userId).getOrder();
        if (order == null) {
            throw new OrderNotFoundException(THE_USER_DOES_NOT_HAVE_AN_ORDER);
        }
        return OrderDtoConverterUtil.orderDtoFromOrder(order);
    }

    private void addItemsToOrder(Order order, AddToOrderDto addToOrderDto) {
        Set<ItemInOrderDto> itemsToAdd = addToOrderDto.getItemsFromOrderDto();
        Set<String> errors = new HashSet<>();
        for (ItemInOrderDto itemInOrder : itemsToAdd) {
            Optional<AvailableItem> availableItemOptional = availableItemService.findById(itemInOrder.getAvailableItemId());
            if (availableItemOptional.isEmpty() || (availableItemOptional.get().getQuantity() < itemInOrder.getQuantity())) { //check if item that need to be checked is valid (has a valid id and quantity and present id db)
                errors.add(String.format(ID_D_QUANTITY_D_PATTERN, itemInOrder.getAvailableItemId(), itemInOrder.getQuantity()));
            }
            AvailableItem availableItem = availableItemOptional.get();
            availableItem.setQuantity(availableItem.getQuantity() - itemInOrder.getQuantity()); //setting quantity of available item minus ordered item
            Optional<OrderedItem> orderedItemOptional = orderedItemService.findByAvailableItemAndOrder(availableItem, order);// getting orderedItemOptional is done to check if we already have the same item in order and user wants to add quantity to it
            if (orderedItemOptional.isPresent()) { // if item is already in orders and user wants to add quantity
                OrderedItem orderedItem = orderedItemOptional.get();
                orderedItem.setQuantity(orderedItem.getQuantity() + itemInOrder.getQuantity()); //setting quantity of item which is already in order plus ordered quantity
            }
            if (orderedItemOptional.isEmpty()) { // if item is not in order and user wants to add a new one
                orderedItemService.saveNew(new OrderedItem(availableItem.getPrice(), itemInOrder.getQuantity(), order, availableItem));
            }
        }
        if (!errors.isEmpty()) {
            throw new AddToOrderException(CAN_T_ADD_ITEMS_TO_ORDER1 + String.join(", ", errors));
        }
    }


    private void returnItemsFromOrder(Order order) {
        List<OrderedItem> orderedItemList = order.getOrderedItemList();
        for (OrderedItem orderedItem : orderedItemList) {
            AvailableItem availableItem = orderedItem.getAvailableItem();
            availableItem.setQuantity(orderedItem.getQuantity() + availableItem.getQuantity());
            availableItemService.update(availableItem);
            orderedItemService.delete(orderedItem);
        }
    }

    public void payForOrder(@Min(1) long userId) {
        Order order = userService.getUserById(userId).getOrder();
        if (order == null) {
            throw new OrderNotFoundException(THE_USER_DOES_NOT_HAVE_AN_ORDER);
        }
        if (order.getOrderedItemList().isEmpty()) {
            throw new EmptyOrderCannotBePaidException(YOU_CAN_T_PAY_FOR_EMPTY_ORDER);
        }
        order.setPaid(true);
        orderRepository.save(order);
    }

    @Transactional
    @Scheduled(fixedRate = 60000) // 60000ms = 1 min
    public void deleteUnpaidOrders() {
        Date cutoffDateTime = new Date(System.currentTimeMillis() - 600000);
        List<Order> orderList = orderRepository.findByModificationTimeBeforeAndPaid(cutoffDateTime, false);
        for (Order order : orderList) {
            returnItemsFromOrder(order);
            orderRepository.delete(order);
        }
    }

//    public void update(@Valid Order order) {
//        validateOrderNotNull(order);
//        if (order.getId() == null) {
//            throw new UnableToUpdateEntityException(YOU_CAN_T_UPDATE_ITEM_WITHOUT_ID);
//        }
//        orderRepository.save(order);
//    }

    private void saveNew(@Valid @NotNull Order order) {
        order.setId(null);
        orderRepository.save(order);
    }

    public Order getOrderByUserId(@Min(1) long id) {
        return orderRepository.findByUserId(id);
    }

}
