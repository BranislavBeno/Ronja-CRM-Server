package com.ronja.crm.ronjaserver.dto;

import com.ronja.crm.ronjaserver.entity.Status;

public record CustomerDto(
    int id,
    String companyName,
    String category,
    String focus,
    Status status) {
}
