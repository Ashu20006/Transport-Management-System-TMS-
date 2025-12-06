package com.ashutosh.tms.repository;

import com.ashutosh.tms.entity.Booking;
import com.ashutosh.tms.entity.Load;
import com.ashutosh.tms.entity.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface BookingRepository extends JpaRepository<Booking, UUID> {

    @Query("SELECT COALESCE(SUM(b.allocatedTrucks), 0) FROM Booking b " +
            "WHERE b.load = :load AND b.status = com.ashutosh.tms.entity.enums.BookingStatus.CONFIRMED")
    int sumAllocatedTrucksForLoad(Load load);
}
