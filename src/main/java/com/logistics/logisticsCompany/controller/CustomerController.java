package com.logistics.logisticsCompany.controller;


import com.logistics.logisticsCompany.DTO.CustomerDTO;
import com.logistics.logisticsCompany.DTO.EmployeeDTO;
import com.logistics.logisticsCompany.DTO.EntityDtoMapper;
import com.logistics.logisticsCompany.DTO.LogisticsCompanyDTO;
import com.logistics.logisticsCompany.customExceptions.CustomerExistsException;
import com.logistics.logisticsCompany.customExceptions.EntityNotFoundException;
import com.logistics.logisticsCompany.entities.users.Customer;
import com.logistics.logisticsCompany.entities.users.User;
import com.logistics.logisticsCompany.repository.CustomerRepository;
import com.logistics.logisticsCompany.service.CustomerServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.logistics.logisticsCompany.service.CustomerService;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {
	
	private final CustomerService customerService;
	private final CustomerRepository customerRepository;
	private final EntityDtoMapper entityDtoMapper;
	
	@Autowired
	public CustomerController(CustomerService customerService, CustomerRepository customerRepository, EntityDtoMapper entityDtoMapper) {
		this.customerService = customerService;
		this.customerRepository = customerRepository;
		this.entityDtoMapper = entityDtoMapper;
	}
	
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
			
		}
	}
	
	@GetMapping
	public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
		//Get all customers from service layer and convert to List<CustomerDTO>
		List<CustomerDTO> customerDTOs = customerService.getAllCustomers().stream()
				.map(entityDtoMapper::convertToCustomerDTO)
				.collect(Collectors.toList());
		//If the list is empty return NOT_FOUND, else return OK and the customers
		return customerDTOs.isEmpty()
				? new ResponseEntity<>(HttpStatus.NOT_FOUND)
				: new ResponseEntity<>(customerDTOs, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable(value = "id") long customerId) {
		//Get customer from service layer, convert to CustomerDTO and convert to ResponseEntity<CustomerDTO>
		return customerService.getCustomerById(customerId)
				.map(entityDtoMapper::convertToCustomerDTO)
				.map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateCustomer(@PathVariable(value = "id") long customerId,
	                                        @RequestBody Customer updatedCustomer) {
		try {
			customerService.updateCustomer(customerId, updatedCustomer);
			return ResponseEntity.ok("Customer updated successfully");
		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer with the provided id doesn't exist");
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteCustomer(@PathVariable(value = "id") long customerId) {
		try {
			customerService.deleteCustomer(customerId);
			return ResponseEntity.ok("Customer deleted successfully");
		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
}

