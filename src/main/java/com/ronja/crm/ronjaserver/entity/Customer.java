package com.ronja.crm.ronjaserver.entity;

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

  @Column(name = "category")
  @Enumerated(EnumType.STRING)
  private Category category;

  @Column(name = "focus")
  @Enumerated(EnumType.STRING)
  private Focus focus;

  @Column(name = "status")
  @Enumerated(EnumType.STRING)
  private Status status;

  @Override
  public String toString() {
    return String.format("Customer[companyName=%s, category=%s, focus=%s, status=%s]",
        companyName, category, focus, status);
  }

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

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
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
