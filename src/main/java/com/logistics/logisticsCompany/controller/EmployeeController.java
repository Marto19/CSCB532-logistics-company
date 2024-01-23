package com.logistics.logisticsCompany.controller;

import com.logistics.logisticsCompany.customExceptions.EntityNotFoundException;
import com.logistics.logisticsCompany.entities.logisticsCompany.LogisticsCompany;
import com.logistics.logisticsCompany.entities.offices.Office;
import com.logistics.logisticsCompany.entities.users.Employee;
import com.logistics.logisticsCompany.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<String> addEmployee(@RequestBody Employee employee) {
        try {
            employeeService.addEmployee(employee);
            return ResponseEntity.status(HttpStatus.CREATED).body("Employee added successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateEmployee(@PathVariable(value = "id") long employeeId,
                                                 @RequestBody Employee updatedEmployee) {
        try {
            employeeService.updateEmployee(employeeId, updatedEmployee);
            return ResponseEntity.ok("Employee updated successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable(value = "id") long employeeId) {
        try {
            employeeService.deleteEmployee(employeeId);
            return ResponseEntity.ok("Employee deleted successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/{id}/assign-office")
    public ResponseEntity<String> assignOfficeToEmployee(@PathVariable(value = "id") long employeeId,
                                                         @RequestBody Office office) {
        try {
            employeeService.assignOfficeToEmployee(employeeId, office);
            return ResponseEntity.ok("Office assigned to employee successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/{id}/assign-logistics-company")
    public ResponseEntity<String> assignLogisticsCompanyToEmployee(@PathVariable(value = "id") long employeeId,
                                                                   @RequestBody LogisticsCompany logisticsCompany) {
        try {
            employeeService.assignLogisticsCompanyToEmployee(employeeId, logisticsCompany);
            return ResponseEntity.ok("LogisticsCompany assigned to employee successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
