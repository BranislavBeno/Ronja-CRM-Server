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

  @Column(name = "last_visit")
  private LocalDate lastVisit;

  @Column(name = "scheduled_visit")
  private LocalDate scheduledVisit;

  @Column(name = "status")
  @Enumerated(EnumType.STRING)
  private Status status;
}
