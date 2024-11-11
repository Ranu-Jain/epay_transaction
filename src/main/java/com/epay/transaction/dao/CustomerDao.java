package com.epay.transaction.dao;

import com.epay.transaction.dto.CustomerDto;
import com.epay.transaction.dto.MerchantDto;
import com.epay.transaction.entity.Customer;
import com.epay.transaction.externalservice.MerchantServicesClient;
import com.epay.transaction.repositary.CustomerRepository;
import com.epay.transaction.repositary.cache.MerchantCacheRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;


/**
 * Class Name:CustomerDao
 * *
 * Description:
 * *
 * Author:V1014352(Ranjan Kumar)
 * <p>
 * Copyright (c) 2024 [State Bank of INdia]
 * All right reserved
 * *
 * Version:1.0
 */
@Component
@RequiredArgsConstructor
public class CustomerDao {

    private final CustomerRepository customerRepository;
    private final MerchantCacheRepository merchantCacheRepository;
    private final ObjectMapper objectMapper;
    private final MerchantServicesClient merchantServicesClient;

    public Optional<MerchantDto> getActiveMerchantByMID(String mID) {
        Optional<MerchantDto> merchantDto = merchantCacheRepository.getActiveMerchantByMID(mID);
        if (merchantDto.isEmpty()) {
            merchantDto = Optional.ofNullable(merchantServicesClient.getMerchantByMID(mID));
        }
        return merchantDto;
    }

    public Optional<CustomerDto> findCustomerByEmailPhoneAndMerchant(String email, String phone) {
        Optional<Customer> customer = customerRepository.findByEmailAndPhoneNumber(email, phone);
        return customer.map(value -> objectMapper.convertValue(value, CustomerDto.class));
    }

    public CustomerDto saveCustomer(CustomerDto customerDto) {
        Customer customer = objectMapper.convertValue(customerDto, Customer.class);
        customer = customerRepository.save(customer);
        return objectMapper.convertValue(customer, CustomerDto.class);
    }

    public Optional<CustomerDto> getCustomerByCustomerId(String customerId) {
        Optional<Customer> customer = customerRepository.findByCustomerId(customerId);
        return customer.map(value -> objectMapper.convertValue(value, CustomerDto.class));
    }
}






