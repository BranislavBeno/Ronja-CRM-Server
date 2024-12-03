package com.ronja.crm.ronjaserver.entity;

public final class Contact {

    private final String contact;
    private final String type;
    private final boolean primary;

    public Contact(String contact, String type, boolean primary) {
        this.contact = contact;
        this.type = type;
        this.primary = primary;
    }

    public String getContact() {
        return contact;
    }

    public String getType() {
        return type;
    }

    public boolean isPrimary() {
        return primary;
    }
}
