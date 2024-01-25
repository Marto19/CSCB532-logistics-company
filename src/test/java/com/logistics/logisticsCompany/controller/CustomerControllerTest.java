package com.logistics.logisticsCompany.controller;

import com.logistics.logisticsCompany.entities.users.Customer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerControllerTest {

    @Test
    void createCustomer() {
        Customer customer = new Customer("FirstName1", "LastName1", "8888888888");
        CustomerController customerController = new CustomerController();
        customerController.createCustomer(customer);
    }

    @Test
    void getAllCustomers() {
    }

    @Test
    void getCustomerById() {
    }

    @Test
    void updateCustomer() {
    }

    @Test
    void deleteCustomer() {
    }
}