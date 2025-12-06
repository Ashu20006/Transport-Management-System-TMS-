package com.ashutosh.tms.repository;

import com.ashutosh.tms.entity.Load;
import com.ashutosh.tms.entity.enums.LoadStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LoadRepository extends JpaRepository<Load, UUID> {

    Page<Load> findByShipperIdAndStatus(String shipperId, LoadStatus status, Pageable pageable);
}
