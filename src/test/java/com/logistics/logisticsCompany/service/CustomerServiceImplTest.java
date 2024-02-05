package com.logistics.logisticsCompany.service;

import com.logistics.logisticsCompany.controller.CustomerController;
import com.logistics.logisticsCompany.entities.users.Customer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerServiceImplTest {

    @Test
    void createCustomer() {
        Customer customer = new Customer("FirstName1", "LastName1", "8888888888");
       // CustomerServiceImpl customerService = new CustomerServiceImpl();
        //customerService.createCustomer(customer);
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

    @Test
    void existsByPhone() {
    }

    @Test
    void existsByPhoneAndIdNot() {
    }
}