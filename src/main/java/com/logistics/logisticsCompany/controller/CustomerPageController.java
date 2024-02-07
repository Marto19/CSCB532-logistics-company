package com.logistics.logisticsCompany.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * This class is a controller for handling requests related to the customer page.
 * It uses Spring's @Controller annotation to indicate that it is a controller.
 * It also uses @RequestMapping to map the web requests.
 */
@Controller
@RequestMapping
public class CustomerPageController {

    /**
     * This method handles the GET requests for the customer page.
     * @return the name of the customer page
     */
    @GetMapping("/customer/customerpage")
//    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public String getCustomerPage() {
        return "customer";
    }
}
