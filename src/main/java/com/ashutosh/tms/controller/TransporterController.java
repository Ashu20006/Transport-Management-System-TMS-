package com.ashutosh.tms.controller;

import com.ashutosh.tms.entity.Transporter;
import com.ashutosh.tms.entity.TransporterTruck;
import com.ashutosh.tms.repository.TransporterRepository;
import com.ashutosh.tms.repository.TransporterTruckRepository;
import com.ashutosh.tms.exception.ResourceNotFoundException;

import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/transporter")
public class TransporterController {

    private TransporterRepository transporterRepository;
    private TransporterTruckRepository truckRepository;

    public TransporterController(
            TransporterRepository transporterRepository,
            TransporterTruckRepository truckRepository
    ) {
        this.transporterRepository = transporterRepository;
        this.truckRepository = truckRepository;
    }

    @PostMapping
    public Transporter createTransporter(@RequestBody Transporter transporter) {
        return transporterRepository.save(transporter);
    }

    @GetMapping("/{id}")
    public Transporter getTransporter(@PathVariable UUID id) {
        return transporterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transporter not found"));
    }

    @PutMapping("/{id}/trucks")
    public TransporterTruck updateTruck(
            @PathVariable UUID id,
            @RequestParam String truckType,
            @RequestParam int count
    ) {
        Transporter transporter = transporterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transporter not found"));

        Optional<TransporterTruck> existing =
                truckRepository.findByTransporterAndTruckType(transporter, truckType);

        TransporterTruck truck;

        if (existing.isPresent()) {
            truck = existing.get();
            truck.setCount(count);
        } else {
            truck = new TransporterTruck();
            truck.setTransporter(transporter);
            truck.setTruckType(truckType);
            truck.setCount(count);
        }

        return truckRepository.save(truck);
    }
}
