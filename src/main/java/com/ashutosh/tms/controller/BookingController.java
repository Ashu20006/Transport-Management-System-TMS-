package com.ashutosh.tms.controller;

import com.ashutosh.tms.entity.Booking;
import com.ashutosh.tms.service.BookingService;

import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/booking")
public class BookingController {

    private BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public Booking createBooking(@RequestParam UUID bidId) {
        return bookingService.createBooking(bidId);
    }

    @GetMapping("/{id}")
    public Booking getBooking(@PathVariable UUID id) {
        return bookingService.getBooking(id);
    }

    @PatchMapping("/{id}/cancel")
    public Booking cancelBooking(@PathVariable UUID id) {
        return bookingService.cancelBooking(id);
    }
}
