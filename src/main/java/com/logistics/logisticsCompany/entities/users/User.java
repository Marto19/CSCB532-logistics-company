package com.logistics.logisticsCompany.entities.users;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.logistics.logisticsCompany.entities.enums.UserRole;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The User class is used to represent a user entity.
 * It contains the id, username, password, employees, customers, and user role list of the user.
 */
@Entity
@Data
@Table(name = "user")
public class User implements UserDetails {

	/**
	 * The id of the user.
	 * It is a unique identifier for the user.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	/**
	 * The username of the user.
	 */
	@Column(name = "username", unique = true, nullable = false, length = 50)


	private String username;
	
	/**
	 * The password of the user.
	 */
	@Column(name = "password", nullable = false, length = 256)
	private String password;


	////////////////////////////////CREATING THE RELATIONSHIPS/////////////////////////
	/**
	 * The set of employees of the user.
	 * It is a one-to-many relationship between user and employee.
	 */
	//  relationship user/employee - 1:n
	@OneToMany(mappedBy = "users")
	private Set<Employee> employees = new HashSet<>();

	//relationship user/customer - 1:n
	/**
	 * The set of customers of the user.
	 * It is a one-to-many relationship between user and customer.
	 */
	@OneToMany(mappedBy = "users")
	private Set<Customer> customers = new HashSet<>();
	//TODO: think whether List or Set (martin)
	//set - unique values, list - duplicates allowed (dani)

	//creating relationship between the enum-entity table - user_role and user
	//lets consider that one user can have many roles and backwards
	/**
	 * The set of user roles of the user.
	 * It is a many-to-many relationship between user and user role.
	 */
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinTable(
			name = "user_role_user_list",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id")
	)
	@JsonManagedReference
	private Set<UserRole> userRoleList = new HashSet<>();
	
	public User() {
	}

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	@Builder
	public User(String username, String password, Set<Employee> employees, Set<Customer> customers, Set<UserRole> userRoleList) {
		this.username = username;
		this.password = password;
		this.employees = employees;
		this.customers = customers;
		this.userRoleList = userRoleList;
	}


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public void setUsername(String username) {
		this.username = username;
	}

//	@Override
//	public Collection<? extends GrantedAuthority> getAuthorities() {
//		Set<GrantedAuthority> authorities = new HashSet<>();
//
//		for (UserRole userRole : userRoleList) {
//			authorities.add(new SimpleGrantedAuthority(userRole.getUserRole()));
//		}
//
//		return authorities;
//	}


	//modify your getAuthorities method in the User class to initialize the userRoleList
	// collection if it's not already initialized
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> authorities = new HashSet<>();

		if (userRoleList != null) {
			for (UserRole userRole : userRoleList) {
				authorities.add(new SimpleGrantedAuthority(userRole.getUserRole()));
			}
		}

		return authorities;
	}



	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(Set<Employee> employees) {
		this.employees = employees;
	}

	public Set<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(Set<Customer> customers) {
		this.customers = customers;
	}

	public Set<UserRole> getUserRoleList() {
		return userRoleList;
	}

	public void setUserRoleList(Set<UserRole> userRoleList) {
		this.userRoleList = userRoleList;
	}
	
	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", username='" + username + '\'' +
				", password='" + password + '\'' +
				", employees=" + employees +
				", customers=" + customers +
				", userRoleList=" + userRoleList +
				'}';
	}
}