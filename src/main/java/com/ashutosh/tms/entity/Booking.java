package com.ashutosh.tms.entity;

import com.ashutosh.tms.entity.enums.BookingStatus;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue
    @Column(name = "booking_id")
    private UUID bookingId;

    @ManyToOne
    @JoinColumn(name = "load_id")
    private Load load;

    @ManyToOne
    @JoinColumn(name = "bid_id")
    private Bid bid;

    @ManyToOne
    @JoinColumn(name = "transporter_id")
    private Transporter transporter;

    private int allocatedTrucks;

    private double finalRate;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    private Instant bookedAt;

    // ---------- GETTERS & SETTERS ----------

    public UUID getBookingId() { return bookingId; }
    public void setBookingId(UUID bookingId) { this.bookingId = bookingId; }

    public Load getLoad() { return load; }
    public void setLoad(Load load) { this.load = load; }

    public Bid getBid() { return bid; }
    public void setBid(Bid bid) { this.bid = bid; }

    public Transporter getTransporter() { return transporter; }
    public void setTransporter(Transporter transporter) { this.transporter = transporter; }

    public int getAllocatedTrucks() { return allocatedTrucks; }
    public void setAllocatedTrucks(int allocatedTrucks) { this.allocatedTrucks = allocatedTrucks; }

    public double getFinalRate() { return finalRate; }
    public void setFinalRate(double finalRate) { this.finalRate = finalRate; }

    public BookingStatus getStatus() { return status; }
    public void setStatus(BookingStatus status) { this.status = status; }

    public Instant getBookedAt() { return bookedAt; }
    public void setBookedAt(Instant bookedAt) { this.bookedAt = bookedAt; }
}
