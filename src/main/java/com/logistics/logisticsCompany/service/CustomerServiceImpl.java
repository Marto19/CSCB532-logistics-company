package com.logistics.logisticsCompany.service;

import com.logistics.logisticsCompany.customExceptions.EntityNotFoundException;
import com.logistics.logisticsCompany.entities.users.Customer;
import com.logistics.logisticsCompany.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    
    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    
    @Override
    public void createCustomer(Customer customer) {
        //simple check (ignore it)
        if (customer.getFirstName() == null || customer.getFirstName().isEmpty()) {
            throw new IllegalArgumentException("First name must not be null or empty");
        }
        //works perfectly - balance 0.00
        customer.setBalance(BigDecimal.ZERO); // Set balance explicitly to zero
        customerRepository.save(customer);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Optional<Customer> getCustomerById(long customerId) {
        return customerRepository.findById(customerId);
    }
    
    @Override
    public Customer getCustomerByPhoneNumber(String phoneNumber) {
        return customerRepository.findByPhone(phoneNumber)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with phone number: " + phoneNumber));
    }
    @Override
    public void updateCustomer(long customerId, Customer updatedCustomer) {
        Customer existingCustomer = customerRepository.findById(customerId).orElse(null);
        if (existingCustomer != null) {
            // Set the properties to update
            existingCustomer.setFirstName(updatedCustomer.getFirstName());
            existingCustomer.setSecondName(updatedCustomer.getSecondName());
            existingCustomer.setPhone(updatedCustomer.getPhone());

            // Set other properties as needed

            // Save the updated customer
            customerRepository.save(existingCustomer);
        }
    }

    @Override
    public void deleteCustomer(long customerId) {
        customerRepository.deleteById(customerId);
    }

    @Override
    public boolean existsByPhone(String phone) {
        return customerRepository.existsByPhone(phone);
    }

    @Override
    public boolean existsByPhoneAndIdNot(String phone, long customerId) {
        return customerRepository.existsByPhoneAndIdNot(phone, customerId);
    }
    
    @Override
    public Optional<Customer> findByPhone(String phone) {
        return customerRepository.findByPhone(phone);
    }
    
    @Override
    public void updateCustomer(Customer customer) {
        customerRepository.save(customer);
    }
}
