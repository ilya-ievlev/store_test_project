package com.ievlev.test_task.controller;

import com.ievlev.test_task.initializer.IntegrationTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest extends IntegrationTestBase {

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void authShouldReturnTokenIfUserIsCorrect() throws Exception {
        mockMvc.perform(post("/api/v1/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"user\", \"password\":\"100\"}"))
                .andExpect(status().is2xxSuccessful());
    }


    @Test
    public void authShouldNotReturnTokenIfCredentialsIsIncorrect() throws Exception {
        mockMvc.perform(post("/api/v1/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"user\", \"password\":\"1001\"}"))
                .andExpect(status().isUnauthorized());
    }
}
