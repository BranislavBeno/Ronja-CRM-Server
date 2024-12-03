package com.ronja.crm.ronjaserver.dto;

import com.ronja.crm.ronjaserver.entity.Contact;
import com.ronja.crm.ronjaserver.entity.Customer;
import com.ronja.crm.ronjaserver.entity.Representative;

import java.time.LocalDate;
import java.util.List;

public class RepresentativeMapper {

  public RepresentativeDto toDto(Representative representative) {
    int id = representative.getId();
    String firstName = representative.getFirstName();
    String lastName = representative.getLastName();
    String region = representative.getRegion();
    String notice = representative.getNotice();
    String position = representative.getPosition();
    String status = representative.getStatus();
    LocalDate lastVisit = representative.getLastVisit();
    LocalDate scheduledVisit = representative.getScheduledVisit();
    List<Contact> phoneNumbers = representative.getPhoneNumbers();
    List<Contact> emails = representative.getEmails();
    int customerId = representative.getCustomer() != null
        ? representative.getCustomer().getId()
        : 0;
    String contactType = representative.getContactType();

    return new RepresentativeDto(id, firstName, lastName, position, region, notice, status, lastVisit, scheduledVisit,
        phoneNumbers, emails, customerId, contactType);
  }

  public Representative toEntity(RepresentativeDto dto, Customer customer) {
    var representative = new Representative();
    representative.setId(dto.getId());
    representative.setFirstName(dto.getFirstName());
    representative.setLastName(dto.getLastName());
    representative.setStatus(dto.getStatus());
    representative.setRegion(dto.getRegion());
    representative.setNotice(dto.getNotice());
    representative.setPosition(dto.getPosition());
    representative.setLastVisit(dto.getLastVisit());
    representative.setScheduledVisit(dto.getScheduledVisit());
    representative.setPhoneNumbers(dto.getPhoneNumbers());
    representative.setEmails(dto.getEmails());
    representative.setCustomer(customer);
    representative.setContactType(dto.getContactType());

    return representative;
  }
}
