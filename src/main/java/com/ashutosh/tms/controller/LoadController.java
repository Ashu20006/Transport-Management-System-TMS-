package com.ashutosh.tms.controller;

import com.ashutosh.tms.entity.Load;
import com.ashutosh.tms.entity.enums.LoadStatus;
import com.ashutosh.tms.entity.Bid;
import com.ashutosh.tms.service.LoadService;
import com.ashutosh.tms.service.BidService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/load")
public class LoadController {

    private LoadService loadService;
    private BidService bidService;

    public LoadController(LoadService loadService, BidService bidService) {
        this.loadService = loadService;
        this.bidService = bidService;
    }

    @PostMapping
    public Load createLoad(@RequestBody Load load) {
        return loadService.createLoad(load);
    }

    @GetMapping
    public Page<Load> listLoads(
            @RequestParam(required = false) String shipperId,
            @RequestParam(required = false) LoadStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return loadService.listLoads(shipperId, status, PageRequest.of(page, size));
    }

    @GetMapping("/{loadId}")
    public Load getLoad(@PathVariable UUID loadId) {
        return loadService.getLoad(loadId);
    }

    @PatchMapping("/{loadId}/cancel")
    public Load cancelLoad(@PathVariable UUID loadId) {
        return loadService.cancelLoad(loadId);
    }

    @GetMapping("/{loadId}/active-bids")
    public List<Bid> getActiveBids(@PathVariable UUID loadId) {
        return bidService.getBidsForLoad(loadId);
    }
}
