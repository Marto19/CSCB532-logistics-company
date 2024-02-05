package com.logistics.logisticsCompany.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class CustomerPageController {
    @GetMapping("/customer/customerpage")
//    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public String getCustomerPage() {
        return "customer";
    }
}
