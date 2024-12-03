package com.ronja.crm.ronjaserver.client.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.Objects;

public final class MetalExchange {

    private final boolean success;
    private final int timestamp;
    private final LocalDate date;
    @JsonProperty("base")
    private final String currency;
    private final String unit;
    private final Rates rates;

    public MetalExchange(
            boolean success,
            int timestamp,
            LocalDate date,
            @JsonProperty("base") String currency,
            String unit,
            Rates rates) {
        this.success = success;
        this.timestamp = timestamp;
        this.date = date;
        this.currency = currency;
        this.unit = unit;
        this.rates = rates;
    }

    public boolean isSuccess() {
        return success;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getCurrency() {
        return currency;
    }

    public String getUnit() {
        return unit;
    }

    public Rates getRates() {
        return rates;
    }
}
