package com.logistics.logisticsCompany.service;

import com.logistics.logisticsCompany.customExceptions.EntityNotFoundException;
import com.logistics.logisticsCompany.entities.users.User;
import com.logistics.logisticsCompany.repository.CustomerRepository;
import com.logistics.logisticsCompany.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserLinkageServiceImpl implements UserLinkageService {
	
	private final CustomerRepository customerRepository;
	private final EmployeeRepository employeeRepository;
	
	private final UserService userService;
	
	@Autowired
	public UserLinkageServiceImpl(CustomerRepository customerRepository, EmployeeRepository employeeRepository, UserService userService) {
		this.customerRepository = customerRepository;
		this.employeeRepository = employeeRepository;
		this.userService = userService;
	}
	
	@Override
	public User findAndValidateUserForLinkage(String userId, String username) {
		Long userIdValue = null;
		if (userId != null) {
			try {
				userIdValue = Long.parseLong(userId);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("User ID must be a valid number");
			}
		}
		
		User user = findUser(userIdValue, username);
		
		// Ensure user is not linked to another Customer or Employee
		if (user != null && (customerRepository.existsByUsers_Id(user.getId()) || employeeRepository.existsByUsers_Id(user.getId()))) {
			throw new IllegalStateException("User with ID: " + user.getId() + " is already linked to another entity.");
		}
		
		return user;
	}
	
	private User findUser(Long userId, String username) {
		if (userId != null) {
			return userService.findById(userId).orElseThrow(() ->
					new EntityNotFoundException("Invalid userId: User does not exist"));
		} else if (username != null) {
			return userService.findByUsername(username).orElseThrow(() ->
					new EntityNotFoundException("Invalid username: User does not exist"));
		}
		throw new EntityNotFoundException("Both userId and username cannot be null");
	}
}