package com.logistics.logisticsCompany.entities.users;

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

@Entity
@Data
@Table(name = "user")
@Builder
public class User implements UserDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "username", unique = true, nullable = false, length = 50)
	
	private String username;
	@Column(name = "password", nullable = false, length = 50)
	private String password;

	
	////////////////////////////////CREATING THE RELATIONSHIPS/////////////////////////
    //  relationship user/employee - 1:n
    @OneToMany(mappedBy = "users")
    private Set<Employee> employees = new HashSet<>();

   //relationship user/customer - 1:n
    @OneToMany(mappedBy = "users")
    private Set<Customer> customers = new HashSet<>();
    //TODO: think whether List or Set (martin)
    //set - unique values, list - duplicates allowed (dani)

	//creating relationship between the enum-entity table - user_role and user
	//lets consider that one user can have many roles and backwards
	@ManyToMany(mappedBy = "userList", cascade = CascadeType.ALL)
	private Set<UserRole> userRoleList;
	
	public User() {
	}
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
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

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> authorities = new HashSet<>();

		for (UserRole userRole : userRoleList) {
			authorities.add(new SimpleGrantedAuthority(userRole.getUserRole()));
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
				", userRoleList=" + userRoleList +
				'}';
	}
}
