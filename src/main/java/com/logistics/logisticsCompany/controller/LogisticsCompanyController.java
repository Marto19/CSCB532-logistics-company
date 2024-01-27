package com.logistics.logisticsCompany.controller;

import com.logistics.logisticsCompany.customExceptions.EntityNotFoundException;
import com.logistics.logisticsCompany.entities.logisticsCompany.LogisticsCompany;
import com.logistics.logisticsCompany.repository.LogisticsCompanyRepository;
import com.logistics.logisticsCompany.service.LogisticsCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/logistics-companies")
public class LogisticsCompanyController {

    private final LogisticsCompanyService logisticsCompanyService;

    @Autowired
    public LogisticsCompanyController(LogisticsCompanyService logisticsCompanyService, com.logistics.logisticsCompany.repository.LogisticsCompanyRepository logisticsCompanyRepository) {
        this.logisticsCompanyService = logisticsCompanyService;
        LogisticsCompanyRepository = logisticsCompanyRepository;
    }

    private final LogisticsCompanyRepository LogisticsCompanyRepository;

    @PostMapping
    public ResponseEntity<String> createLogisticsCompany(@RequestBody LogisticsCompany logisticsCompany) {
        if (LogisticsCompanyRepository.existsByName(logisticsCompany.getName())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Logistics company with the same name already exists");
        }
        try {
            logisticsCompanyService.createLogisticsCompany(logisticsCompany);
            return ResponseEntity.status(HttpStatus.CREATED).body("LogisticsCompany created successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping
    public List<LogisticsCompany> getAllLogisticsCompanies() {
        return logisticsCompanyService.getAllLogisticsCompanies();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateLogisticsCompany(@PathVariable(value = "id") long companyId,
                                                         @RequestBody LogisticsCompany updatedCompany) {
        try {
            logisticsCompanyService.updateLogisticsCompany(companyId, updatedCompany);
            return ResponseEntity.ok("LogisticsCompany updated successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLogisticsCompany(@PathVariable(value = "id") long companyId) {
        try {
            logisticsCompanyService.deleteLogisticsCompany(companyId);
            return ResponseEntity.ok("LogisticsCompany deleted successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
