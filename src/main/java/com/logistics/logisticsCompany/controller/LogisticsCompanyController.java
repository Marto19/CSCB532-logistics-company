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

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import com.logistics.logisticsCompany.DTO.EntityDtoMapper;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * This class is a controller for handling requests related to logistics companies.
 * It uses Spring's @RestController annotation to indicate that it is a controller and the response bodies should be bound to the web response body.
 * It also uses @RequestMapping to map the web requests.
 */
@RestController
@RequestMapping("/api/v1/logistics-companies")
public class LogisticsCompanyController {

    /**
     * The LogisticsCompanyService instance used for logistics company-related operations.
     */
    private final LogisticsCompanyService logisticsCompanyService;

    /**
     * The LogisticsCompanyRepository instance used for logistics company-related operations.
     */
    private final LogisticsCompanyRepository logisticsCompanyRepository;

    /**
     * The EntityDtoMapper instance used for mapping entities to DTOs and vice versa.
     */
    private final EntityDtoMapper entityDtoMapper;

    /**
     * This constructor is used to inject the LogisticsCompanyService, LogisticsCompanyRepository and EntityDtoMapper instances.
     * @param logisticsCompanyService the LogisticsCompanyService instance
     * @param logisticsCompanyRepository the LogisticsCompanyRepository instance
     * @param entityDtoMapper the EntityDtoMapper instance
     */
    @Autowired
    public LogisticsCompanyController(LogisticsCompanyService logisticsCompanyService, LogisticsCompanyRepository logisticsCompanyRepository, EntityDtoMapper entityDtoMapper) {
        this.logisticsCompanyService = logisticsCompanyService;
        this.logisticsCompanyRepository = logisticsCompanyRepository;
        this.entityDtoMapper= entityDtoMapper;
    }

    /**
     * This method handles the POST requests for creating a logistics company.
     * @param logisticsCompanyDTO the logistics company to create
     * @return a ResponseEntity with the status and a message
     */
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> createLogisticsCompany(@Valid @RequestBody LogisticsCompanyDTO logisticsCompanyDTO) {
            logisticsCompanyService.createLogisticsCompany(logisticsCompanyDTO);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Logistics company created successfully");
    }

    /**
     * This method handles the GET requests for getting all logistics companies.
     * @return a ResponseEntity with the status and a message
     */
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

    /**
     * This method handles the GET requests for getting a logistics company by id.
     * @param companyId the id of the logistics company
     * @return a ResponseEntity with the status and a message
     */
    @GetMapping("/{id}")
    public ResponseEntity<LogisticsCompanyDTO> getLogisticsCompanyById(@PathVariable(value = "id") long companyId) {
        return logisticsCompanyService.getLogisticsCompanyById(companyId)
                .map(entityDtoMapper::convertToLogisticsCompanyDTO)
                .map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * This method handles the PUT requests for updating a logistics company.
     * @param companyId the id of the logistics company
     * @param updatedCompany the updated logistics company
     * @return a ResponseEntity with the status and a message
     */
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

    /**
     * This method handles the DELETE requests for deleting a logistics company.
     * @param companyId the id of the logistics company
     * @return a ResponseEntity with the status and a message
     */
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
