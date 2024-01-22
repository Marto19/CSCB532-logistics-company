package com.logistics.logisticsCompany.controller;

import com.logistics.logisticsCompany.entities.offices.Office;
import com.logistics.logisticsCompany.service.OfficeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/offices")
public class OfficeController {

    @Autowired
    private OfficeServiceImpl officeService;

    @PostMapping
    public void createOffice(@RequestBody Office office) {
        officeService.createOffice(office);
    }

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
