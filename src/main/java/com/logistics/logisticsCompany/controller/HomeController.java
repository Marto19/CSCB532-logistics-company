package com.logistics.logisticsCompany.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping
    public String home(){
        return "Hello, World";
    }

    @GetMapping("/admin")
    public String admin(){
        return "Hello, Admin!";
    }
}
