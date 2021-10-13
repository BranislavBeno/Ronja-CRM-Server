package com.ronja.crm.ronjaserver.dto;

import java.time.LocalDate;
import java.util.List;

public record RepresentativeDto(
    int id,
    String firstName,
    String lastName,
    String position,
    String region,
    String notice,
    String status,
    LocalDate lastVisit,
    LocalDate scheduledVisit,
    List<String> phoneNumbers,
    List<String> emails,
    int customerId,
    String contactType) {
}
