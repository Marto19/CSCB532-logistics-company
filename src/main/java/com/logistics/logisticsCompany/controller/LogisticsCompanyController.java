package com.logistics.logisticsCompany.controller;

import com.logistics.logisticsCompany.DTO.LogisticsCompanyDTO;
import com.logistics.logisticsCompany.customExceptions.EntityNotFoundException;
import com.logistics.logisticsCompany.entities.logisticsCompany.LogisticsCompany;
import com.logistics.logisticsCompany.repository.LogisticsCompanyRepository;
import com.logistics.logisticsCompany.service.LogisticsCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import com.logistics.logisticsCompany.DTO.EntityDtoMapper;

@RestController
@RequestMapping("/api/v1/logistics-companies")
public class LogisticsCompanyController {
    private final LogisticsCompanyService logisticsCompanyService;
    private final LogisticsCompanyRepository logisticsCompanyRepository;
    private final EntityDtoMapper entityDtoMapper;

    @Autowired
    public LogisticsCompanyController(LogisticsCompanyService logisticsCompanyService, LogisticsCompanyRepository logisticsCompanyRepository, EntityDtoMapper entityDtoMapper) {
        this.logisticsCompanyService = logisticsCompanyService;
        this.logisticsCompanyRepository = logisticsCompanyRepository;
        this.entityDtoMapper= entityDtoMapper;
    }

    @PostMapping
    public ResponseEntity<String> createLogisticsCompany(@RequestBody LogisticsCompany logisticsCompany) {
        try {
            logisticsCompanyService.createLogisticsCompany(logisticsCompany);
            return ResponseEntity.status(HttpStatus.CREATED).body("LogisticsCompany created successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }
    
//     @GetMapping

//     @PreAuthorize("hasAuthority('ADMIN')")
//     public List<LogisticsCompany> getAllLogisticsCompanies() {
//         return logisticsCompanyService.getAllLogisticsCompanies();
  
    @GetMapping
    public ResponseEntity<List<LogisticsCompanyDTO>> getAllLogisticsCompanies() {
        //Convert to List<LogisticsCompanyDTO>
        List<LogisticsCompanyDTO> logisticsCompanyDTOs = logisticsCompanyService.getAllLogisticsCompanies().stream()
                .map(entityDtoMapper::convertToLogisticsCompanyDTO)
                .collect(Collectors.toList());
        //If the list is empty return NOT_FOUND, else return OK and the companies
        return logisticsCompanyDTOs.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(logisticsCompanyDTOs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LogisticsCompanyDTO> getLogisticsCompanyById(@PathVariable(value = "id") long companyId) {
        return logisticsCompanyService.getLogisticsCompanyById(companyId)
                .map(entityDtoMapper::convertToLogisticsCompanyDTO)
                .map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
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
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
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
