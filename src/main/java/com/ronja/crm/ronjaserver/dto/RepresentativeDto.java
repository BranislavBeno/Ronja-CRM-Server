package com.ronja.crm.ronjaserver.dto;

import com.ronja.crm.ronjaserver.entity.Contact;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public final class RepresentativeDto {

    private final int id;
    private final String firstName;
    private final String lastName;
    private final String position;
    private final String region;
    private final String notice;
    private final String status;
    private final LocalDate lastVisit;
    private final LocalDate scheduledVisit;
    private final List<Contact> phoneNumbers;
    private final List<Contact> emails;
    private final int customerId;
    private final String contactType;

    public RepresentativeDto(
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
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.region = region;
        this.notice = notice;
        this.status = status;
        this.lastVisit = lastVisit;
        this.scheduledVisit = scheduledVisit;
        this.phoneNumbers = phoneNumbers;
        this.emails = emails;
        this.customerId = customerId;
        this.contactType = contactType;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPosition() {
        return position;
    }

    public String getRegion() {
        return region;
    }

    public String getNotice() {
        return notice;
    }

    public String getStatus() {
        return status;
    }

    public LocalDate getLastVisit() {
        return lastVisit;
    }

    public LocalDate getScheduledVisit() {
        return scheduledVisit;
    }

    public List<Contact> getPhoneNumbers() {
        return phoneNumbers;
    }

    public List<Contact> getEmails() {
        return emails;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getContactType() {
        return contactType;
    }
}
