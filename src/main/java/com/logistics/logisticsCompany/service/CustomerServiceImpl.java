package com.logistics.logisticsCompany.service;

import com.logistics.logisticsCompany.DTO.CustomerDTO;
import com.logistics.logisticsCompany.DTO.EntityDtoMapper;
import com.logistics.logisticsCompany.customExceptions.EntityNotFoundException;
import com.logistics.logisticsCompany.entities.users.Customer;
import com.logistics.logisticsCompany.entities.users.User;
import com.logistics.logisticsCompany.repository.CustomerRepository;
import com.logistics.logisticsCompany.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of the CustomerService interface providing functionality related to customers.
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final EntityDtoMapper entityDtoMapper;
    private final UserService userService;
    private final UserRepository userRepository;
    private final UserLinkageService userLinkageService;

    /**
     * Constructor to initialize CustomerServiceImpl with required dependencies.
     *
     * @param customerRepository   The repository for Customer entities.
     * @param entityDtoMapper      The mapper for DTO and entity conversion.
     * @param userService          The service for user-related operations.
     * @param userRepository       The repository for User entities.
     * @param userLinkageService   The service for linking users to entities.
     */
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

    /**
     * Create a new customer based on the provided DTO.
     *
     * @param customerDTO The DTO containing customer information.
     * @throws EntityNotFoundException If a customer with the provided phone number already exists.
     */
    @Override
    public void createCustomer(CustomerDTO customerDTO) {
        if (existsByPhone(customerDTO.getPhone())) {
            throw new EntityNotFoundException("Customer with the " + customerDTO.getPhone() + " phone number already exists");
        }
        
        //User is set if user id or username is entered---------
        User user = null;
        // If user id or username is entered, find the user and link it to customer if found
        if (customerDTO.getUserId() != null || customerDTO.getUsername() != null) {
            user = userLinkageService.findAndValidateUserForLinkage(customerDTO.getUserId(), customerDTO.getUsername());
            if (user == null) {
                throw new EntityNotFoundException("User not found with ID: " + customerDTO.getUserId() + " or username: " + customerDTO.getUsername());
            }
        }
        
        Customer customer = entityDtoMapper.convertCustomerDtoToEntity(customerDTO);
        customer.setUsers(user); // Link the customer to the user if found
        customer.setBalance(BigDecimal.ZERO); // Initialize balance to zero for new customers
        
        customerRepository.save(customer);
    }

    /**
     * Retrieve all customers.
     *
     * @return A list of all customers.
     */
    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
    
/*    @Override
    public List<Employee> getAllCustomersByCompanyId(Long companyId) {
     //   return customerRepository.findAllByLogisticsCompanyId(companyId);
    }*/

    /**
     * Retrieve a customer by ID.
     *
     * @param customerId The ID of the customer to retrieve.
     * @return An Optional containing the customer if found, otherwise empty.
     */
    @Override
    public Optional<Customer> getCustomerById(long customerId) {
        return customerRepository.findById(customerId);
    }

    /**
     * Retrieve a customer by phone number.
     *
     * @param phoneNumber The phone number of the customer to retrieve.
     * @return The customer entity.
     * @throws EntityNotFoundException If no customer is found with the provided phone number.
     */
    @Override
    public Customer getCustomerByPhoneNumber(String phoneNumber) {
        return customerRepository.findByPhone(phoneNumber)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with phone number: " + phoneNumber));
    }

    /**
     * Update an existing customer.
     *
     * @param customerId     The ID of the customer to update.
     * @param updatedCustomer The updated customer entity.
     * @throws EntityNotFoundException If no customer exists with the provided ID.
     */
    @Override
    public void updateCustomer(long customerId, Customer updatedCustomer) {
        if (!customerRepository.existsById(customerId)) {
            throw new EntityNotFoundException("Customer with the provided id doesn't exist");
        }
        updatedCustomer.setId(customerId);
        customerRepository.save(updatedCustomer);
    }

    /**
     * Delete a customer by ID.
     *
     * @param customerId The ID of the customer to delete.
     * @throws EntityNotFoundException If no customer exists with the provided ID.
     */
    @Override
    public void deleteCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id: " + customerId));
        
        customer.getSentShipments().forEach(shipment -> shipment.setSenderCustomer(null));
        customer.getReceivedShipments().forEach(shipment -> shipment.setReceiverCustomer(null));
        customerRepository.save(customer); // Save the state if necessary
        
        customerRepository.delete(customer);
    }

    /**
     * Check if a customer exists with the given phone number.
     *
     * @param phone The phone number to check for existence.
     * @return True if a customer with the given phone number exists, false otherwise.
     */
    @Override
    public boolean existsByPhone(String phone) {
        return customerRepository.existsByPhone(phone);
    }

    /**
     * Check if a customer exists with the given phone number and excluding the specified customer ID.
     *
     * @param phone       The phone number to check for existence.
     * @param customerId  The ID of the customer to exclude from the check.
     * @return True if a customer with the given phone number exists excluding the specified ID, false otherwise.
     */
    @Override
    public boolean existsByPhoneAndIdNot(String phone, long customerId) {
        return customerRepository.existsByPhoneAndIdNot(phone, customerId);
    }
    /**
     * Retrieve a customer by phone number.
     *
     * @param phone The phone number to search for.
     * @return An Optional containing the customer if found, otherwise empty.
     */
    @Override
    public Optional<Customer> findByPhone(String phone) {
        return customerRepository.findByPhone(phone);
    }

    /**
     * Update a customer entity.
     *
     * @param customer The customer entity to update.
     */
    @Override
    public void updateCustomer(Customer customer) {
        customerRepository.save(customer);
    }
}
