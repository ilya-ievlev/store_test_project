package com.ievlev.test_task.service;

import com.ievlev.test_task.custom_collection.CustomSet;
import com.ievlev.test_task.dto.AddToOrderDto;
import com.ievlev.test_task.dto.ItemInOrderDto;
import com.ievlev.test_task.exceptions.OrderNotFoundException;
import com.ievlev.test_task.model.AvailableItem;
import com.ievlev.test_task.model.Order;
import com.ievlev.test_task.model.Role;
import com.ievlev.test_task.model.User;
import com.ievlev.test_task.util.AvailableItemDtoConverterUtil;
import org.awaitility.Duration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.jdbc.Sql;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

//@Transactional
//@Testcontainers
//@SpringBootTest
//public class OrderServiceTest extends IntegrationTestBase {
@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    @InjectMocks
    private OrderService orderService;
    @Mock
    private UserService userService;
    @Mock
    private AvailableItemService availableItemService;
    @Mock
    private OrderedItemService orderedItemService;

//    @Autowired
//    public OrderServiceTest(OrderService orderService, UserService userService,
//                            AvailableItemService availableItemService, OrderedItemService orderedItemService) {
//        this.orderService = orderService;
//        this.userService = userService;
//        this.availableItemService = availableItemService;
//        this.orderedItemService = orderedItemService;
//    }


    @Test
    public void addRequestToOrderShouldUpdateOrderIfOrderExists() {
        Role role = new Role(1L, "ROLE_USER");
        User user = new User("user1", "password", List.of(role));
        Order order = new Order(false, new Date(), user);
        user.setOrder(order);
        Mockito.doReturn(user).when(userService).getUserById(1);

        ItemInOrderDto itemInOrderDto = new ItemInOrderDto(1L, 1);
        CustomSet<ItemInOrderDto> itemInOrderDtoCustomSet = new CustomSet<>();
        itemInOrderDtoCustomSet.add(itemInOrderDto);
        AddToOrderDto addToOrderDto = new AddToOrderDto(itemInOrderDtoCustomSet);
        orderService.addRequestToOrder(1, addToOrderDto);

//        Mockito.verify(orderService, Mockito.never()).
    }

    @Test
    public void addRequestToOrderShouldCreateNewOrderIfOrderNotExists() {

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

    @Sql("classpath:sql/data_with_2_available_items.sql")
    @Test
    public void addRequestToOrderShouldCreateNewOrderForUserIfNotCreatedBefore() {
        CustomSet<ItemInOrderDto> itemInOrderDtoCustomSet = new CustomSet<>();
        ItemInOrderDto itemInOrderDto = new ItemInOrderDto(1L, 1);
        itemInOrderDtoCustomSet.add(itemInOrderDto);
        AddToOrderDto addToOrderDto = new AddToOrderDto(itemInOrderDtoCustomSet);
        orderService.addRequestToOrder(1, addToOrderDto);
        assertThat(userService.getUserById(1).getOrder() != null);
    }

    @Sql("classpath:sql/data_with_2_available_items.sql")
    @Test
    public void addRequestToOrderShouldAddRequestWhenUserAlreadyHaveEqualItemsInOrder() {
        CustomSet<ItemInOrderDto> itemInOrderDtoCustomSet = new CustomSet<>();
        ItemInOrderDto itemInOrderDto = new ItemInOrderDto(1L, 1);
        itemInOrderDtoCustomSet.add(itemInOrderDto);
        AddToOrderDto addToOrderDto = new AddToOrderDto(itemInOrderDtoCustomSet);
        orderService.addRequestToOrder(1, addToOrderDto);
        AvailableItem availableItem = AvailableItemDtoConverterUtil.availableItemFromItemDtoWithId(availableItemService.getById(1));
        Order order = orderService.getOrderByUserId(1L);
        assertThat(orderedItemService.findByAvailableItemAndOrder(availableItem, order).get().getQuantity() == 2);
    }


    @Test
    public void getOrderShouldThrowExceptionIfUserDoesntHaveOrder() {
        Assertions.assertThrows(OrderNotFoundException.class, () -> orderService.getOrder(1L));
    }

    @Test
    public void getOrderShouldReturnCorrectOrder() {
        CustomSet<ItemInOrderDto> itemInOrderDtoCustomSet = new CustomSet<>();
        ItemInOrderDto itemInOrderDto = new ItemInOrderDto(1L, 1);
        itemInOrderDtoCustomSet.add(itemInOrderDto);
        AddToOrderDto addToOrderDto = new AddToOrderDto(itemInOrderDtoCustomSet);
        orderService.addRequestToOrder(1, addToOrderDto);
        assertThat(orderService.getOrder(1).getOrderedItemList().get(0).equals(itemInOrderDto));
    }

    @Test
    public void payForOrderShouldNotBeAvailableIfUserDoesntHaveOrder() {
        Assertions.assertThrows(OrderNotFoundException.class, () -> orderService.payForOrder(1));
    }

    @Sql("classpath:sql/data_with_3_available_items.sql")
    @Test
    public void payForOrderShouldSetOrderPayed() {
        CustomSet<ItemInOrderDto> itemInOrderDtoCustomSet = new CustomSet<>();
        ItemInOrderDto itemInOrderDto = new ItemInOrderDto(1L, 1);
        itemInOrderDtoCustomSet.add(itemInOrderDto);
        AddToOrderDto addToOrderDto = new AddToOrderDto(itemInOrderDtoCustomSet);
        orderService.addRequestToOrder(1, addToOrderDto);
        orderService.payForOrder(1);
        Order order = orderService.getOrderByUserId(1);
        assertThat(orderService.getOrder(1).isPaid());
    }


}
