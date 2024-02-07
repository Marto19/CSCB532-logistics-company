package com.logistics.logisticsCompany.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


//controller to test secured endpoint

/**
 * This class is a controller for handling requests related to the demo page.
 * It uses Spring's @RestController annotation to indicate that it is a controller and the response bodies should be bound to the web response body.
 * It also uses @RequestMapping to map the web requests.
 */
@RestController
@RequestMapping("/api/v1/demo-controller")
public class DemoController {

    /**
     * This method handles the GET requests for the demo page.
     * @return the response entity with the message "Hello from secured endpoint"
     */
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