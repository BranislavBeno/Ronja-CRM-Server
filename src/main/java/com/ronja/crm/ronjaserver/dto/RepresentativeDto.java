package com.ronja.crm.ronjaserver.dto;

import com.ronja.crm.ronjaserver.entity.Contact;

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
    List<Contact> phoneNumbers,
    List<Contact> emails,
    int customerId,
    String contactType) {
}
