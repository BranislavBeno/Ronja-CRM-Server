package com.ronja.crm.ronjaserver.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public final class MetalDataDto {

    private final int id;
    private final LocalDate fetched;
    private final String currency;
    private final BigDecimal aluminum;
    private final BigDecimal copper;
    private final BigDecimal lead;

    public MetalDataDto(
            int id,
            LocalDate fetched,
            String currency,
            BigDecimal aluminum,
            BigDecimal copper,
            BigDecimal lead) {
        this.id = id;
        this.fetched = fetched;
        this.currency = currency;
        this.aluminum = aluminum;
        this.copper = copper;
        this.lead = lead;
    }

    public int getId() {
        return id;
    }

    public LocalDate getFetched() {
        return fetched;
    }

    public String getCurrency() {
        return currency;
    }

    public BigDecimal getAluminum() {
        return aluminum;
    }

    public BigDecimal getCopper() {
        return copper;
    }

    public BigDecimal getLead() {
        return lead;
    }
}
