package com.ronja.crm.ronjaserver.dto;

public record CustomerDto(
    int id,
    String companyName,
    String category,
    String focus,
    String status) {
}
