package com.logistics.logisticsCompany.repository;

import com.logistics.logisticsCompany.entities.users.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Employee entities.
 */
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    /**
     * Check if an employee exists with the given first name.
     *
     * @param firstName The first name of the employee to check for existence.
     * @return True if an employee with the given first name exists, false otherwise.
     */
    boolean existsByFirstName(String firstName);
    /**
     * Check if an employee exists with the given second name.
     *
     * @param secondName The second name of the employee to check for existence.
     * @return True if an employee with the given second name exists, false otherwise.
     */
    boolean existsBySecondName(String secondName);
    /**
     * Check if an employee exists with the given ID.
     *
     * @param id The ID of the employee to check for existence.
     * @return True if an employee with the given ID exists, false otherwise.
     */
    boolean existsById(long id);
    /**
     * Retrieve all employees associated with a logistics company.
     *
     * @param companyId The ID of the logistics company.
     * @return A list of employees associated with the specified logistics company.
     */
    List<Employee> findAllByLogisticsCompanyId(Long companyId);
    /**
     * Check if an employee exists with the given user ID.
     *
     * @param userId The ID of the user associated with the employee.
     * @return True if an employee with the given user ID exists, false otherwise.
     */
    boolean existsByUsers_Id(Long userId);
    
    Optional<Employee> findByUsersId(Long userId);
}