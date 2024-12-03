package com.ronja.crm.ronjaserver.client.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public final class Rates {

    @JsonProperty("LME-ALU")
    private final BigDecimal aluminum;
    @JsonProperty("LME-XCU")
    private final BigDecimal copper;
    @JsonProperty("LME-LEAD")
    private final BigDecimal lead;

    public Rates(
            @JsonProperty("LME-ALU") BigDecimal aluminum,
            @JsonProperty("LME-XCU") BigDecimal copper,
            @JsonProperty("LME-LEAD") BigDecimal lead) {
        this.aluminum = aluminum;
        this.copper = copper;
        this.lead = lead;
    }

    @JsonProperty("LME-ALU")
    public BigDecimal getAluminum() {
        return aluminum;
    }

    @JsonProperty("LME-XCU")
    public BigDecimal getCopper() {
        return copper;
    }

    @JsonProperty("LME-LEAD")
    public BigDecimal getLead() {
        return lead;
    }
}
