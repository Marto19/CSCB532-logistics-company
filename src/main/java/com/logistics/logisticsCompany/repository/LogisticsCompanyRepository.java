package com.logistics.logisticsCompany.repository;

import com.logistics.logisticsCompany.entities.logisticsCompany.LogisticsCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for LogisticsCompany entities.
 */
@Repository
public interface LogisticsCompanyRepository extends JpaRepository<LogisticsCompany, Long> {
    /**
     * Check if a logistics company exists with the given name.
     *
     * @param name The name of the logistics company to check for existence.
     * @return True if a logistics company with the given name exists, false otherwise.
     */
    boolean existsByName(String name);
    /**
     * Check if a logistics company exists with the given ID.
     *
     * @param id The ID of the logistics company to check for existence.
     * @return True if a logistics company with the given ID exists, false otherwise.
     */
    boolean existsById(long id);
    /**
     * Retrieve a logistics company by its ID.
     *
     * @param id The ID of the logistics company to retrieve.
     * @return An Optional containing the logistics company if found, otherwise empty.
     */
    Optional<LogisticsCompany> getLogisticsCompanyById(long id);
    

}
