package com.logistics.logisticsCompany.controller;

import com.logistics.logisticsCompany.DTO.EmployeeDTO;
import com.logistics.logisticsCompany.DTO.EntityDtoMapper;
import com.logistics.logisticsCompany.customExceptions.EntityNotFoundException;
import com.logistics.logisticsCompany.entities.logisticsCompany.LogisticsCompany;
import com.logistics.logisticsCompany.entities.offices.Office;
import com.logistics.logisticsCompany.entities.users.Employee;
import com.logistics.logisticsCompany.repository.EmployeeRepository;
import com.logistics.logisticsCompany.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * This class is a controller for handling requests related to Employee.
 * It uses Spring's @RestController annotation to indicate that it is a controller and the response bodies should be bound to the web response body.
 * It also uses @RequestMapping to map the web requests.
 */

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {
    /**
     * The EmployeeService instance used for employee-related operations.
     */
    private final EmployeeService employeeService;
    /**
     * The EmployeeRepository instance used for employee-related operations.
     */
    private final EmployeeRepository employeeRepository;
    /**
     * The EntityDtoMapper instance used for converting entities to DTOs and vice versa.
     */
    private final EntityDtoMapper entityDtoMapper;

    /**
     * Constructs an EmployeeController with the specified EmployeeService, EmployeeRepository, and EntityDtoMapper.
     * @param employeeService the EmployeeService
     * @param employeeRepository the EmployeeRepository
     * @param entityDtoMapper the EntityDtoMapper
     */
    @Autowired
    public EmployeeController(EmployeeService employeeService, EmployeeRepository employeeRepository, EntityDtoMapper entityDtoMapper) {
        this.employeeService = employeeService;
        this.employeeRepository = employeeRepository;
        this.entityDtoMapper = entityDtoMapper;
    }

    /**
     * This method handles the POST requests for creating an employee.
     * @param employeeDTO the employee to create
     * @return a ResponseEntity with the status and a message
     */
    @PostMapping
    public ResponseEntity<String> createEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
            employeeService.createEmployee(employeeDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Employee created successfully");

    }

    /**
     * This method handles the GET requests for getting all employees.
     * @return a ResponseEntity with the list of EmployeeDTO if it exists, or a not found status
     */
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
    /**
     * This method handles the GET requests for getting all employees by company id.
     * @param companyId the id of the company
     * @return a ResponseEntity with the list of EmployeeDTO if it exists, or a not found status
     */
    @GetMapping("/by-company-id/{companyId}")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployeesByCompanyId(@PathVariable Long companyId) {
        List<EmployeeDTO> employeeDTOs = employeeService.getAllEmployeesByCompanyId(companyId).stream()
                .map(entityDtoMapper::convertToEmployeeDTO)
                .collect(Collectors.toList());
        return employeeDTOs.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(employeeDTOs, HttpStatus.OK);
    }

    /**
     * This method handles the GET requests for getting an employee by id.
     * @param employeeId the id of the employee
     * @return a ResponseEntity with the EmployeeDTO if it exists, or a not found status
     */
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable(value = "id") long employeeId) {
        return employeeService.getEmployeeById(employeeId)
                .map(entityDtoMapper::convertToEmployeeDTO)
                .map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * This method handles the PUT requests for updating an employee.
     * @param employeeId the id of the employee to update
     * @param updatedEmployee the updated employee
     * @return a ResponseEntity with the status and a message
     */
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

    /**
     * This method handles the DELETE requests for deleting an employee.
     * @param employeeId the id of the employee to delete
     * @return a ResponseEntity with the status and a message
     */
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

    /**
     * This method handles the POST requests for assigning a logistics company to an employee.
     * @param employeeId the id of the employee
     * @param logisticsCompany the logistics company to assign
     * @return a ResponseEntity with the status and a message
     */
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
