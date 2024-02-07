package com.logistics.logisticsCompany.controller;

import com.logistics.logisticsCompany.service.LogisticsCompanyService;
import com.logistics.logisticsCompany.service.LogisticsCompanyServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.logistics.logisticsCompany.entities.offices.Office;

/**
 * This class is a controller for handling requests related to the logistics company.
 * It uses Spring's @Controller annotation to indicate that it is a controller.
 * It also uses @RequestMapping to map the web requests.
 */
@Controller
@RequestMapping("/logistics")
public class LogisticsController {

    /**
     * The LogisticsCompanyService instance used for logistics company-related operations.
     */
    private final LogisticsCompanyServiceImpl logisticsService;


    /**
     * This constructor is used to inject the LogisticsCompanyService instance.
     * @param logisticsService the LogisticsCompanyService instance
     */
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

    /**
     * This method handles the POST requests for adding an office.
     * @param office the office to add
     * @return the name of the all-offices page
     */
    @PostMapping("/add-office")
    public String addOffice(@ModelAttribute Office office) {
        System.out.println("Received Office: " + office);  // Add this line for debugging
        logisticsService.addOffice(office);
        return "redirect:/logistics/offices";
    }


    // Mapping for displaying all offices

    /**
     * This method handles the GET requests for displaying all offices.
     * @param model the model to add the offices to
     * @return the name of the all-offices page
     */
    @GetMapping("/offices")
    public String showAllOffices(Model model) {
        model.addAttribute("offices", logisticsService.getAllOffices());
        return "all-offices";
    }

}
