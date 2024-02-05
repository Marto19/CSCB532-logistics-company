package com.logistics.logisticsCompany.service;

import com.logistics.logisticsCompany.customExceptions.EntityNotFoundException;
import com.logistics.logisticsCompany.entities.logisticsCompany.LogisticsCompany;
import com.logistics.logisticsCompany.entities.offices.Office;
import com.logistics.logisticsCompany.entities.users.Employee;
import com.logistics.logisticsCompany.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void createEmployee(Employee employee) {
        if (employee.getFirstName() == null || employee.getFirstName().isEmpty() || employee.getSecondName() == null || employee.getSecondName().isEmpty()) {
            throw new IllegalArgumentException("Employee name must not be null or empty");
        }

        employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
    
    @Override
    public List<Employee> getAllEmployeesByCompanyId(Long companyId) {
        return employeeRepository.findAllByLogisticsCompanyId(companyId);
    }
    
    @Override
    public void updateEmployee(long employeeId, Employee updatedEmployee) {
        if (!employeeRepository.existsById(employeeId)) {
            throw new EntityNotFoundException("Employee with the provided id doesn't exist");
        }
        updatedEmployee.setId(employeeId);
        employeeRepository.save(updatedEmployee);
    }

    @Override
    public void deleteEmployee(long employeeId) {
        if (!employeeRepository.existsById(employeeId)) {
            throw new EntityNotFoundException("Employee with the provided id doesn't exist");
        }
        employeeRepository.deleteById(employeeId);
    }

    // Additional method for assigning an office to an employee
    @Override
    public void assignOfficeToEmployee(long employeeId, Office office) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee with the provided id doesn't exist"));
        employee.setCurrentOffice(office);
        employeeRepository.save(employee);
    }

    // Additional method for assigning a logistics company to an employee
    @Override
    public void assignLogisticsCompanyToEmployee(long employeeId, LogisticsCompany logisticsCompany) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee with the provided id doesn't exist"));
        employee.setLogisticsCompany(logisticsCompany);
        employeeRepository.save(employee);
    }

    @Override
    public Optional<Employee> getEmployeeById(long emplpoyeeId) {
        return employeeRepository.findById(emplpoyeeId);
    }
}
