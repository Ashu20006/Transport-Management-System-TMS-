package com.ashutosh.tms.repository;

import com.ashutosh.tms.entity.Transporter;
import com.ashutosh.tms.entity.TransporterTruck;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TransporterTruckRepository extends JpaRepository<TransporterTruck, UUID> {

    Optional<TransporterTruck> findByTransporterAndTruckType(Transporter transporter, String truckType);
}
