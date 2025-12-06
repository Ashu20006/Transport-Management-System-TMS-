package com.ashutosh.tms.service;

import com.ashutosh.tms.entity.*;
import com.ashutosh.tms.entity.enums.*;
import com.ashutosh.tms.exception.*;
import com.ashutosh.tms.repository.*;

import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
public class BookingService {

    private LoadRepository loadRepository;
    private BidRepository bidRepository;
    private BookingRepository bookingRepository;
    private TransporterTruckRepository transporterTruckRepository;

    public BookingService(
            LoadRepository loadRepository,
            BidRepository bidRepository,
            BookingRepository bookingRepository,
            TransporterTruckRepository transporterTruckRepository
    ) {
        this.loadRepository = loadRepository;
        this.bidRepository = bidRepository;
        this.bookingRepository = bookingRepository;
        this.transporterTruckRepository = transporterTruckRepository;
    }

    @Transactional
    public Booking createBooking(UUID bidId) {

        try {
            Bid bid = bidRepository.findById(bidId)
                    .orElseThrow(() -> new ResourceNotFoundException("Bid not found"));

            Load load = bid.getLoad();
            Transporter transporter = bid.getTransporter();

            if (bidRepository.existsByLoadAndStatus(load, BidStatus.ACCEPTED)) {
                throw new LoadAlreadyBookedException("Load already booked");
            }

            int allocated = bookingRepository.sumAllocatedTrucksForLoad(load);
            int remaining = load.getNoOfTrucks() - allocated;

            if (bid.getTrucksOffered() > remaining) {
                throw new InsufficientCapacityException("Not enough remaining trucks");
            }

            TransporterTruck truck = transporterTruckRepository
                    .findByTransporterAndTruckType(transporter, load.getTruckType())
                    .orElseThrow(() -> new ResourceNotFoundException("Truck type not found"));

            if (bid.getTrucksOffered() > truck.getCount()) {
                throw new InsufficientCapacityException("Transporter does not have enough trucks");
            }

            truck.setCount(truck.getCount() - bid.getTrucksOffered());
            transporterTruckRepository.save(truck);

            bid.setStatus(BidStatus.ACCEPTED);
            bidRepository.save(bid);

            Booking booking = new Booking();
            booking.setLoad(load);
            booking.setBid(bid);
            booking.setTransporter(transporter);
            booking.setAllocatedTrucks(bid.getTrucksOffered());
            booking.setFinalRate(bid.getProposedRate());
            booking.setStatus(BookingStatus.CONFIRMED);
            booking.setBookedAt(Instant.now());

            bookingRepository.save(booking);

            int after = remaining - bid.getTrucksOffered();

            if (after == 0) {
                load.setStatus(LoadStatus.BOOKED);
            } else {
                load.setStatus(LoadStatus.OPEN_FOR_BIDS);
            }

            loadRepository.save(load);

            return booking;

        } catch (ObjectOptimisticLockingFailureException e) {
            throw new LoadAlreadyBookedException("Concurrent booking detected!");
        }
    }

    public Booking getBooking(UUID id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));
    }

    @Transactional
    public Booking cancelBooking(UUID bookingId) {

        Booking booking = getBooking(bookingId);

        if (booking.getStatus() == BookingStatus.CANCELLED) {
            return booking;
        }

        Load load = booking.getLoad();
        Transporter transporter = booking.getTransporter();

        TransporterTruck truck = transporterTruckRepository
                .findByTransporterAndTruckType(transporter, load.getTruckType())
                .orElseThrow(() -> new ResourceNotFoundException("Truck type not found"));

        truck.setCount(truck.getCount() + booking.getAllocatedTrucks());
        transporterTruckRepository.save(truck);

        booking.setStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);

        int allocated = bookingRepository.sumAllocatedTrucksForLoad(load);
        int remaining = load.getNoOfTrucks() - allocated;

        if (remaining > 0) {
            load.setStatus(LoadStatus.OPEN_FOR_BIDS);
        }

        loadRepository.save(load);

        return booking;
    }
}
