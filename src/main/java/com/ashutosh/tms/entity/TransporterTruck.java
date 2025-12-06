package com.ashutosh.tms.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "transporter_trucks")
public class TransporterTruck {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "transporter_id")
    private Transporter transporter;

    private String truckType;

    private int count;

    // -------- GETTERS & SETTERS --------

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public Transporter getTransporter() { return transporter; }
    public void setTransporter(Transporter transporter) { this.transporter = transporter; }

    public String getTruckType() { return truckType; }
    public void setTruckType(String truckType) { this.truckType = truckType; }

    public int getCount() { return count; }
    public void setCount(int count) { this.count = count; }
}
