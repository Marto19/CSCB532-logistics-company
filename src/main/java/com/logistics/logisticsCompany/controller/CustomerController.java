package com.logistics.logisticsCompany.controller;


import com.logistics.logisticsCompany.DTO.CustomerDTO;
import com.logistics.logisticsCompany.entities.users.Customer;
import com.logistics.logisticsCompany.entities.users.User;
import com.logistics.logisticsCompany.repository.CustomerRepository;
import com.logistics.logisticsCompany.service.CustomerServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    @Autowired
    private CustomerServiceImpl customerService;

    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping
    public ResponseEntity<String> createCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
        try {
            Customer createdCustomer = customerService.createCustomer(customerDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Customer created successfully with ID: " + createdCustomer.getId());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + e.getMessage());
            //public ResponseEntity<String> createCustomer(@RequestBody Customer customer) {
            //    try {
            //        customerService.createCustomer(customer);
            //        return ResponseEntity.status(HttpStatus.CREATED).body("Customer created successfully");
            //    } catch (IllegalArgumentException e) {
            //        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            //    } catch (RuntimeException e) {
            //        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
            //    }
            //}
        }
    }
    @GetMapping
    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        return CustomerDTO.toDTOList(customers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable(value = "id") long customerId) {
        Optional<Customer> customer = customerService.getCustomerById(customerId);
        return customer.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<?> updateCustomer(@PathVariable Optional<Long> customerId, @RequestBody Customer updatedCustomer) {
        if(!customerRepository.existsById(customerId)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Customer with the provided id doesn't exist");
        }
        if (customerId.isPresent()) {
            Long id = customerId.get();
            updatedCustomer.setId(id);
            customerService.updateCustomer(id, updatedCustomer);
            return ResponseEntity.ok("Customer updated successfully");
        } else {
            // Handle the case when customerId is not present
            return ResponseEntity.badRequest().body("customerId is required");
        }
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable(value = "id") long customerId) {
        if (!customerRepository.existsById(customerId)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Customer with the provided id doesn't exist");
        }
        customerService.deleteCustomer(customerId);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Customer deleted successfully");
    }
}

