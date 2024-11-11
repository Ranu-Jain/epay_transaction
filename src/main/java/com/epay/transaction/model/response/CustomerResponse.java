package com.epay.transaction.model.response;

import lombok.Data;

import java.util.List;

/**
 * Class Name: CustomerController
 * *
 * Description: Customer creation for given Merchant.
 * *
 * Author: V1018400 (Ranjan Kumar)
 * Copyright (c) 2024 [State Bank of India]
 * All rights reserved
 * *
 * Version:1.0
 */

@Data

public class CustomerResponse {

    private List<DataItem> data;

    private int status;

    private int count;

    private int size;


    public CustomerResponse(Long id, String customerId) {

        this.data = List.of(new DataItem(id, customerId));

        this.status = 1; // Indicating success

        this.count = 1; // Number of items

        this.size = 1;  // Response size

    }


    @Data

    public static class DataItem {

        private Long id;

        private String customerId;


        public DataItem(Long id, String customerId) {

            this.id = id;

            this.customerId = customerId;

        }

    }

}

