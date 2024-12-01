package com.epay.transaction.model.request;

/*
 * Copyright (c) [2024] [State Bank of India]
 * All rights reserved.
 *
 * @author Shilpa Kothre
 */

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class TokenRequest {

    @Schema(example = "(\\\"{\"userName\":\"USERNAME\",\"password\":\"12345\")")
    String tokenRequest;

}
