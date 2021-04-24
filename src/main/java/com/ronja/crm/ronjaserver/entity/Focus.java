package com.ronja.crm.ronjaserver.entity;

public enum Focus {
  BUILDER("Stavebník"),
  MANUFACTURE("Priemysel/Výroba"),
  SPECIALIZED_MANUFACTURE("Špecializovaná výroba"),
  TRADE("Obchod");

  private final String label;

  Focus(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }
}
