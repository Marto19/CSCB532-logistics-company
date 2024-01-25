package com.logistics.logisticsCompany;

import com.logistics.logisticsCompany.controller.CustomerController;
import com.logistics.logisticsCompany.entities.enums.UserRole;
import com.logistics.logisticsCompany.entities.users.Customer;
import com.logistics.logisticsCompany.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Optional;


@SpringBootApplication
public class LogisticsCompanyApplication implements CommandLineRunner {

	private final UserRoleRepository userRoleRepository;

	@Autowired
	public LogisticsCompanyApplication(UserRoleRepository userRoleRepository) {
		this.userRoleRepository = userRoleRepository;
	}
//
//	@Autowired
//  	private OfficeServiceImpl officeService;
	public static void main(String[] args) {
		SpringApplication.run(LogisticsCompanyApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Create a new UserRole
		UserRole userRole = new UserRole("ROLE_USER");
		userRole.setId(1);


		// Check if the role already exists
		Optional<UserRole> existingRole = userRoleRepository.findByUserRole(userRole.getUserRole());

		if (existingRole.isPresent()) {
			System.out.println("Role already exists: " + existingRole.get().getUserRole());
		} else {
			// Save the UserRole
			userRoleRepository.save(userRole);
			System.out.println("Role created: " + userRole.getUserRole());
		}

		// Create a new UserRole for ROLE_EMPLOYEE
		UserRole userRoleEmployee = new UserRole("ROLE_EMPLOYEE");
		userRoleEmployee.setId(2);

		// Check if the role for ROLE_EMPLOYEE already exists
		Optional<UserRole> existingRoleEmployee = userRoleRepository.findByUserRole(userRoleEmployee.getUserRole());

		if (existingRoleEmployee.isPresent()) {
			System.out.println("Role already exists: " + existingRoleEmployee.get().getUserRole());
		} else {
			// Save the UserRole for ROLE_EMPLOYEE
			userRoleRepository.save(userRoleEmployee);
			System.out.println("Role created: " + userRoleEmployee.getUserRole());
		}
	}
	//TODO:fix mappings - done


}
