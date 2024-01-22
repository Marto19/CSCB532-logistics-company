package com.logistics.logisticsCompany.controller;

import com.logistics.logisticsCompany.entities.offices.Office;
import com.logistics.logisticsCompany.repository.OfficeRepository;
import com.logistics.logisticsCompany.service.OfficeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/offices")
public class OfficeController {

    @Autowired
    private OfficeServiceImpl officeService;
    @Autowired
    private OfficeRepository officeRepository;

    @PostMapping
    public ResponseEntity<String> createOffice(@RequestBody Office office) {
        // Check if the office with the given name already exists
        if (officeRepository.existsByOfficeName(office.getOfficeName())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Office with the same name already exists");
        }

        // If the office doesn't exist, proceed with saving it
        officeService.createOffice(office);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Office created successfully");
    }//todo: add protection in other places too
    @GetMapping
    public List<Office> getAllOffices() {
        return officeService.getAllOffices();
    }

    @PutMapping("/{id}")
    public void updateOffice(@PathVariable(value = "id") long officeId, @RequestBody Office updatedOffice) {
        officeService.updateOffice(officeId, updatedOffice);
    }

    @DeleteMapping("/{id}")
    public void deleteOffice(@PathVariable(value = "id") long officeId) {
        officeService.deleteOffice(officeId);
    }
}
