package com.ashutosh.tms.entity;

import com.ashutosh.tms.entity.enums.BidStatus;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "bids")
public class Bid {

    @Id
    @GeneratedValue
    @Column(name = "bid_id")
    private UUID bidId;

    @ManyToOne
    @JoinColumn(name = "load_id")
    private Load load;

    @ManyToOne
    @JoinColumn(name = "transporter_id")
    private Transporter transporter;

    private double proposedRate;

    private int trucksOffered;

    @Enumerated(EnumType.STRING)
    private BidStatus status;

    private Instant submittedAt;

    // ---------- GETTERS & SETTERS ----------

    public UUID getBidId() { return bidId; }
    public void setBidId(UUID bidId) { this.bidId = bidId; }

    public Load getLoad() { return load; }
    public void setLoad(Load load) { this.load = load; }

    public Transporter getTransporter() { return transporter; }
    public void setTransporter(Transporter transporter) { this.transporter = transporter; }

    public double getProposedRate() { return proposedRate; }
    public void setProposedRate(double proposedRate) { this.proposedRate = proposedRate; }

    public int getTrucksOffered() { return trucksOffered; }
    public void setTrucksOffered(int trucksOffered) { this.trucksOffered = trucksOffered; }

    public BidStatus getStatus() { return status; }
    public void setStatus(BidStatus status) { this.status = status; }

    public Instant getSubmittedAt() { return submittedAt; }
    public void setSubmittedAt(Instant submittedAt) { this.submittedAt = submittedAt; }
}
