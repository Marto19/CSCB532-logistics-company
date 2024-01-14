package com.logistics.logisticsCompany.entities.offices;


import com.logistics.logisticsCompany.entities.orders.Shipment;
import com.logistics.logisticsCompany.entities.users.Customer;
import com.logistics.logisticsCompany.entities.users.Employee;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "office")
public class Office {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "office_name", nullable = false, length = 50)
	private String officeName;
	
	@Column(name = "city", nullable = false, length = 50)
	private String city;
	
	@Column(name = "postcode", nullable = false)
	private int postcode;
	
	@Column(name = "address", nullable = false, length = 250)
	private String address;
	
	@OneToMany(mappedBy = "currentOffice")
	private Set<Employee> employees = new HashSet<>();
	
	@OneToMany(mappedBy = "lastOffice")
	private Set<Customer> customers = new HashSet<>();
	
	@OneToMany(mappedBy = "senderOffice")
	private Set<Shipment> sentFromOffice = new HashSet<>();
	
	@OneToMany(mappedBy = "receiverOffice")
	private Set<Shipment> receivedInOffice = new HashSet<>();
	
	//Constructorsa
	public Office(){
	}

	public Office(String officeName, String city, int postcode, String address){
		this.officeName = officeName;
		this.city = city;
		this.postcode = postcode;
		this.address = address;
	}

	//Getters and setters
	
}
