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

@RestController
@RequestMapping("/api/v1/offices")
public class OfficeController {

    private final OfficeService officeService;
    private final OfficeRepository officeRepository;
    private final EntityDtoMapper entityDtoMapper;

    @Autowired
    public OfficeController(OfficeService officeService, OfficeRepository officeRepository, EntityDtoMapper entityDtoMapper) {
        this.officeService = officeService;
        this.officeRepository = officeRepository;
        this.entityDtoMapper = entityDtoMapper;
    }

    @PostMapping
    public ResponseEntity<String> createOffice(@RequestBody Office office) {
        try {
            officeService.createOffice(office);
            return ResponseEntity.status(HttpStatus.CREATED).body("Office created successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }

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

    @GetMapping("/{id}")
    public ResponseEntity<OfficeDTO> getOfficeById(@PathVariable(value = "id") long officeId) {
        return officeService.getOfficeById(officeId)
                .map(entityDtoMapper::convertToOfficeDTO)
                .map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

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
