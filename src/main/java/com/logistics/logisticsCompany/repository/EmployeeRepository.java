package com.logistics.logisticsCompany.repository;

import com.logistics.logisticsCompany.entities.users.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    boolean existsByFirstName(String firstName);
    boolean existsBySecondName(String secondName);


}
