package com.logistics.logisticsCompany.service;

import com.logistics.logisticsCompany.entities.users.Customer;
import com.logistics.logisticsCompany.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public void createCustomer(Customer customer) {
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
}
