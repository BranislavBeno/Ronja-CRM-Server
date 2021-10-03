package com.ronja.crm.ronjaserver.dto;

import com.ronja.crm.ronjaserver.entity.Customer;
import com.ronja.crm.ronjaserver.entity.Representative;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
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
    List<String> phoneNumbers = representative.getPhoneNumbers();
    List<String> emails = representative.getEmails();
    int customerId = representative.getCustomer() != null
        ? representative.getCustomer().getId()
        : 0;

    return new RepresentativeDto(id, firstName, lastName, position, region, notice, status, lastVisit, scheduledVisit,
        phoneNumbers, emails, customerId);
  }

  public Representative toEntity(RepresentativeDto dto, Customer customer) {
    var representative = new Representative();
    representative.setId(dto.id());
    representative.setFirstName(dto.firstName());
    representative.setLastName(dto.lastName());
    representative.setStatus(dto.status());
    representative.setRegion(dto.region());
    representative.setNotice(dto.notice());
    representative.setPosition(dto.position());
    representative.setLastVisit(dto.lastVisit());
    representative.setScheduledVisit(dto.scheduledVisit());
    representative.setPhoneNumbers(dto.phoneNumbers());
    representative.setEmails(dto.emails());
    representative.setCustomer(customer);

    return representative;
  }
}
