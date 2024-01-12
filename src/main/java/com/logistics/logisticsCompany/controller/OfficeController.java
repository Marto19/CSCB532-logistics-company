package com.logistics.logisticsCompany.controller;

import com.logistics.logisticsCompany.entities.Office;
import com.logistics.logisticsCompany.entities.Employee;
import com.logistics.logisticsCompany.service.OfficeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class OfficeController {

    @Autowired
    private OfficeServiceImpl officeServiceImpl;

    @PostMapping("/offices")
    public Office createOffice(@RequestBody Office office){
        officeServiceImpl.createOffice(office);
        return null;
    }

    @GetMapping("/offices")
    public List<Employee> getAllOffices(){
        return null;
    }

    @PutMapping("/offices/{id}")
    public void updateOffice(@PathVariable(value = "id")long officeId){
        // http://localhost:8081/api/v1/offices/id_number_of_office
        //logic for updating
    }

    @PutMapping("/offices/{id}")
    public void deleteOffice(@PathVariable(value = "id")long officeId){
        // http://localhost:8081/api/v1/offices/id_number_of_office
        //logic for deleting
    }
}
