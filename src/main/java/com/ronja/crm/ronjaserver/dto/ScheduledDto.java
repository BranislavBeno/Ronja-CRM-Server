package com.ronja.crm.ronjaserver.dto;

import java.time.LocalDate;

public record ScheduledDto(
        String firstName,
        String lastName,
        LocalDate scheduledVisit,
        String customerName) {
}
