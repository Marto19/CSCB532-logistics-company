package com.logistics.logisticsCompany.service;

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
	public boolean isUserLinkedToCustomer(Long userId) {
		return customerRepository.existsByUsers_Id(userId);
	}
	
	@Override
	public boolean isUserLinkedToEmployee(Long userId) {
		return employeeRepository.existsByUsers_Id(userId);
	}
	
	@Override
	public User findAndValidateUserForLinkage(Long userId, String username) {
		User user = findUser(userId, username);
		
		// Ensure user is not linked to another Customer or Employee
		if (user != null && (customerRepository.existsByUsers_Id(user.getId()) || employeeRepository.existsByUsers_Id(user.getId()))) {
			throw new IllegalStateException("User with ID: " + user.getId() + " is already linked to another entity.");
		}
		
		return user;
	}
	
	private User findUser(Long userId, String username) {
		return userId != null ? userService.findById(userId).orElseThrow(() ->
				new IllegalArgumentException("Invalid userId: User does not exist")) :
				username != null ? userService.findByUsername(username).orElseThrow(() ->
						new IllegalArgumentException("Invalid username: User does not exist")) : null;
	}
}