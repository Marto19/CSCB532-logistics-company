package com.logistics.logisticsCompany.service;

import com.logistics.logisticsCompany.entities.logisticsCompany.LogisticsCompany;
import com.logistics.logisticsCompany.entities.offices.Office;
import com.logistics.logisticsCompany.entities.users.Employee;

import java.util.List;

public interface EmployeeService {

    void addEmployee(Employee employee);

    List<Employee> getAllEmployees();

    void updateEmployee(long employeeId, Employee updatedEmployee);

    void deleteEmployee(long employeeId);

    void assignOfficeToEmployee(long employeeId, Office office);

    void assignLogisticsCompanyToEmployee(long employeeId, LogisticsCompany logisticsCompany);
}
