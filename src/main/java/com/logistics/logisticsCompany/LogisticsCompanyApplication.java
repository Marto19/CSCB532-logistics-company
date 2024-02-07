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

/**
 * Main application class for the logistics company.
 */
@SpringBootApplication
public class LogisticsCompanyApplication implements CommandLineRunner {

	private final UserRoleRepository userRoleRepository;

	/**
	 * Constructor for LogisticsCompanyApplication.
	 *
	 * @param userRoleRepository Repository for managing user roles.
	 */
	@Autowired
	public LogisticsCompanyApplication(UserRoleRepository userRoleRepository) {
		this.userRoleRepository = userRoleRepository;
	}

	/**
	 * Method that is run after the application has started.
	 *
	 * @param args Command line arguments.
	 */
	public static void main(String[] args) {
		SpringApplication.run(LogisticsCompanyApplication.class, args);
	}

	/**
	 * This method is executed after the application has started. It creates three user roles: "ROLE_CUSTOMER",
	 * "ROLE_EMPLOYEE", and "ROLE_ADMIN". If a role already exists in the repository, it prints a message indicating
	 * that the role already exists. Otherwise, it saves the new role in the repository and prints a message indicating
	 * that the role was created.
	 *
	 * @param args Command line arguments.
	 * @throws Exception if an error occurs during execution.
	 */
	@Override
	public void run(String... args) throws Exception {
		// Create a new UserRole
		UserRole userRoleCustomer = new UserRole("ROLE_CUSTOMER");
		userRoleCustomer.setId(1);


		// Check if the role already exists
		Optional<UserRole> existingRole = userRoleRepository.findByUserRole(userRoleCustomer.getUserRole());

		if (existingRole.isPresent()) {
			System.out.println("Role already exists: " + existingRole.get().getUserRole());
		} else {
			// Save the UserRole
			userRoleRepository.save(userRoleCustomer);
			System.out.println("Role created: " + userRoleCustomer.getUserRole());
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

		// Create a new UserRole
		UserRole userRoleAdmin = new UserRole("ROLE_ADMIN");
		userRoleAdmin.setId(3);


		// Check if the role already exists
		Optional<UserRole> existingRoleAdmin = userRoleRepository.findByUserRole(userRoleAdmin.getUserRole());

		if (existingRoleAdmin.isPresent()) {
			System.out.println("Role already exists: " + existingRoleAdmin.get().getUserRole());
		} else {
			// Save the UserRole
			userRoleRepository.save(userRoleAdmin);
			System.out.println("Role created: " + userRoleAdmin.getUserRole());
		}
	}
	//TODO:fix mappings - done


}
