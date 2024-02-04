package com.logistics.logisticsCompany.controller;

import com.logistics.logisticsCompany.DTO.EmployeeDTO;
import com.logistics.logisticsCompany.DTO.EntityDtoMapper;
import com.logistics.logisticsCompany.customExceptions.EntityNotFoundException;
import com.logistics.logisticsCompany.entities.logisticsCompany.LogisticsCompany;
import com.logistics.logisticsCompany.entities.offices.Office;
import com.logistics.logisticsCompany.entities.users.Employee;
import com.logistics.logisticsCompany.repository.EmployeeRepository;
import com.logistics.logisticsCompany.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {
    private final EmployeeService employeeService;
    private final EmployeeRepository employeeRepository;
    private final EntityDtoMapper entityDtoMapper;

    @Autowired
    public EmployeeController(EmployeeService employeeService, EmployeeRepository employeeRepository, EntityDtoMapper entityDtoMapper) {
        this.employeeService = employeeService;
        this.employeeRepository = employeeRepository;
        this.entityDtoMapper = entityDtoMapper;
    }

    @PostMapping
    public ResponseEntity<String> createEmployee(@RequestBody Employee employee) {
        try {
            employeeService.createEmployee(employee);
            return ResponseEntity.status(HttpStatus.CREATED).body("Employee added successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        //Convert to List<EmployeeDTO>
        List<EmployeeDTO> employeeDTOs = employeeService.getAllEmployees().stream()
                .map(entityDtoMapper::convertToEmployeeDTO)
                .collect(Collectors.toList());
        //If the list is empty return NO_CONTENT, else return OK and the employees
        return employeeDTOs.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(employeeDTOs, HttpStatus.OK);
    }
    
    //todo decide whether we have 1 or more companies
    @GetMapping("/by-company-id/{companyId}")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployeesByCompanyId(@PathVariable Long companyId) {
        List<EmployeeDTO> employeeDTOs = employeeService.getAllEmployeesByCompanyId(companyId).stream()
                .map(entityDtoMapper::convertToEmployeeDTO)
                .collect(Collectors.toList());
        return employeeDTOs.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(employeeDTOs, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable(value = "id") long employeeId) {
        return employeeService.getEmployeeById(employeeId)
                .map(entityDtoMapper::convertToEmployeeDTO)
                .map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
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
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
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
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
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
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }
}
