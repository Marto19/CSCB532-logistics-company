package com.logistics.logisticsCompany.controller;

import com.logistics.logisticsCompany.DTO.EntityDtoMapper;
import com.logistics.logisticsCompany.DTO.OfficeDTO;
import com.logistics.logisticsCompany.customExceptions.EntityNotFoundException;
import com.logistics.logisticsCompany.entities.offices.Office;
import com.logistics.logisticsCompany.repository.OfficeRepository;
import com.logistics.logisticsCompany.service.OfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The OfficeController class handles HTTP requests related to office operations.
 * It provides endpoints for creating, retrieving, updating, and deleting offices.
 */
@RestController
@RequestMapping("/api/v1/offices")
public class OfficeController {

    private final OfficeService officeService;
    private final OfficeRepository officeRepository;
    private final EntityDtoMapper entityDtoMapper;

    /**
     * Constructs an OfficeController with the specified dependencies.
     *
     * @param officeService    The service for handling office-related operations.
     * @param officeRepository The repository for accessing office data.
     * @param entityDtoMapper  The mapper for converting between entities and DTOs.
     */
    @Autowired
    public OfficeController(OfficeService officeService, OfficeRepository officeRepository, EntityDtoMapper entityDtoMapper) {
        this.officeService = officeService;
        this.officeRepository = officeRepository;
        this.entityDtoMapper = entityDtoMapper;
    }

   
   /**
     * Handles the HTTP POST request to create a new office.
     *
     * @param officeDTO The DTO containing the details of the new office.
     * @return A ResponseEntity indicating the result of the operation.
     */
   
    @PostMapping
    public ResponseEntity<String> createOffice(@RequestBody OfficeDTO officeDTO) {
            officeService.createOffice(officeDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Office created successfully");
    }

    /**
     * Handles the HTTP GET request to retrieve all offices.
     *
     * @return A ResponseEntity containing the list of offices or an error status.
     */
    @GetMapping
    public ResponseEntity<List<OfficeDTO>> getAllOffices() {
        //Convert to List<officeDTO>
        List<OfficeDTO> officeDTOs = officeService.getAllOffices().stream()
                .map(entityDtoMapper::convertToOfficeDTO)
                .collect(Collectors.toList());
        //If the list is empty return NO_CONTENT, else return OK and the offices
        return officeDTOs.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(officeDTOs, HttpStatus.OK);
    }

    /**
     * Handles the HTTP GET request to retrieve an office by ID.
     *
     * @param officeId The ID of the office to retrieve.
     * @return A ResponseEntity containing the office DTO or an error status.
     */
    @GetMapping("/{id}")
    public ResponseEntity<OfficeDTO> getOfficeById(@PathVariable(value = "id") long officeId) {
        return officeService.getOfficeById(officeId)
                .map(entityDtoMapper::convertToOfficeDTO)
                .map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Handles the HTTP PUT request to update an office.
     *
     * @param officeId      The ID of the office to update.
     * @param updatedOffice The updated office details.
     * @return A ResponseEntity indicating the result of the operation.
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> updateOffice(@PathVariable(value = "id") long officeId,
                                               @RequestBody Office updatedOffice) {
        try {
            officeService.updateOffice(officeId, updatedOffice);
            return ResponseEntity.ok("Office updated successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }

    /**
     * Handles the HTTP DELETE request to delete an office.
     *
     * @param officeId The ID of the office to delete.
     * @return A ResponseEntity indicating the result of the operation.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOffice(@PathVariable(value = "id") long officeId) {
        try {
            officeService.deleteOffice(officeId);
            return ResponseEntity.ok("Office deleted successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
