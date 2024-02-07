package com.logistics.logisticsCompany.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class is a controller for handling requests related to the home page.
 * It uses Spring's @RestController annotation to indicate that it is a controller and the response bodies should be bound to the web response body.
 * It also uses @GetMapping to map the web requests.
 */
@RestController
public class HomeController {
    /**
     * This method handles the GET requests for the home page.
     * @return the message "Hello, World"
     */
    @GetMapping
    public String home(){
        return "Hello, World";
    }

    /**
     * This method handles the GET requests for the customer page.
     * @return the message "Hello, Customer!"
     */
    @GetMapping("/admin")
    public String admin(){
        return "Hello, Admin!";
    }
}
