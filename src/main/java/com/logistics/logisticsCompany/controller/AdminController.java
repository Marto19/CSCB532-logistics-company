package com.logistics.logisticsCompany.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping
public class AdminController {

    @GetMapping("/admin/adminpage")
    public String getAdminPage() {
        return "admin";
    }
}