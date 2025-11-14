package com.ronja.crm.ronjaserver.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "metal_data")
public class MetalData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "fetched")
    private LocalDate fetched;

    @com.ronja.crm.ronjaserver.validator.Currency
    @Column(name = "currency")
    private String currency;

    @Column(name = "lme_aluminium")
    private BigDecimal aluminum;

    @Column(name = "lme_copper")
    private BigDecimal copper;

    @Column(name = "lme_lead")
    private BigDecimal lead;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getFetched() {
        return fetched;
    }

    public void setFetched(LocalDate fetched) {
        this.fetched = fetched;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getAluminum() {
        return aluminum;
    }

    public void setAluminum(BigDecimal aluminum) {
        this.aluminum = aluminum;
    }

    public BigDecimal getCopper() {
        return copper;
    }

    public void setCopper(BigDecimal copper) {
        this.copper = copper;
    }

    public BigDecimal getLead() {
        return lead;
    }

    public void setLead(BigDecimal lead) {
        this.lead = lead;
    }
}
