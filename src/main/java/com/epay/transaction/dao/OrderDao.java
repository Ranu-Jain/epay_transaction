package com.epay.transaction.dao;


import com.epay.transaction.dto.MerchantDto;
import com.epay.transaction.dto.OrderDto;
import com.epay.transaction.entity.Order;
import com.epay.transaction.util.enums.OrderStatus;
import com.epay.transaction.externalservice.MerchantServicesClient;
import com.epay.transaction.repositary.OrderRepository;
import com.epay.transaction.repositary.cache.MerchantCacheRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
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


    public OrderDto saveOrder(OrderDto customerDto) {
        Order order = objectMapper.convertValue(customerDto, Order.class);
        order = orderRepository.save(order);
        return objectMapper.convertValue(order, OrderDto.class);
    }

    public OrderDto updateOrder(OrderStatusRequest orderStatusRequest) {
        Order order = orderRepository.findById(orderStatusRequest.getOrderid()).get();
        order.setStatus(OrderStatus.valueOf(orderStatusRequest.getStatus()));
        Order saveOrder = orderRepository.save(order);
        return objectMapper.convertValue(saveOrder,OrderDto.class);
    }

    public OrderDto getOrder(String orderid) {
        Optional<Order> order = orderRepository.findById(orderid);
      return  objectMapper.convertValue(order, OrderDto.class);
    }

    public Page<Order> getOrderByPages(String mid, int pageSize, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Order> pages = orderRepository.findByMid(mid,pageable);
        return pages;
    }
    public Boolean getOrderStatus(String orderId)
    {
        return orderRepository.existsById(orderId);
    }


}
