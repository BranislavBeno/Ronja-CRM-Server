package com.ronja.crm.ronjaserver.entity;

public class Customer {

  private String firstName;
  private String lastName;
  private String companyName;

  public Customer(String firstName, String lastName, String companyName) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.companyName = companyName;
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

  @Override
  public String toString() {
    return "Customer[" +
        "firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", companyName='" + companyName + '\'' +
        ']';
  }
}
