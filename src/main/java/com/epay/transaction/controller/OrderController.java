package com.epay.transaction.controller;

import com.epay.transaction.dto.OrderDto;
import com.epay.transaction.model.request.OrderRequest;
import com.epay.transaction.model.response.OrderResponse;
import com.epay.transaction.model.response.TransactionResponse;
import com.epay.transaction.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Class Name: OrderController
 * *
 * Description: Customer creation order request for given Merchant.
 * *
 * Author: V1012904(Shital suryawanshi)
 * Copyright (c) 2024 [State Bank of India]
 * All rights reserved
 * *
 * Version:1.0
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
@Validated
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public TransactionResponse<String> createOrder(@Valid @RequestBody OrderRequest orderRequest) {
        return orderService.createOrder(orderRequest);

    }

    @GetMapping("/{orderNumber}")
    public TransactionResponse<OrderDto> getOrderByOrderNumber(@PathVariable("orderNumber") String orderNumber) {
        return orderService.getOrderById(orderNumber);
    }


    @PostMapping("/{orderNumber}/{status}")
    public TransactionResponse<OrderResponse> updateOrderStatus(@PathVariable("orderNumber") String orderNumber, @PathVariable("status") String status) {
        return orderService.updateOrderStatus(orderStatusRequest);
    }

}