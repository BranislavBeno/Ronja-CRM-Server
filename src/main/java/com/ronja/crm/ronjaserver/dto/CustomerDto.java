package com.ronja.crm.ronjaserver.dto;

import com.ronja.crm.ronjaserver.entity.Category;
import com.ronja.crm.ronjaserver.entity.Focus;
import com.ronja.crm.ronjaserver.entity.Status;

public record CustomerDto(
    int id,
    String companyName,
    Category category,
    Focus focus,
    Status status) {
}
