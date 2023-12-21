package com.ievlev.test_task.controller;

import com.ievlev.test_task.dto.AddToOrderDto;
import com.ievlev.test_task.dto.OrderDto;
import com.ievlev.test_task.service.OrderService;
import com.ievlev.test_task.util.UserIdFromAuthenticationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/api/v1/secured/pay")
    public void payForOrder(Authentication authentication) {
        long userId = UserIdFromAuthenticationUtil.getUserIdFromAuthentication(authentication);
        orderService.payForOrder(userId);
    }

    @PostMapping("/api/v1/secured/add-to-order")
    public void addToOrder(Authentication authentication, @RequestBody @Valid AddToOrderDto addToOrderDto) {
        long userId = UserIdFromAuthenticationUtil.getUserIdFromAuthentication(authentication);
        orderService.addRequestToOrder(userId, addToOrderDto);
    }

    @GetMapping("/api/v1/secured/get-order")
    public OrderDto getOrder(Authentication authentication) {
        long userId = UserIdFromAuthenticationUtil.getUserIdFromAuthentication(authentication);
        return orderService.getOrder(userId);
    }
}
