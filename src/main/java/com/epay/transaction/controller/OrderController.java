package com.epay.transaction.controller;



import com.epay.transaction.dto.OrderDto;
import com.epay.transaction.entity.Order;
import com.epay.transaction.model.request.OrderStatusRequest;
import com.epay.transaction.model.response.OrderResponse;
import com.epay.transaction.model.response.TransactionResponse;
import com.epay.transaction.service.OrderService;
import com.epay.transaction.util.OrderDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
public class OrderController {

    private final OrderService OrderService;

    @PostMapping("/order/createorder")
    public TransactionResponse<String> createOrder(@RequestBody OrderDetail orderRequest, @RequestHeader (name="Authorization") String token) {
        return OrderService.createOrder(orderRequest,token);

    }

    @GetMapping("/order/{orderNumber}")
    public TransactionResponse<OrderDto> getOrder(@PathVariable("orderNumber") String orderNumber) {
             return OrderService.getOrderById(orderNumber);
         }

    @GetMapping("/orders/{mid}/{pageSize}/{pageNumber}")
    public TransactionResponse<Page<Order>> getAllOrder(@PathVariable("mid") String mid,@PathVariable("pageSize") int pageSize, @PathVariable("pageNumber") int pageNumber)
    {
        return OrderService.getOrderByPages(mid,pageSize,pageNumber);
    }


    @PostMapping("/order/updateOrder")
    public TransactionResponse<OrderResponse> updateOrderStatus(@RequestBody OrderStatusRequest orderStatusRequest) {
             return OrderService.updateOrderStatus(orderStatusRequest);

    }

    @GetMapping("/order/validatid/{orderNumber}")
    public TransactionResponse<Boolean> validateOrderid(@PathVariable("orderNumber") String orderNumber) {
          return OrderService.validateOrderid(orderNumber);

    }


}