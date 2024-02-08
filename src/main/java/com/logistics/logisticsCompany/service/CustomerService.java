package com.logistics.logisticsCompany.service;

import com.logistics.logisticsCompany.DTO.CustomerDTO;
import com.logistics.logisticsCompany.entities.users.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    
    
    void createCustomer(CustomerDTO customerDTO);
    
    List<Customer> getAllCustomers();
    
    //List<Employee> getAllCustomersByCompanyId(Long companyId);
    
    Optional<Customer> getCustomerById(long customerId);
    
    Customer getCustomerByPhoneNumber(String phoneNumber);
    
    void updateCustomer(long customerId, Customer updatedCustomer);
    
    void deleteCustomer(Long customerId);
    
    boolean existsByPhone(String phone);

    boolean existsByPhoneAndIdNot(String phone, long customerId);
    
    Optional<Customer> findByPhone(String phone);
    
    void updateCustomer(Customer customer);
}
