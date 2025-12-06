package com.ashutosh.tms.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping("/ping")
    public String ping() {
        return "TMS Backend is running";
    }
}
