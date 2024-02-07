package com.logistics.logisticsCompany.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * This class is a controller for handling requests related to the authentication pages.
 * It uses Spring's @Controller annotation to indicate that it is a controller.
 */
@Controller
public class AuthenticationPageController {
    /**
     * This method handles the GET requests for the login page.
     * @return the name of the login page
     */
    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }


    /**
     * This method handles the GET requests for the register page.
     * @return the name of the register page
     */
    @GetMapping("/register")
    public String getRegisterPage() {
        return "register";
    }
}