package com.logistics.logisticsCompany.service;

import com.logistics.logisticsCompany.DTO.CustomerDTO;
import com.logistics.logisticsCompany.DTO.EntityDtoMapper;
import com.logistics.logisticsCompany.customExceptions.CustomerExistsException;
import com.logistics.logisticsCompany.customExceptions.EntityNotFoundException;
import com.logistics.logisticsCompany.entities.users.Customer;
import com.logistics.logisticsCompany.entities.users.User;
import com.logistics.logisticsCompany.repository.CustomerRepository;
import com.logistics.logisticsCompany.repository.UserRepository;
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
    private final EntityDtoMapper entityDtoMapper;
    private final UserService userService;
    private final UserRepository userRepository;
    private final UserLinkageService userLinkageService;
    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, EntityDtoMapper entityDtoMapper, UserService userService, UserRepository userRepository, UserLinkageService userLinkageService) {
        this.customerRepository = customerRepository;
        this.entityDtoMapper = entityDtoMapper;
        this.userService = userService;
        this.userRepository = userRepository;
        this.userLinkageService = userLinkageService;
        
    }
    //Behind the creation of customer,
    //check if user is linked to another entity (either customer or employee)
    //we check if phone number already exists in the database
    //we convert the entered customerDTO to customer entity
    //if user id or username is entered, we find the user and link the customer to the user
    //customer can be created without user id or username
    //we initialize the balance of the customer to zero
    //we save the customer to the database
    @Override
    public Customer createCustomer(CustomerDTO customerDTO) {
        if (existsByPhone(customerDTO.getPhone())) {
            throw new EntityNotFoundException("Customer with the " + customerDTO.getPhone() + " phone number already exists");
        }
        
        User user = null;
        // Use UserLinkageService to find and validate the user for linkage
        if (customerDTO.getUserId() != null || customerDTO.getUsername() != null) {
            user = userLinkageService.findAndValidateUserForLinkage(customerDTO.getUserId(), customerDTO.getUsername());
            if (user == null) {
                throw new EntityNotFoundException("User not found with ID: " + customerDTO.getUserId() + " or username: " + customerDTO.getUsername());
            }
        }
        Customer customer = entityDtoMapper.convertToEntity(customerDTO);
        customer.setUsers(user); // Link the customer to the user if found
        customer.setBalance(BigDecimal.ZERO); // Initialize balance to zero for new customers
        
        return customerRepository.save(customer);

        ////Check if a customer with the given phone already exists
        //if (this.existsByPhone(customer.getPhone())) {
        //    throw new IllegalArgumentException("Customer with the provided phone number already exists");
        //}
        ////works perfectly - balance 0.00
        //customer.setBalance(BigDecimal.ZERO); // Set balance explicitly to zero

        //customerRepository.save(customer);
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
