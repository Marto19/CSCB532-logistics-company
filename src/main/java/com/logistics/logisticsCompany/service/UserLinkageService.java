package com.logistics.logisticsCompany.service;

import com.logistics.logisticsCompany.DTO.CustomerDTO;
import com.logistics.logisticsCompany.entities.users.User;

public interface UserLinkageService {
	boolean isUserLinkedToCustomer(Long userId);
	boolean isUserLinkedToEmployee(Long userId);
	
	
	User findAndValidateUserForLinkage(Long userId, String username);
}