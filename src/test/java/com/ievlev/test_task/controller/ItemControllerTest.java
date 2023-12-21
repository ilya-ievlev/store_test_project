package com.ievlev.test_task.controller;

import com.ievlev.test_task.initializer.IntegrationTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ItemControllerTest extends IntegrationTestBase {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getAvailableItemsIsNotAvailableForNotAuthorizedUsers() throws Exception {
        mockMvc.perform(get("/api/v1/secured/items")
                .param("pageNumber", "0")
                .param("pageSize", "100"))
                .andExpect(status().isUnauthorized());
    }

    @WithMockUser(authorities = "USER")
    @Test
    public void getAvailableItemAvailableForAuthorizedUsers() throws Exception {
        mockMvc.perform(get("/api/v1/secured/items")
                .param("pageNumber", "0")
                .param("pageSize", "100"))
                .andExpect(status().is2xxSuccessful());
    }

    @WithMockUser(authorities = "USER")
    @Test
    public void createNewItemShouldNotBeAvailableForUser() throws Exception {
        mockMvc.perform(put("/api/v1/admin/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(" {\n" +
                        "        \"id\": 78978,\n" +
                        "        \"name\": \"item1\",\n" +
                        "        \"quantity\": 3,\n" +
                        "        \"price\": 14129\n" +
                        "    }")
        ).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void createNewItemShouldBeAvailableForAdmin() throws Exception {
        mockMvc.perform(put("/api/v1/admin/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(" {\"id\": 78978, \"name\": \"item1\", \"quantity\": 3, \"price\": 14129}")
        ).andExpect(status().is2xxSuccessful());
    }

    @Test
    public void createItemShouldNotBeAvailableForNonAuthorizedUsers() throws Exception {
        mockMvc.perform(put("/api/v1/admin/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(" {\n" +
                        "        \"id\": 78978,\n" +
                        "        \"name\": \"item1\",\n" +
                        "        \"quantity\": 3,\n" +
                        "        \"price\": 14129\n" +
                        "    }")
        ).andExpect(status().isUnauthorized());
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    public void createItemShouldNotCreateItemWithWrongQuantity() throws Exception {
        mockMvc.perform(put("/api/v1/admin/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(" {\n" +
                        "        \"id\": 78978,\n" +
                        "        \"name\": \"item1\",\n" +
                        "        \"quantity\": -3,\n" +
                        "        \"price\": 14129\n" +
                        "    }")
        ).andExpect(status().isBadRequest());
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    public void createItemShouldNotCreateItemWithWrongPrice() throws Exception {
        mockMvc.perform(put("/api/v1/admin/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(" {\n" +
                        "        \"id\": 78978,\n" +
                        "        \"name\": \"item1\",\n" +
                        "        \"quantity\": 3,\n" +
                        "        \"price\": -14129\n" +
                        "    }")
        ).andExpect(status().isBadRequest());
    }


    @Test
    public void getItemByIdShouldNotBeAvailableForNonAuthorizedUsers() throws Exception {
        mockMvc.perform(get("/api/v1/secured/items/1"))
//                .param("id", "1"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    public void getItemByIdShouldBeAvailableForAuthorizedUsers() throws Exception {
        mockMvc.perform(get("/secured/get-item-by-id")
                .param("id", "1"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithMockUser
    public void getItemByIdShouldReturnErrorIfIdIsNotAvailableOrIncorrect() throws Exception {
        mockMvc.perform(get("/secured/get-item-by-id")
                .param("id", "-1"))
                .andExpect(status().is4xxClientError());
    }
}
