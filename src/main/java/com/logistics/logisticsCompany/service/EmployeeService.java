package com.logistics.logisticsCompany.service;

import com.logistics.logisticsCompany.entities.logisticsCompany.LogisticsCompany;
import com.logistics.logisticsCompany.entities.offices.Office;
import com.logistics.logisticsCompany.entities.users.Customer;
import com.logistics.logisticsCompany.entities.users.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    void createEmployee(Employee employee);

    List<Employee> getAllEmployees();
	
	List<Employee> getAllEmployeesByCompanyId(Long companyId);
	
	void updateEmployee(long employeeId, Employee updatedEmployee);

    void deleteEmployee(long employeeId);

    void assignOfficeToEmployee(long employeeId, Office office);

    void assignLogisticsCompanyToEmployee(long employeeId, LogisticsCompany logisticsCompany);

    Optional<Employee> getEmployeeById(long emplpoyeeId);

}
