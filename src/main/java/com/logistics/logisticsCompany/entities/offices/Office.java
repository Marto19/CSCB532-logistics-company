package com.logistics.logisticsCompany.entities.offices;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.logistics.logisticsCompany.entities.logisticsCompany.LogisticsCompany;
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
	private long id;
	
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
	@JsonManagedReference
	private Set<Shipment> sentFromOffice = new HashSet<>();
	
	@OneToMany(mappedBy = "receiverOffice")
	private Set<Shipment> receivedInOffice = new HashSet<>();


	//creating the relationship between office and logisctics company n:1
	@ManyToOne
	private LogisticsCompany logisticsCompany;
	
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getPostcode() {
		return postcode;
	}

	public void setPostcode(int postcode) {
		this.postcode = postcode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public Set<Shipment> getSentFromOffice() {
		return sentFromOffice;
	}

	public void setSentFromOffice(Set<Shipment> sentFromOffice) {
		this.sentFromOffice = sentFromOffice;
	}

	public Set<Shipment> getReceivedInOffice() {
		return receivedInOffice;
	}

	public void setReceivedInOffice(Set<Shipment> receivedInOffice) {
		this.receivedInOffice = receivedInOffice;
	}

	@Override
	public String toString() {
		return "Office{" +
				"id=" + id +
				", officeName='" + officeName + '\'' +
				", city='" + city + '\'' +
				", postcode=" + postcode +
				", address='" + address + '\'' +
				'}';
	}
}
