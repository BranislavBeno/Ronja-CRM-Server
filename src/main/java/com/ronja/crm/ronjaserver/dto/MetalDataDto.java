package com.ronja.crm.ronjaserver.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record MetalDataDto(
    int id,
    LocalDate fetched,
    String currency,
    BigDecimal aluminum,
    BigDecimal copper,
    BigDecimal lead) {
}
