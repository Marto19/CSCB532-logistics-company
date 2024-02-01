package com.logistics.logisticsCompany.controller;

import com.logistics.logisticsCompany.DTO.LogisticsCompanyDTO;
import com.logistics.logisticsCompany.DTO.UserDTO;
import com.logistics.logisticsCompany.customExceptions.EntityNotFoundException;
import com.logistics.logisticsCompany.entities.logisticsCompany.LogisticsCompany;
import com.logistics.logisticsCompany.entities.users.Employee;
import com.logistics.logisticsCompany.entities.users.User;
import com.logistics.logisticsCompany.repository.LogisticsCompanyRepository;
import com.logistics.logisticsCompany.service.LogisticsCompanyService;
import com.logistics.logisticsCompany.service.LogisticsCompanyServiceImpl;
import org.apache.coyote.Response;
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
    public LogisticsCompanyController(LogisticsCompanyService logisticsCompanyService, com.logistics.logisticsCompany.repository.LogisticsCompanyRepository logisticsCompanyRepository1, com.logistics.logisticsCompany.repository.LogisticsCompanyRepository logisticsCompanyRepository, EntityDtoMapper entityDtoMapper) {
        this.logisticsCompanyService = logisticsCompanyService;
        this.logisticsCompanyRepository = logisticsCompanyRepository1;
        LogisticsCompanyRepository = logisticsCompanyRepository;
        this.entityDtoMapper= entityDtoMapper;
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
    public ResponseEntity<List<LogisticsCompanyDTO>> getAllLogisticsCompanies() {
        List<LogisticsCompanyDTO> logisticsCompanyDTOs = logisticsCompanyService.getAllLogisticsCompanies().stream()
                .map(entityDtoMapper::convertToLogisticsCompanyDTO)
                .collect(Collectors.toList());
        
        return logisticsCompanyDTOs.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(logisticsCompanyDTOs, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<LogisticsCompanyDTO> getLogisticsCompanyById(@PathVariable long id) {
        return logisticsCompanyService.getLogisticsCompanyById(id)
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
