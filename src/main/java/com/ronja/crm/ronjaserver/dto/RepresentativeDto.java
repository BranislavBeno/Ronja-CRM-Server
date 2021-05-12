package com.ronja.crm.ronjaserver.dto;

import com.ronja.crm.ronjaserver.entity.Status;

import java.time.LocalDate;

public record RepresentativeDto(
    int id, String firstName, String lastName, String position, String region, String notice, Status status,
    LocalDate lastVisit, LocalDate scheduledVisit) {
}
