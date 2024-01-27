package com.logistics.logisticsCompany.repository;

import com.logistics.logisticsCompany.entities.users.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    boolean existsByPhone(String phone);

    boolean existsByPhoneAndIdNot(String phone, long customerId);
    
    // Retrieve a customer by phone number
    Optional<Customer> findByPhone(String phone);
}
