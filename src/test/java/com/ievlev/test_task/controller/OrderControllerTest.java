package com.ievlev.test_task.controller;

import com.ievlev.test_task.custom_collection.CustomSet;
import com.ievlev.test_task.dto.AddToOrderDto;
import com.ievlev.test_task.dto.CustomUserDetailsDto;
import com.ievlev.test_task.dto.ItemInOrderDto;
import com.ievlev.test_task.initializer.IntegrationTestBase;
import com.ievlev.test_task.model.User;
import com.ievlev.test_task.service.OrderService;
import com.ievlev.test_task.service.UserService;
import com.ievlev.test_task.util.JwtTokenUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest extends IntegrationTestBase {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void unauthorizedUserShouldNotBeAbleToPayForOrder() throws Exception {
        mockMvc.perform(post("/secured/pay")
        ).andExpect(status().isUnauthorized());
    }

    @Test
    public void authorizedUserWithoutOrderCantPayForOrder() throws Exception {
        CustomUserDetailsDto userDetailsDto = userService.loadUserByUsername("admin");
        String token = jwtTokenUtils.generateToken(userDetailsDto);
        mockMvc.perform(post("/secured/pay")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isBadRequest());
    }

    @Sql("classpath:sql/data.sql")
    @Test
    public void authorizedUserWithOrderCanPayForOrder() throws Exception {
        CustomUserDetailsDto userDetailsDto = userService.loadUserByUsername("admin");
        String token = jwtTokenUtils.generateToken(userDetailsDto);
        User user = userService.getByUsername("admin");
        ItemInOrderDto itemInOrderDto = new ItemInOrderDto(1L, 1);
        CustomSet<ItemInOrderDto> itemInOrderDtoSet = new CustomSet<>();
        itemInOrderDtoSet.add(itemInOrderDto);
        AddToOrderDto addToOrderDto = new AddToOrderDto(itemInOrderDtoSet);
        orderService.addRequestToOrder(user.getId(), addToOrderDto);
        mockMvc.perform(post("/secured/pay")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void unauthorizedUserCantGetOrder() throws Exception {
        mockMvc.perform(post("/secured/pay"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void authorizedUserWithoutOrderCantGetOrder() throws Exception {
        CustomUserDetailsDto userDetailsDto = userService.loadUserByUsername("admin");
        String token = jwtTokenUtils.generateToken(userDetailsDto);
        mockMvc.perform(get("/secured/get-order")
                .header("Authorization", "Bearer " + token)
        ).andExpect(status().isBadRequest());
    }

    @Sql("classpath:sql/data.sql")
    @Test
    public void authorizedUserWithOrderCanGetOrder() throws Exception {
        CustomUserDetailsDto userDetailsDto = userService.loadUserByUsername("admin");
        String token = jwtTokenUtils.generateToken(userDetailsDto);
        User user = userService.getByUsername("admin");
        ItemInOrderDto itemInOrderDto = new ItemInOrderDto(1L, 1);
        CustomSet<ItemInOrderDto> itemInOrderDtoSet = new CustomSet<>();
        itemInOrderDtoSet.add(itemInOrderDto);
        AddToOrderDto addToOrderDto = new AddToOrderDto(itemInOrderDtoSet);
        orderService.addRequestToOrder(user.getId(), addToOrderDto);
        mockMvc.perform(get("/secured/get-order")
                .header("Authorization", "Bearer " + token)
        ).andExpect(status().is2xxSuccessful());
    }


    @Test
    public void unauthorizedUserCantAddToOrder() throws Exception {
        mockMvc.perform(post("/secured/add-to-order"))
                .andExpect(status().isUnauthorized());
    }

    @Sql("classpath:sql/data_with_2_available_items.sql")
    @Test
    public void authorizedUserCanAddToOrderIfItemAlreadyInOrder() throws Exception {
        CustomUserDetailsDto userDetailsDto = userService.loadUserByUsername("admin");
        String token = jwtTokenUtils.generateToken(userDetailsDto);
        User user = userService.getByUsername("admin");
        ItemInOrderDto itemInOrderDto = new ItemInOrderDto(1L, 1);
        CustomSet<ItemInOrderDto> itemInOrderDtoSet = new CustomSet<>();
        itemInOrderDtoSet.add(itemInOrderDto);
        AddToOrderDto addToOrderDto = new AddToOrderDto(itemInOrderDtoSet);
        orderService.addRequestToOrder(user.getId(), addToOrderDto);
        mockMvc.perform(post("/secured/add-to-order")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON).content(" {\n" +
                        "  \"itemsFromOrderDto\": [\n" +
                        "    {\n" +
                        "      \"availableItemId\": 1,\n" +
                        "      \"quantity\": 1 \n" +
                        "    }\n" +
                        "  ]\n" +
                        "}"))
                .andExpect(status().is2xxSuccessful());
    }

    @Sql("classpath:sql/data_with_2_available_items.sql")
    @Test
    public void authorizedUserCantPutTwoIdenticalItemsAtTheSameTime() throws Exception {
        CustomUserDetailsDto userDetailsDto = userService.loadUserByUsername("admin");
        String token = jwtTokenUtils.generateToken(userDetailsDto);
        mockMvc.perform(post("/secured/add-to-order")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON).content("  {\n" +
                        "  \"itemsFromOrderDto\": [\n" +
                        "    {\n" +
                        "      \"availableItemId\": 1,\n" +
                        "      \"quantity\": 1 \n" +
                        "    },\n" +
                        "    {\n" +
                        "        \"availableItemId\": 1,\n" +
                        "      \"quantity\": 1 \n" +
                        "    }\n" +
                        "  ]\n" +
                        "}"))
                .andExpect(status().isBadRequest());
    }

    @Sql("classpath:sql/data_with_2_available_items.sql")
    @Test
    public void userCanAddItemsToOrderIfOrderWasEmptyOrNonExisted() throws Exception {
        CustomUserDetailsDto userDetailsDto = userService.loadUserByUsername("admin");
        String token = jwtTokenUtils.generateToken(userDetailsDto);
        mockMvc.perform(post("/secured/add-to-order")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON).content(" {\n" +
                        "  \"itemsFromOrderDto\": [\n" +
                        "    {\n" +
                        "      \"availableItemId\": 1,\n" +
                        "      \"quantity\": 1 \n" +
                        "    }\n" +
                        "  ]\n" +
                        "}"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void userCantGetItemIfTokenIsDamagedOrNotCorrect() throws Exception {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX0FETUlOIl0sInN1YiI6ImFkbSIsImlhdCI6MTY5OTMzNDE5NCw" +
                "iZXhwIjoxNjk5MzM0Nzk0LCJqdGkiOiIyIn0.VCn8Poup-nSzAhhC6s30nr4xqNeBQt5YJby6I9q7q8g"; // damaged token (wrong data)
        mockMvc.perform(get("/secured/get-item-by-id").header("Authorization", "Bearer " + token))
                .andExpect(status().isUnauthorized());
    }
}
