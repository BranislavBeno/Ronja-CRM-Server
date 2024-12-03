package com.ronja.crm.ronjaserver.dto;

public final class CustomerDto {

    private final int id;
    private final String companyName;
    private final String category;
    private final String focus;
    private final String status;

    public CustomerDto(
            int id,
            String companyName,
            String category,
            String focus,
            String status) {
        this.id = id;
        this.companyName = companyName;
        this.category = category;
        this.focus = focus;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getCategory() {
        return category;
    }

    public String getFocus() {
        return focus;
    }

    public String getStatus() {
        return status;
    }
}
