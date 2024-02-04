package com.logistics.logisticsCompany.service;

import com.logistics.logisticsCompany.customExceptions.EntityNotFoundException;
import com.logistics.logisticsCompany.entities.users.Customer;
import com.logistics.logisticsCompany.entities.users.Employee;
import com.logistics.logisticsCompany.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        //Check if a customer with the given phone already exists
        if (this.existsByPhone(customer.getPhone())) {
            throw new IllegalArgumentException("Customer with the provided phone number already exists");
        }
        //works perfectly - balance 0.00
        customer.setBalance(BigDecimal.ZERO); // Set balance explicitly to zero

        customerRepository.save(customer);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
    
/*    @Override
    public List<Employee> getAllCustomersByCompanyId(Long companyId) {
     //   return customerRepository.findAllByLogisticsCompanyId(companyId);
    }*/
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
        if (!customerRepository.existsById(customerId)) {
            throw new EntityNotFoundException("Customer with the provided id doesn't exist");
        }
        updatedCustomer.setId(customerId);
        customerRepository.save(updatedCustomer);
    }

    @Override
    public void deleteCustomer(long customerId) {
        if (!customerRepository.existsById(customerId)){
            throw new EntityNotFoundException("Customer with the provided id doesn't exist");
        }
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
