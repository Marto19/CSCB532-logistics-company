package com.logistics.logisticsCompany.service;

import com.logistics.logisticsCompany.entities.users.User;
import com.logistics.logisticsCompany.repository.CustomerRepository;
import com.logistics.logisticsCompany.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * Service for managing user linkages.
 */
@Service
public class UserLinkageServiceImpl implements UserLinkageService {
	
	private final CustomerRepository customerRepository;
	private final EmployeeRepository employeeRepository;
	
	private final UserService userService;

	/**
	 * Constructor for UserLinkageServiceImpl.
	 *
	 * @param customerRepository Repository for managing customers.
	 * @param employeeRepository Repository for managing employees.
	 * @param userService Service for managing users.
	 */
	@Autowired
	public UserLinkageServiceImpl(CustomerRepository customerRepository, EmployeeRepository employeeRepository, UserService userService) {
		this.customerRepository = customerRepository;
		this.employeeRepository = employeeRepository;
		this.userService = userService;
	}

	/**
	 * Finds and validates a user for linkage.
	 *
	 * @param userId The ID of the user.
	 * @param username The username of the user.
	 * @return The found and validated user.
	 * @throws IllegalStateException if the user is already linked to another entity.
	 * @throws IllegalArgumentException if the user does not exist.
	 */
	@Override
	public User findAndValidateUserForLinkage(Long userId, String username) {
		User user = findUser(userId, username);
		
		// Ensure user is not linked to another Customer or Employee
		if (user != null && (customerRepository.existsByUsers_Id(user.getId()) || employeeRepository.existsByUsers_Id(user.getId()))) {
			throw new IllegalStateException("User with ID: " + user.getId() + " is already linked to another entity.");
		}
		
		return user;
	}

	/**
	 * Finds and validates a user for linkage.
	 *
	 * @param userId The ID of the user.
	 * @param username The username of the user.
	 * @return The found and validated user.
	 * @throws IllegalArgumentException if the user does not exist.
	 */
	private User findUser(Long userId, String username) {
		return userId != null ? userService.findById(userId).orElseThrow(() ->
				new IllegalArgumentException("Invalid userId: User does not exist")) :
				username != null ? userService.findByUsername(username).orElseThrow(() ->
						new IllegalArgumentException("Invalid username: User does not exist")) : null;
	}
}