package com.logistics.logisticsCompany.service;

import com.logistics.logisticsCompany.DTO.CustomerDTO;
import com.logistics.logisticsCompany.DTO.EntityDtoMapper;
import com.logistics.logisticsCompany.customExceptions.CustomerExistsException;
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
    private final EntityDtoMapper entityDtoMapper;
    
    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, EntityDtoMapper entityDtoMapper) {
        this.customerRepository = customerRepository;
        this.entityDtoMapper = entityDtoMapper;
    }
    
    @Override
    public Customer createCustomer(CustomerDTO customerDTO) {
        if (existsByPhone(customerDTO.getPhone())) {
            throw new CustomerExistsException("Customer with the " + customerDTO.getPhone() + " phone number already exists");
        }
        Customer customer = entityDtoMapper.convertToEntity(customerDTO);
        customer.setBalance(BigDecimal.ZERO); // Initialize balance to zero for new customers
        
        return customerRepository.save(customer);
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
