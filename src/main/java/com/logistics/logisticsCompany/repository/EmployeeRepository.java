package com.logistics.logisticsCompany.repository;

import com.logistics.logisticsCompany.entities.users.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    boolean existsByFirstName(String firstName);
    
    boolean existsBySecondName(String secondName);
    
    boolean existsById(long id);
    
    List<Employee> findAllByLogisticsCompanyId(Long companyId);
}