package com.logistics.logisticsCompany.controller;

import com.logistics.logisticsCompany.service.LogisticsCompanyService;
import com.logistics.logisticsCompany.service.LogisticsCompanyServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.logistics.logisticsCompany.entities.offices.Office;

@Controller
@RequestMapping("/logistics")
public class LogisticsController {


    private final LogisticsCompanyServiceImpl logisticsService;

    public LogisticsController(LogisticsCompanyService logisticsService) {
        this.logisticsService = (LogisticsCompanyServiceImpl) logisticsService;
    }

    // Mapping for displaying the form to add an office
    @GetMapping("/add-office")
    public String showAddOfficeForm(Model model) {
        model.addAttribute("office", new Office());
        return "add-office";
    }

    // Mapping for submitting the form to add an office
    // In LogisticsController
    @PostMapping("/add-office")
    public String addOffice(@ModelAttribute Office office) {
        System.out.println("Received Office: " + office);  // Add this line for debugging
        logisticsService.addOffice(office);
        return "redirect:/logistics/offices";
    }


    // Mapping for displaying all offices
    @GetMapping("/offices")
    public String showAllOffices(Model model) {
        model.addAttribute("offices", logisticsService.getAllOffices());
        return "all-offices";
    }

    // Add similar mappings for other entities (Employee, Customer, Shipment, etc.)
}