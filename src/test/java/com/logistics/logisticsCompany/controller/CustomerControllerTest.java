/*
package com.logistics.logisticsCompany.controller;

import com.logistics.logisticsCompany.DTO.CustomerDTO;
import com.logistics.logisticsCompany.entities.users.Customer;
import com.logistics.logisticsCompany.repository.CustomerRepository;
import com.logistics.logisticsCompany.service.CustomerService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;


@SpringBootTest
class CustomerControllerTest {

    @Autowired
    private CustomerController customerController;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerRepository customerRepository;

    //Temp customers for tests
    Customer customer1, customer2;

    @BeforeEach
    void setUp() {
        customer1 = new Customer("FirstName1", "LastName1", "8888888888");
        customer2 = new Customer("FirstName1", "LastName1", "9999999999");

        customerController.createCustomer(customer1);
        customerController.createCustomer(customer2);
    }

    @AfterEach
    void tearDown() {
        customerController.deleteCustomer(customer1.getId());
        customerController.deleteCustomer(customer2.getId());
    }

    @Test
    void createCustomer() {
        //Checks if customer was created successfully
        assertEquals(customerController.createCustomer(customer1), ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Customer with the provided phone number already exists"));

        //Checks if database contains customer with given phone number
        assertTrue(customerRepository.existsByPhone(customer1.getPhone()));
    }
    
    
    @Test
    void getAllCustomers() {
        //Retrieves all customers
        List<CustomerDTO> customers = customerController.getAllCustomers().getBody();
        
        //Checks if the retrieved customers list contains the saved customers
        assertEquals(customers.get(0).getPhone(), customer1.getPhone());
        assertEquals(customers.get(1).getPhone(), customer2.getPhone());
    }


    @Test
    void getCustomerById() {
        //Retrieves the customer by ID
        Customer foundCustomer = customerController.getCustomerById(customer1.getId()).getBody();

        //Checks if the retrieved customer is in the database
        assertNotNull(foundCustomer);
        assertEquals(customer1.getId(), foundCustomer.getId());
    }
    
    @Test
    void updateCustomer() {
        //Updates customer's name
        customer1.setFirstName("Gosho");
        customerService.updateCustomer(customer1.getId(), customer1);
        
        //Retrieves the customer and checks if the name was updated
        Customer updatedCustomer = customerController.getCustomerById(customer1.getId()).getBody();
        assertNotNull(updatedCustomer);
        assertEquals("Gosho", updatedCustomer.getFirstName());
    }
    
    @Test
    void deleteCustomer() {
        //Deletes the customer
        customerService.deleteCustomer(customer1.getId());
        
        //Checks if the customer no longer exists in the database
        boolean exists = customerController.getCustomerById(customer1.getId()).hasBody();
        assertFalse(exists);
    }
}*/
