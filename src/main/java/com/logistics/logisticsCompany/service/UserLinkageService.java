package com.logistics.logisticsCompany.service;

import com.logistics.logisticsCompany.DTO.CustomerDTO;
import com.logistics.logisticsCompany.entities.users.User;

public interface UserLinkageService {
	
	User findAndValidateUserForLinkage(String userId, String username);
}