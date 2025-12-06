package com.ashutosh.tms.service;

import com.ashutosh.tms.entity.Load;
import com.ashutosh.tms.entity.enums.LoadStatus;
import com.ashutosh.tms.exception.InvalidStatusTransitionException;
import com.ashutosh.tms.exception.ResourceNotFoundException;
import com.ashutosh.tms.repository.LoadRepository;
import com.ashutosh.tms.repository.BidRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class LoadService {

    private LoadRepository loadRepository;
    private BidRepository bidRepository;

    public LoadService(LoadRepository loadRepository, BidRepository bidRepository) {
        this.loadRepository = loadRepository;
        this.bidRepository = bidRepository;
    }

    // Create load
    public Load createLoad(Load load) {
        load.setDatePosted(Instant.now());
        load.setStatus(LoadStatus.POSTED);
        return loadRepository.save(load);
    }

    // List loads with filter
    public Page<Load> listLoads(String shipperId, LoadStatus status, Pageable pageable) {
        if (shipperId != null && status != null) {
            return loadRepository.findByShipperIdAndStatus(shipperId, status, pageable);
        }
        return loadRepository.findAll(pageable);
    }

    // Get load
    public Load getLoad(UUID loadId) {
        return loadRepository.findById(loadId)
                .orElseThrow(() -> new ResourceNotFoundException("Load not found"));
    }

    // Cancel load
    public Load cancelLoad(UUID loadId) {
        Load load = getLoad(loadId);

        if (load.getStatus() == LoadStatus.BOOKED) {
            throw new InvalidStatusTransitionException("Cannot cancel a booked load");
        }

        load.setStatus(LoadStatus.CANCELLED);
        return loadRepository.save(load);
    }
}
