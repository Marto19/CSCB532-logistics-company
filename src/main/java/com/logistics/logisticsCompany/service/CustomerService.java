package com.logistics.logisticsCompany.service;

import com.logistics.logisticsCompany.entities.users.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    void createCustomer(Customer customer);

    List<Customer> getAllCustomers();

    Optional<Customer> getCustomerById(long customerId);
    
    Customer getCustomerByPhoneNumber(String phoneNumber);
    
    void updateCustomer(long customerId, Customer updatedCustomer);

    void deleteCustomer(long customerId);

    boolean existsByPhone(String phone);

    boolean existsByPhoneAndIdNot(String phone, long customerId);
    
    Optional<Customer> findByPhone(String phone);
}
