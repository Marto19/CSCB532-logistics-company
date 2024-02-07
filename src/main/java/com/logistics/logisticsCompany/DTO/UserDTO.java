package com.logistics.logisticsCompany.DTO;

import com.logistics.logisticsCompany.entities.users.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;

/**
 * The UserDTO class is used to represent a user data transfer object.
 * It contains the id and username of the user.
 */
public class UserDTO {
	/**
	 * The id of the user.
	 */
	private long id;
	/**
	 * The username of the user.
	 */
	@NotBlank(message = "Username is required.")
	private String username;
	/**
	 * The password of the user.
	 */
	@NotBlank(message = "Password is required.")
	private String password;

	
	@JsonProperty("userRoleBeingSet")
	@Digits(integer = 10, fraction = 0, message = "userrolebeingset must be a number")
	private String userRoleBeingSet;
	
	/**
	 * Default constructor for the {@code UserDTO} class.
	 * It is used to create an empty user data transfer object.
	 * This constructor is used by the Jackson library to create an empty user data transfer object during deserialization.
	 * It should not be used explicitly.
	 */
	public UserDTO() {
	}

	/**
	 * Constructs a UserDTO with the specified id and username.
	 * @param id the id
	 * @param username the username
	 */
	public UserDTO(long id, String username) {
		this.id = id;
		this.username = username;
	}
	
	
	public String getUserRoleBeingSet() {
		return userRoleBeingSet;
	}
	
	public void setUserRoleBeingSet(String userRoleBeingSet) {
		this.userRoleBeingSet = userRoleBeingSet;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public long getId() {
		return id;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
}
