package com.logistics.logisticsCompany.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class is a controller for handling requests related to the admin page.
 * It uses Spring's @Controller annotation to indicate that it is a controller.
 * It also uses @RequestMapping to map the web requests.
 */
@Controller
@RequestMapping
public class AdminController {

    /**
     * This method handles the GET requests for the admin page.
     * @return the name of the admin page
     */
    @GetMapping("/admin/adminpage")
    public String getAdminPage() {
        return "admin";
    }
}