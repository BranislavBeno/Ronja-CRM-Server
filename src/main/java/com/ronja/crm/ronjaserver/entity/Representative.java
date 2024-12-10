package com.ronja.crm.ronjaserver.entity;

import com.ronja.crm.ronjaserver.validator.ContactType;
import com.ronja.crm.ronjaserver.validator.Status;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "representative")
public class Representative {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "position")
  private String position;

  @Column(name = "region")
  private String region;

  @Column(name = "notice")
  private String notice;

  @Status
  @Column(name = "status")
  private String status;

  @PastOrPresent(message = "Dátum poslednej návštevy nesmie byť novší ako dnešný.")
  @Column(name = "last_visit")
  private LocalDate lastVisit;

  @FutureOrPresent(message = "Dátum plánovanej návštevy nesmie byť starší ako dnešný.")
  @Column(name = "scheduled_visit")
  private LocalDate scheduledVisit;

  @Column(name = "phone_numbers")
  @Convert(converter = ListAttributeConverter.class)
  private List<Contact> phoneNumbers;

  @Column(name = "emails")
  @Convert(converter = ListAttributeConverter.class)
  private List<Contact> emails;

  @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
  @JoinColumn(name = "customer_id")
  private Customer customer;

  @ContactType
  @Column(name = "contact_type")
  private String contactType;

  public String getContactType() {
    return contactType;
  }

  public void setContactType(String contactType) {
    this.contactType = contactType;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getPosition() {
    return position;
  }

  public void setPosition(String position) {
    this.position = position;
  }

  public String getRegion() {
    return region;
  }

  public void setRegion(String region) {
    this.region = region;
  }

  public String getNotice() {
    return notice;
  }

  public void setNotice(String notice) {
    this.notice = notice;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public LocalDate getLastVisit() {
    return lastVisit;
  }

  public void setLastVisit(LocalDate lastVisit) {
    this.lastVisit = lastVisit;
  }

  public LocalDate getScheduledVisit() {
    return scheduledVisit;
  }

  public void setScheduledVisit(LocalDate scheduledVisit) {
    this.scheduledVisit = scheduledVisit;
  }

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  public List<Contact> getPhoneNumbers() {
    return phoneNumbers;
  }

  public void setPhoneNumbers(List<Contact> phoneNumbers) {
    this.phoneNumbers = phoneNumbers;
  }

  public List<Contact> getEmails() {
    return emails;
  }

  public void setEmails(List<Contact> emails) {
    this.emails = emails;
  }
}