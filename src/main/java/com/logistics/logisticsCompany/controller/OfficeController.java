package com.logistics.logisticsCompany.controller;

import com.logistics.logisticsCompany.DTO.OfficeDTO;
import com.logistics.logisticsCompany.customExceptions.EntityNotFoundException;
import com.logistics.logisticsCompany.entities.offices.Office;
import com.logistics.logisticsCompany.repository.OfficeRepository;
import com.logistics.logisticsCompany.service.OfficeServiceImpl;
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

    @Autowired
    private OfficeServiceImpl officeService;
    @Autowired
    private OfficeRepository officeRepository;

    @PostMapping
    public ResponseEntity<String> createOffice(@RequestBody Office office) {
        //Checks if office with the given address already exists
        if (officeRepository.existsByAddress(office.getAddress())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Office with the same address already exists");
        }

        //Create office if address does not already exist
        try {
            officeService.createOffice(office);
            return ResponseEntity.status(HttpStatus.CREATED).body("Office created successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping
    public List<OfficeDTO> getAllOffices() {
        List<Office> offices = officeService.getAllOffices();

        // Convert the list of Office entities to OfficeDTOs
        List<OfficeDTO> officeDTOs = offices.stream()
                .map(office -> new OfficeDTO(
                        office.getId(),
                        office.getOfficeName(),
                        office.getCity(),
                        office.getPostcode(),
                        office.getAddress()
                ))
                .collect(Collectors.toList());

        return officeDTOs;
    }

    @GetMapping("/{id}")
    public ResponseEntity<OfficeDTO> getOfficeById(@PathVariable(value = "id") long officeId) {
        Optional<Office> office = officeRepository.getOfficeById(officeId);

        return office.map(o -> new ResponseEntity<>(
                new OfficeDTO(
                        o.getId(),
                        o.getOfficeName(),
                        o.getCity(),
                        o.getPostcode(),
                        o.getAddress()
                ),
                HttpStatus.OK
        )).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @PutMapping("/{id}")
    public ResponseEntity<String> updateOffice(@PathVariable(value = "id") long officeId, @RequestBody Office updatedOffice) {
        if(!officeRepository.existsById(officeId)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Company with the provided id doesn't exist");
        }
        try {
            officeService.updateOffice(officeId, updatedOffice);
            return ResponseEntity.ok("Office updated successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOffice(@PathVariable(value = "id") long officeId) {
        if (!officeRepository.existsById(officeId)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Customer with the provided id doesn't exist");
        }
        officeService.deleteOffice(officeId);
        return ResponseEntity.ok("Office with id = " + officeId + " deleted successfully");
    }
}
