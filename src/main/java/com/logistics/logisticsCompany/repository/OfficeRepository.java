package com.logistics.logisticsCompany.repository;

import com.logistics.logisticsCompany.entities.logisticsCompany.LogisticsCompany;
import com.logistics.logisticsCompany.entities.offices.Office;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
/**
 * Repository interface for Office entities.
 */
@Repository
public interface OfficeRepository extends JpaRepository<Office, Long> {
/**
 * Check if an office exists with the given office name.
 *
 * @param officeName The name of the office to check
 * @return True if an office with the given name exists, false otherwise.
 */

    boolean existsByOfficeName(String officeName);
    /**
     * Check if an office exists with the given address.
     *
     * @param address The address of the office to check
     * @return True if an office with the given address exists, false otherwise.
     */
    boolean existsByAddress(String address);
    /**
     * Check if an office exists with the given ID.
     *
     * @param id The ID of the office to check
     * @return True if an office with the given ID exists, false otherwise.
     */
    boolean existsById(long id);
    /**
     * Retrieve an office by its ID.
     *
     * @param id The ID of the office to retrieve
     * @return An Optional containing the office if found, otherwise empty.
     */
    Optional<Office> getOfficeById(long id);


    /**
     * Retrieve an office by its id.
     * @param id must not be {@literal null}.
     * @return
     */
    Optional<Office> findById(Long id);//i want to use this..
    
    Optional<Office> findByEmployeesId(Long employeeId);
}
