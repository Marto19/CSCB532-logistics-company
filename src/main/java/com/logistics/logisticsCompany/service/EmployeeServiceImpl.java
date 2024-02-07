package com.logistics.logisticsCompany.service;

import com.logistics.logisticsCompany.DTO.EmployeeDTO;
import com.logistics.logisticsCompany.DTO.EntityDtoMapper;
import com.logistics.logisticsCompany.customExceptions.EntityNotFoundException;
import com.logistics.logisticsCompany.entities.logisticsCompany.LogisticsCompany;
import com.logistics.logisticsCompany.entities.offices.Office;
import com.logistics.logisticsCompany.entities.users.Employee;
import com.logistics.logisticsCompany.entities.users.User;
import com.logistics.logisticsCompany.repository.EmployeeRepository;
import com.logistics.logisticsCompany.repository.LogisticsCompanyRepository;
import com.logistics.logisticsCompany.repository.OfficeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.logistics.logisticsCompany.entities.logisticsCompany.LogisticsCompany;
import java.util.List;
import java.util.Optional;


/**
 * The {@code EmployeeServiceImpl} class implements the {@code EmployeeService} interface.
 * It provides the business logic for managing employees.
 */

@Service
public class EmployeeServiceImpl implements EmployeeService {
    /**
     * The {@code EmployeeRepository} instance used for employee-related database operations.
     */
    private final EmployeeRepository employeeRepository;

    /**
     * Constructs an {@code EmployeeServiceImpl} with the specified {@code EmployeeRepository}.
     * @param employeeRepository the {@code EmployeeRepository}
     */

    private final LogisticsCompanyRepository logisticsCompanyRepository;
    
    private final OfficeRepository officeRepository;
    
    private final UserLinkageService userLinkageService;
    
    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, LogisticsCompanyRepository logisticsCompanyRepository, UserLinkageService userLinkageService, OfficeRepository officeRepository){
        this.employeeRepository = employeeRepository;
        this.logisticsCompanyRepository = logisticsCompanyRepository;
        this.userLinkageService = userLinkageService;
        this.officeRepository = officeRepository;
    }
 
    /**
     * Creates a new employee.
     * @param employeeDTO the employee to create
     */
    
    @Override
    public Employee createEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        EntityDtoMapper.convertEmployeeDtoToEntity(employeeDTO); // Ensure this method sets names etc.
        
        // Link User
        if (employeeDTO.getUserId() != null || employeeDTO.getUsername() != null) {
            User user = userLinkageService.findAndValidateUserForLinkage(employeeDTO.getUserId(), employeeDTO.getUsername());
            employee.setUsers(user);
        }
        
        // Link Company
        if (employeeDTO.getCompanyId() != null) {
            Long companyId = Long.parseLong(employeeDTO.getCompanyId());
            LogisticsCompany company = logisticsCompanyRepository.findById(companyId)
                    .orElseThrow(() -> new EntityNotFoundException("Company not found with ID: " + companyId));
            employee.setLogisticsCompany(company);
        }
        // Link Office
        if (employeeDTO.getCurrentOfficeId() != null) {
            Long officeId = Long.parseLong(employeeDTO.getCurrentOfficeId());
            Office office = officeRepository.findById(officeId)
                    .orElseThrow(() -> new EntityNotFoundException("Office not found with ID: " + officeId));
            employee.setCurrentOffice(office);
        }
        
        return employeeRepository.save(employee);
    }

    /**
     * Retrieves all employees.
     * @return a list of all employees
     */
    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
    /**
     * Retrieves all employees by company id.
     * @param companyId the id of the company
     * @return a list of all employees by company id
     */
    @Override
    public List<Employee> getAllEmployeesByCompanyId(Long companyId) {
        return employeeRepository.findAllByLogisticsCompanyId(companyId);
    }
    /**
     * Updates an existing employee.
     * @param employeeId the id of the employee to update
     * @param updatedEmployee the updated employee
     */
    @Override
    public void updateEmployee(long employeeId, Employee updatedEmployee) {
        if (!employeeRepository.existsById(employeeId)) {
            throw new EntityNotFoundException("Employee with the provided id doesn't exist");
        }
        updatedEmployee.setId(employeeId);
        employeeRepository.save(updatedEmployee);
    }
    /**
     * Deletes an existing employee.
     * @param employeeId the id of the employee to delete
     */
    @Override
    public void deleteEmployee(long employeeId) {
        if (!employeeRepository.existsById(employeeId)) {
            throw new EntityNotFoundException("Employee with the provided id doesn't exist");
        }
        employeeRepository.deleteById(employeeId);
    }

    /**
     * Assigns an office to an employee.
     * @param employeeId the id of the employee
     * @param office the office to assign
     */
    // Additional method for assigning an office to an employee
    @Override
    public void assignOfficeToEmployee(long employeeId, Office office) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee with the provided id doesn't exist"));
        employee.setCurrentOffice(office);
        employeeRepository.save(employee);
    }
    /**
     * Assigns a logistics company to an employee.
     * @param employeeId the id of the employee
     * @param logisticsCompany the logistics company to assign
     */
    // Additional method for assigning a logistics company to an employee
    @Override
    public void assignLogisticsCompanyToEmployee(long employeeId, LogisticsCompany logisticsCompany) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee with the provided id doesn't exist"));
        employee.setLogisticsCompany(logisticsCompany);
        employeeRepository.save(employee);
    }
    /**
     * Retrieves an employee by id.
     * @param emplpoyeeId the id of the employee
     * @return an Optional containing the employee if it exists, or an empty Optional otherwise
     */
    @Override
    public Optional<Employee> getEmployeeById(long emplpoyeeId) {
        return employeeRepository.findById(emplpoyeeId);
    }
}
