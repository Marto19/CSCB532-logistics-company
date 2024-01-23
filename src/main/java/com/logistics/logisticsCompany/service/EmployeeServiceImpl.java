package com.logistics.logisticsCompany.service;

import com.logistics.logisticsCompany.customExceptions.EntityNotFoundException;
import com.logistics.logisticsCompany.entities.logisticsCompany.LogisticsCompany;
import com.logistics.logisticsCompany.entities.offices.Office;
import com.logistics.logisticsCompany.entities.users.Employee;
import com.logistics.logisticsCompany.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void addEmployee(Employee employee) {
        employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public void updateEmployee(long employeeId, Employee updatedEmployee) {
        if (employeeRepository.existsById(employeeId)) {
            updatedEmployee.setId(employeeId);
            employeeRepository.save(updatedEmployee);
        } else {
            throw new EntityNotFoundException("Employee with ID " + employeeId + " not found.");
        }
    }

    @Override
    public void deleteEmployee(long employeeId) {
        if (employeeRepository.existsById(employeeId)) {
            employeeRepository.deleteById(employeeId);
        } else {
            throw new EntityNotFoundException("Employee with ID " + employeeId + " not found.");
        }
    }

    // Additional method for assigning an office to an employee
    @Override
    public void assignOfficeToEmployee(long employeeId, Office office) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee with ID " + employeeId + " not found."));
        employee.setCurrentOffice(office);
        employeeRepository.save(employee);
    }

    // Additional method for assigning a logistics company to an employee
    @Override
    public void assignLogisticsCompanyToEmployee(long employeeId, LogisticsCompany logisticsCompany) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee with ID " + employeeId + " not found."));
        employee.setLogisticsCompany(logisticsCompany);
        employeeRepository.save(employee);
    }
}
