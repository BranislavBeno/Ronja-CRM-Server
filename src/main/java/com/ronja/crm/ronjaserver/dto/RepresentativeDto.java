package com.ronja.crm.ronjaserver.dto;

import com.ronja.crm.ronjaserver.entity.Status;

import java.time.LocalDate;

public class RepresentativeDto {
  private int id;
  private String firstName;
  private String lastName;
  private String position;
  private String region;
  private String notice;
  private Status status;
  private LocalDate lastVisit;
  private LocalDate scheduledVisit;

  public RepresentativeDto(int id, String firstName, String lastName, String position, String region, String notice,
                           Status status, LocalDate lastVisit, LocalDate scheduledVisit) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.position = position;
    this.region = region;
    this.notice = notice;
    this.status = status;
    this.lastVisit = lastVisit;
    this.scheduledVisit = scheduledVisit;
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
}
