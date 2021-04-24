package com.ronja.crm.ronjaserver.entity;

public enum Status {
  ACTIVE("Aktívny"), INACTIVE("Neaktívny");

  private final String label;

  Status(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }
}
