package com.epay.transaction.dao;


import com.epay.transaction.dto.MerchantDto;
import com.epay.transaction.dto.OrderDto;
import com.epay.transaction.entity.Order;
import com.epay.transaction.exceptions.TransactionException;
import com.epay.transaction.externalservice.MerchantServicesClient;
import com.epay.transaction.repository.OrderRepository;
import com.epay.transaction.repository.cache.MerchantCacheRepo;
import com.epay.transaction.util.ErrorConstants;
import com.epay.transaction.util.enums.OrderStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Optional;

/**
 * Class Name: OrderDao
 * *
 * Description: OrderDao class for access order reposiotry
 * *
 * Author: V1012904(Shital suryawanshi)
 * Copyright (c) 2024 [State Bank of India]
 * All rights reserved
 * *
 * Version:1.0
 */


@Component
@RequiredArgsConstructor
public class OrderDao {
    private final MerchantCacheRepo merchantCacheRepository;
    private final MerchantServicesClient merchantServicesClient;
    private final ObjectMapper objectMapper;
    private final OrderRepository orderRepository;

    public Optional<MerchantDto> getActiveMerchantByMID(String mID) {
        Optional<MerchantDto> merchantDto = merchantCacheRepository.getActiveMerchantByMID(mID);
        if (merchantDto.isEmpty()) {
            merchantDto = Optional.ofNullable(merchantServicesClient.getMerchantByMID(mID));
        }
        return merchantDto;
    }

    public OrderDto saveOrder(OrderDto orderDto) {
        Order order = objectMapper.convertValue(orderDto, Order.class);
        order = orderRepository.save(order);
        return objectMapper.convertValue(order, OrderDto.class);
    }

    public OrderDto updateOrderStatus(String orderRefNumber, String status) {
        //TODO : Need to check status and add some more validation
        Order order = orderRepository.findByOrderRefNumber(orderRefNumber).orElseThrow(() -> new TransactionException(ErrorConstants.NOT_FOUND_ERROR_CODE, MessageFormat.format(ErrorConstants.NOT_FOUND_ERROR_MESSAGE, "Order")));
        order.setStatus(OrderStatus.valueOf(status));
        Order saveOrder = orderRepository.save(order);
        return objectMapper.convertValue(saveOrder, OrderDto.class);
    }

    public OrderDto getOrderByOrderRefNumber(String orderRefNumber) {
        Optional<Order> order = orderRepository.findByOrderRefNumber(orderRefNumber);
        return objectMapper.convertValue(order, OrderDto.class);
    }

}
