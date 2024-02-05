package com.logistics.logisticsCompany.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


//controller to test secured endpoint
@RestController
@RequestMapping("/api/v1/demo-controller")
public class DemoController {
    @GetMapping
    public ResponseEntity<String> sayHello(){
        return ResponseEntity.ok("Hello from secured endpoint");
    }
}

//ALTER TABLE user MODIFY COLUMN password VARCHAR(255); -- Use an appropriate length
//TODO: hide the endpoints properly and learn how to pass the authentication token to the other pages
//i suppose that once we've generated one, its available for 24 hours, so we can pass it to the other requests
//which will allow them to be executed

//this controller have the bearer token which is generated when loggin, we nee to see how to pass it to the other requests