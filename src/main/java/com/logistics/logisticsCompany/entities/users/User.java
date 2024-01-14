package com.logistics.logisticsCompany.entities.users;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "username", unique = true, nullable = false, length = 50)
	
	private String username;
	@Column(name = "password", nullable = false, length = 50)
	private String password;
	@Column(name = "role", nullable = false, length = 50)
	private String role;
	
	////////////////////////////////CREATING THE RELATIONSHIPS/////////////////////////
    //  relationship user/employee - 1:n
    @OneToMany(mappedBy = "users")
    private Set<Employee> employees = new HashSet<>();

   //relationship user/customer - 1:n
    @OneToMany(mappedBy = "users")
    private Set<Customer> customers = new HashSet<>();
    //TODO: think whether List or Set (martin)
    //set - unique values, list - duplicates allowed (dani)
	
	
	public User() {
	}
	
	public User(String username, String password, String role) {
		this.username = username;
		this.password = password;
		this.role = role;
	}
	
	
}
