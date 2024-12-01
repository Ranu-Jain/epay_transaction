package com.epay.transaction.util;

/*
 * Copyright (c) [2024] [State Bank of India]
 * All rights reserved.
 *
 * @author Shilpa Kothre
 */

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDto<T> {
    private Integer status;
    private List<T> data;
    private Long count;
    private Long total;
    private List<ErrorDto> errors;

    @Override
    public String toString() {
        return "ResponseDto{" +
                "status=" + status +
                ", data=" + data +
                ", count=" + count +
                ", total=" + total +
                ", errors=" + errors +
                '}';
    }
}