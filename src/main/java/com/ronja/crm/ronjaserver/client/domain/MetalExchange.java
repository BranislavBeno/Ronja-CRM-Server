package com.ronja.crm.ronjaserver.client.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public record MetalExchange(
    boolean success,
    int timestamp,
    LocalDate date,
    @JsonProperty("base") String currency,
    String unit,
    Rates rates) {
}
