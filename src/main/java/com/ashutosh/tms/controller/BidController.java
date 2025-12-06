package com.ashutosh.tms.controller;

import com.ashutosh.tms.entity.Bid;
import com.ashutosh.tms.entity.enums.BidStatus;
import com.ashutosh.tms.service.BidService;

import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/bid")
public class BidController {

    private BidService bidService;

    public BidController(BidService bidService) {
        this.bidService = bidService;
    }

    @PostMapping
    public Bid createBid(
            @RequestParam UUID loadId,
            @RequestParam UUID transporterId,
            @RequestParam double rate,
            @RequestParam int trucks
    ) {
        return bidService.createBid(loadId, transporterId, rate, trucks);
    }

    @GetMapping("/{bidId}")
    public Bid getBid(@PathVariable UUID bidId) {
        return bidService.getBidsForLoad(null)
                .stream()
                .filter(b -> b.getBidId().equals(bidId))
                .findFirst()
                .orElse(null);
    }

    @PatchMapping("/{bidId}/reject")
    public Bid rejectBid(@PathVariable UUID bidId) {
        return bidService.rejectBid(bidId);
    }
}
