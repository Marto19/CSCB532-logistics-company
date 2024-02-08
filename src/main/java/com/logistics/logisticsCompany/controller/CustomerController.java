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

/**
 * This class is a controller for handling requests related to customers.
 * It uses Spring's @RestController annotation to indicate that it is a controller and the response bodies should be bound to the web response body.
 * It also uses @RequestMapping to map the web requests.
 */
@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

	/**
	 * The CustomerService instance used for customer-related operations.
	 */
	private final CustomerService customerService;
	/**
	 * The CustomerRepository instance used for customer-related operations.
	 */
	private final CustomerRepository customerRepository;
	/**
	 * The EntityDtoMapper instance used for mapping entities to DTOs and vice versa.
	 */
	private final EntityDtoMapper entityDtoMapper;

	/**
	 * This constructor is used to inject the CustomerService, CustomerRepository and EntityDtoMapper instances.
	 * @param customerService the CustomerService instance
	 * @param customerRepository the CustomerRepository instance
	 * @param entityDtoMapper the EntityDtoMapper instance
	 */
	@Autowired
	public CustomerController(CustomerService customerService, CustomerRepository customerRepository, EntityDtoMapper entityDtoMapper) {
		this.customerService = customerService;
		this.customerRepository = customerRepository;
		this.entityDtoMapper = entityDtoMapper;
	}

	/**
	 * This method handles the POST requests for creating a customer.
	 * @param customerDTO the customer to create
	 * @return a ResponseEntity with the status and a message
	 */
	@PostMapping
	public ResponseEntity<String> createCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
		customerService.createCustomer(customerDTO);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body("Customer created successfully");
	}

	/**
	 * This method handles the GET requests for getting all customers.
	 * @return a ResponseEntity with the status and a message
	 */
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

	/**
	 * This method handles the GET requests for getting a customer by ID.
	 * @param customerId the id of the customer to get
	 * @return a ResponseEntity with the status and a message
	 */
	@GetMapping("/{id}")
	public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable(value = "id") long customerId) {
		//Get customer from service layer, convert to CustomerDTO and convert to ResponseEntity<CustomerDTO>
		return customerService.getCustomerById(customerId)
				.map(entityDtoMapper::convertToCustomerDTO)
				.map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	/**
	 * This method handles the PUT requests for updating a customer.
	 * @param customerId the id of the customer to update
	 * @param updatedCustomer the updated customer
	 * @return a ResponseEntity with the status and a message
	 */
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

	/**
	 * This method handles the DELETE requests for deleting a customer.
	 * @param customerId the id of the customer to delete
	 * @return a ResponseEntity with the status and a message
	 */
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

