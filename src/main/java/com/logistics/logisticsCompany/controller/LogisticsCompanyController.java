package com.logistics.logisticsCompany.controller;

import com.logistics.logisticsCompany.DTO.LogisticsCompanyDTO;
import com.logistics.logisticsCompany.customExceptions.EntityNotFoundException;
import com.logistics.logisticsCompany.entities.logisticsCompany.LogisticsCompany;
import com.logistics.logisticsCompany.repository.LogisticsCompanyRepository;
import com.logistics.logisticsCompany.service.LogisticsCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        //Checks if logistics company with the given name already exists
        if (logisticsCompanyRepository.existsByName(logisticsCompany.getName())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Logistics company with the same name already exists");
        }
        //Create logistics company if name does not already exist
        try {
            logisticsCompanyService.createLogisticsCompany(logisticsCompany);
            return ResponseEntity.status(HttpStatus.CREATED).body("LogisticsCompany created successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    
    @GetMapping
    public ResponseEntity<List<LogisticsCompanyDTO>> getAllLogisticsCompanies() {
        //Convert to List<LogisticsCompanyDTO>
        List<LogisticsCompanyDTO> logisticsCompanyDTOs = logisticsCompanyService.getAllLogisticsCompanies().stream()
                .map(entityDtoMapper::convertToLogisticsCompanyDTO)
                .collect(Collectors.toList());
        //If the list is empty return NO_CONTENT, else return OK and the companies
        return logisticsCompanyDTOs.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
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
        if(!logisticsCompanyRepository.existsById(companyId)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Company with the provided id doesn't exist");
        }
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
        if (!logisticsCompanyRepository.existsById(companyId)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Company with the provided id doesn't exist");
        }
        try {
            logisticsCompanyService.deleteLogisticsCompany(companyId);
            return ResponseEntity.ok("LogisticsCompany deleted successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
