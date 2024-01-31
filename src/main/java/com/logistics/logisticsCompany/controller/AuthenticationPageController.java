package com.logistics.logisticsCompany.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthenticationPageController {

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }



    @GetMapping("/register")
    public String getRegisterPage() {
        return "register";
    }
}
