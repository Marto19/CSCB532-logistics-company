package com.logistics.logisticsCompany.DTO;

import com.logistics.logisticsCompany.entities.users.User;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserDTO {
	private long id;
	private String username;
	
	public UserDTO() {
	}
	public UserDTO(long id, String username) {
		this.id = id;
		this.username = username;
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
