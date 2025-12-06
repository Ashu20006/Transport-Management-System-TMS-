package com.ashutosh.tms.service;

import com.ashutosh.tms.entity.*;
import com.ashutosh.tms.entity.enums.*;
import com.ashutosh.tms.exception.*;
import com.ashutosh.tms.repository.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;

@Service
public class BidService {

    private LoadRepository loadRepository;
    private TransporterRepository transporterRepository;
    private TransporterTruckRepository transporterTruckRepository;
    private BidRepository bidRepository;
    private BookingRepository bookingRepository;

    public BidService(
            LoadRepository loadRepository,
            TransporterRepository transporterRepository,
            TransporterTruckRepository transporterTruckRepository,
            BidRepository bidRepository,
            BookingRepository bookingRepository
    ) {
        this.loadRepository = loadRepository;
        this.transporterRepository = transporterRepository;
        this.transporterTruckRepository = transporterTruckRepository;
        this.bidRepository = bidRepository;
        this.bookingRepository = bookingRepository;
    }

    @Transactional
    public Bid createBid(UUID loadId, UUID transporterId, double rate, int trucksOffered) {

        Load load = loadRepository.findById(loadId)
                .orElseThrow(() -> new ResourceNotFoundException("Load not found"));

        if (load.getStatus() == LoadStatus.CANCELLED || load.getStatus() == LoadStatus.BOOKED) {
            throw new InvalidStatusTransitionException("Cannot bid on this load");
        }

        Transporter transporter = transporterRepository.findById(transporterId)
                .orElseThrow(() -> new ResourceNotFoundException("Transporter not found"));

        TransporterTruck truck = transporterTruckRepository
                .findByTransporterAndTruckType(transporter, load.getTruckType())
                .orElseThrow(() -> new InsufficientCapacityException("No trucks available for this type"));

        if (trucksOffered > truck.getCount()) {
            throw new InsufficientCapacityException("Offered trucks exceed available trucks");
        }

        int allocated = bookingRepository.sumAllocatedTrucksForLoad(load);

        int remainingTrucks = load.getNoOfTrucks() - allocated;

        if (trucksOffered > remainingTrucks) {
            throw new InsufficientCapacityException("Not enough remaining trucks for this load");
        }

        Bid bid = new Bid();
        bid.setLoad(load);
        bid.setTransporter(transporter);
        bid.setProposedRate(rate);
        bid.setTrucksOffered(trucksOffered);
        bid.setStatus(BidStatus.PENDING);
        bid.setSubmittedAt(Instant.now());

        if (load.getStatus() == LoadStatus.POSTED) {
            load.setStatus(LoadStatus.OPEN_FOR_BIDS);
            loadRepository.save(load);
        }

        return bidRepository.save(bid);
    }

    public List<Bid> getBidsForLoad(UUID loadId) {
        return bidRepository.findByLoad_LoadId(loadId);
    }

    public Bid rejectBid(UUID bidId) {
        Bid bid = bidRepository.findById(bidId)
                .orElseThrow(() -> new ResourceNotFoundException("Bid not found"));

        bid.setStatus(BidStatus.REJECTED);
        return bidRepository.save(bid);
    }
}
