package com.ronja.crm.ronjaserver.dto;

import com.ronja.crm.ronjaserver.entity.Status;

import java.time.LocalDate;
import java.util.List;

public record RepresentativeDto(
    int id,
    String firstName,
    String lastName,
    String position,
    String region,
    String notice,
    Status status,
    LocalDate lastVisit,
    LocalDate scheduledVisit,
    List<String> phoneNumbers,
    List<String> emails,
    CustomerDto customer) {
}
