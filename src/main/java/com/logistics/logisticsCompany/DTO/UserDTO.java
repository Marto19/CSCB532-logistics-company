package com.logistics.logisticsCompany.DTO;

import com.logistics.logisticsCompany.entities.users.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public class UserDTO {
	private long id;
	@NotBlank(message = "Username is required.")
	private String username;
	
	@NotBlank(message = "Password is required.")
	private String password;
	
	public UserDTO() {
	}
	public UserDTO(long id, String username) {
		this.id = id;
		this.username = username;
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
