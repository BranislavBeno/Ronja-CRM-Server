package com.ronja.crm.ronjaserver.entity;

import javax.persistence.*;
import java.time.LocalDate;

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

  @Column(name = "status")
  @Enumerated(EnumType.STRING)
  private Status status;

  @Column(name = "last_visit")
  private LocalDate lastVisit;

  @Column(name = "scheduled_visit")
  private LocalDate scheduledVisit;

  @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
      CascadeType.DETACH, CascadeType.REFRESH})
  @JoinColumn(name = "customer_id")
  private Customer customer;

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

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
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
}
