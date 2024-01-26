package com.logistics.logisticsCompany.controller;

import com.logistics.logisticsCompany.entities.users.Customer;
import com.logistics.logisticsCompany.repository.CustomerRepository;
import com.logistics.logisticsCompany.service.CustomerService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
class CustomerControllerTest {
    
    @Autowired
    private CustomerService customerService;
    
    @Autowired
    private CustomerRepository customerRepository;
    
    //delete after tests?
    @AfterEach
    void tearDown() {
        // Code to clean up database after each test
    }
    
    //no teardown so if we run tests multiple times, we will get errors
    @Test
    void createCustomer() {
        // Arrange
        Customer customer = new Customer();
        customer.setFirstName("FirstName1");
        customer.setSecondName("LastName1");
        customer.setPhone("8888888888");
        
        // Act
        customerService.createCustomer(customer);
        
        // Assert existence
        assertTrue(customerRepository.existsByPhone(customer.getPhone()), "Customer should be found in the database.");
        
        // Retrieve the customer by phone number
        Customer retrievedCustomer = customerRepository.findByPhone(customer.getPhone())
                .orElseThrow(() -> new AssertionError("Customer not found with phone number: " + customer.getPhone()));
        
        // Assert details
        assertNotNull(retrievedCustomer, "Retrieved customer should not be null.");
        assertEquals(customer.getFirstName(), retrievedCustomer.getFirstName(), "First name should match.");
        assertEquals(customer.getSecondName(), retrievedCustomer.getSecondName(), "Last name should match.");
        assertEquals(customer.getPhone(), retrievedCustomer.getPhone(), "Phone should match.");
    }
    
    
    @Test
    void getAllCustomers() {
        // Arrange: Create and save a few customers
        Customer customer1 = new Customer("John", "Doe", "1234567890");
        Customer customer2 = new Customer("Jane", "Doe", "0987654321");
        customerRepository.save(customer1);
        customerRepository.save(customer2);
        
        // Act: Retrieve all customers
        List<Customer> customers = customerService.getAllCustomers();
        
        // Assert: Check if the retrieved customers list contains the saved customers
        assertTrue(customers.contains(customer1));
        assertTrue(customers.contains(customer2));
    }


    @Test
    void getCustomerById() {
        // Arrange: Create and save a customer
        Customer customer = new Customer("John", "Doe", "1234567890");
        customer = customerRepository.save(customer);
        
        // Act: Retrieve the customer by ID
        Customer foundCustomer = customerService.getCustomerById(customer.getId()).orElse(null);
        
        // Assert: Check if the retrieved customer is the one we saved
        assertNotNull(foundCustomer);
        assertEquals(customer.getId(), foundCustomer.getId());
    }
    
    @Test
    void updateCustomer() {
        // Arrange: Create and save a customer
        Customer customer = new Customer("John", "Doe", "1234567890");
        customer = customerRepository.save(customer);
        
        // Act: Update some details of the customer
        customer.setFirstName("Jane");
        customerService.updateCustomer(customer.getId(), customer);
        
        // Assert: Retrieve the customer and check if the details were updated
        Customer updatedCustomer = customerRepository.findById(customer.getId()).orElse(null);
        assertNotNull(updatedCustomer);
        assertEquals("Jane", updatedCustomer.getFirstName());
    }
    
    @Test
    void deleteCustomer() {
        // Arrange: Create and save a customer
        Customer customer = new Customer("John", "Doe", "1234567890");
        customer = customerRepository.save(customer);
        
        // Act: Delete the customer
        customerService.deleteCustomer(customer.getId());
        
        // Assert: Check if the customer no longer exists in the repository
        boolean exists = customerRepository.existsById(customer.getId());
        assertFalse(exists);
    }
}