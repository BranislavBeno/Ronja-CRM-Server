package com.ronja.crm.ronjaserver.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

  @Override
  public String toString() {
    record Field(String key, String val) {
      @Override
      public String toString() {
        return String.format("%s=%s", key, val);
      }
    }
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    return String.format("Representative[%s]", Stream.of(
        new Field("firstName", firstName),
        new Field("lastName", lastName),
        new Field("position", position),
        new Field("region", region),
        new Field("notice", notice),
        new Field("status", status.getLabel()),
        new Field("lastVisit", lastVisit.format(formatter)),
        new Field("scheduledVisit", scheduledVisit.format(formatter)),
        new Field("companyName", Optional.ofNullable(customer.getCompanyName()).orElse("")))
        .map(Field::toString)
        .collect(Collectors.joining(", ")));
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
