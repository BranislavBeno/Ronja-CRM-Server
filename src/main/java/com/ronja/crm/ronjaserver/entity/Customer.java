package com.ronja.crm.ronjaserver.entity;

import com.ronja.crm.ronjaserver.validator.Category;

import javax.persistence.*;

@Entity
@Table(name = "customer")
public class Customer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;

  @Column(name = "company_name")
  private String companyName;

  @Category
  @Column(name = "category")
  private String category;

  @Column(name = "focus")
  @Enumerated(EnumType.STRING)
  private Focus focus;

  @Column(name = "status")
  @Enumerated(EnumType.STRING)
  private Status status;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public Focus getFocus() {
    return focus;
  }

  public void setFocus(Focus focus) {
    this.focus = focus;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }
}
