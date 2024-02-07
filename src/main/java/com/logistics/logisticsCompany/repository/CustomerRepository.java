package com.logistics.logisticsCompany.repository;

import com.logistics.logisticsCompany.entities.users.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
/**
 * Repository interface for Customer entities.
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    /**
     * Check if a customer exists with the given phone number.
     *
     * @param phone The phone number to check for existence.
     * @return True if a customer with the given phone number exists, false otherwise.
     */
    boolean existsByPhone(String phone);
    /**
     * Check if a customer exists with the given phone number and excluding the specified customer ID.
     *
     * @param phone       The phone number to check for existence.
     * @param customerId  The ID of the customer to exclude from the check.
     * @return True if a customer with the given phone number exists excluding the specified ID, false otherwise.
     */
    boolean existsByPhoneAndIdNot(String phone, long customerId);
    /**
     * Retrieve a customer by phone number.
     *
     * @param phone The phone number to search for.
     * @return An Optional containing the customer if found, otherwise empty.
     */
    // Retrieve a customer by phone number
    Optional<Customer> findByPhone(String phone);
    /**
     * Check if a customer exists with the given ID.
     *
     * @param id The ID of the customer to check for existence.
     * @return True if a customer with the given ID exists, false otherwise.
     */
    boolean existsById(long id);

    /**
     * Check if a customer exists with the given ID.
     *
     * @param customerId The ID of the customer to check for existence.
     * @return True if a customer with the given ID exists, false otherwise.
     */
    boolean existsById(Optional<Long> customerId);


    /**
     * Check if a customer exists with the given user ID.
     *
     * @param userId The ID of the user associated with the customer.
     * @return True if a customer with the given user ID exists, false otherwise.
     */
    boolean existsByUsers_Id(Long userId);
    
}
