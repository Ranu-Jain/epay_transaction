package com.epay.transaction.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderStatusRequest {

    @NotNull
    private String orderid;

    @NotNull
    private String status;

}
