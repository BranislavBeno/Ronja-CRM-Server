package com.ronja.crm.ronjaserver.dto;

import com.ronja.crm.ronjaserver.client.domain.MetalExchange;
import com.ronja.crm.ronjaserver.entity.MetalData;

import java.math.BigDecimal;
import java.time.LocalDate;

public class MetalDataMapper {

  public MetalDataDto toDto(MetalData metalData) {
    int id = metalData.getId();
    LocalDate fetched = metalData.getFetched();
    String currency = metalData.getCurrency();
    BigDecimal aluminum = metalData.getAluminum();
    BigDecimal copper = metalData.getCopper();
    BigDecimal lead = metalData.getLead();

    return new MetalDataDto(id, fetched, currency, aluminum, copper, lead);
  }

  public MetalData toEntity(MetalExchange exchange) {
    var metalData = new MetalData();
    metalData.setFetched(exchange.date());
    metalData.setCurrency(exchange.currency());
    metalData.setAluminum(exchange.rates().aluminum());
    metalData.setCopper(exchange.rates().copper());
    metalData.setLead(exchange.rates().lead());

    return metalData;
  }
}
