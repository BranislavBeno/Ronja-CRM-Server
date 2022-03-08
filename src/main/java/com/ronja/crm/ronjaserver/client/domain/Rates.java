package com.ronja.crm.ronjaserver.client.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record Rates(
    @JsonProperty("LME-ALU") BigDecimal aluminum,
    @JsonProperty("LME-XCU") BigDecimal copper,
    @JsonProperty("LME-LEAD") BigDecimal lead) {
}
