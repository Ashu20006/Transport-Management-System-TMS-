package com.ashutosh.tms.repository;

import com.ashutosh.tms.entity.Bid;
import com.ashutosh.tms.entity.Load;
import com.ashutosh.tms.entity.enums.BidStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BidRepository extends JpaRepository<Bid, UUID> {

    List<Bid> findByLoadAndStatus(Load load, BidStatus status);

    List<Bid> findByLoad_LoadId(UUID loadId);

    boolean existsByLoadAndStatus(Load load, BidStatus status);

    Optional<Bid> findByBidId(UUID bidId);
}
