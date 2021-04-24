package com.ronja.crm.ronjaserver.dto;

import com.ronja.crm.ronjaserver.entity.Category;
import com.ronja.crm.ronjaserver.entity.Focus;
import com.ronja.crm.ronjaserver.entity.Status;

public class CustomerDto {
  private int id;
  private String companyName;
  private Category category;
  private Focus focus;
  private Status status;

  public CustomerDto(int id, String companyName, Category category, Focus focus, Status status) {
    this.id = id;
    this.companyName = companyName;
    this.category = category;
    this.focus = focus;
    this.status = status;
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
