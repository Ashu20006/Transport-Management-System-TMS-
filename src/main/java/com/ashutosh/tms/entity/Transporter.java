package com.ashutosh.tms.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "transporters")
public class Transporter {

    @Id
    @GeneratedValue
    @Column(name = "transporter_id")
    private UUID transporterId;

    private String companyName;

    private double rating; // 1–5

    // ---------- GETTERS & SETTERS ----------

    public UUID getTransporterId() { return transporterId; }
    public void setTransporterId(UUID transporterId) { this.transporterId = transporterId; }

    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }
}
