package com.ronja.crm.ronjaserver.dto;

public class CustomerDto {
  private int id;
  private String firstName;
  private String lastName;
  private String companyName;

  public CustomerDto(int id, String firstName, String lastName, String companyName) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.companyName = companyName;
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

  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }
}
