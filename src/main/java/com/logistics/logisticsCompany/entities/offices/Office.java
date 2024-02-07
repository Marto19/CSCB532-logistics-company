package com.logistics.logisticsCompany.entities.offices;


import com.logistics.logisticsCompany.entities.logisticsCompany.LogisticsCompany;
import com.logistics.logisticsCompany.entities.orders.Shipment;
import com.logistics.logisticsCompany.entities.users.Customer;
import com.logistics.logisticsCompany.entities.users.Employee;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

/**
 * The Office class is used to represent an office entity.
 * It contains the id, office name, city, postcode, and address of the office.
 */
@Entity
@Table(name = "office")
public class Office {
	/**
	 * The id of the office.
	 * It is a unique identifier for the office.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	/**
	 * The office name of the office.
	 */
	@Column(name = "office_name", nullable = false, length = 50)
	private String officeName;

	/**
	 * The city of the office.
	 */
	@Column(name = "city", nullable = false, length = 50)
	private String city;

	/**
	 * The postcode of the office.
	 */
	@Column(name = "postcode", nullable = false)
	private int postcode;

	/**
	 * The address of the office.
	 */
	@Column(name = "address", nullable = false, length = 250)
	private String address;

	/**
	 * The set of employees of the office.
	 * It is a one-to-many relationship between office and employee.
	 */
	@OneToMany(mappedBy = "currentOffice")
	private Set<Employee> employees = new HashSet<>();

	/**
	 * The set of customers of the office.
	 * It is a one-to-many relationship between office and customer.
	 */
	@OneToMany(mappedBy = "lastOffice")
	private Set<Customer> customers = new HashSet<>();

	/**
	 * The set of shipments sent from the office.
	 * It is a one-to-many relationship between office and shipment.
	 */
	@OneToMany(mappedBy = "senderOffice")
	private Set<Shipment> sentFromOffice = new HashSet<>();

	/**
	 * The set of shipments received in the office.
	 * It is a one-to-many relationship between office and shipment.
	 */
	@OneToMany(mappedBy = "receiverOffice", cascade = CascadeType.REMOVE)// todo :consider putting this cascade type other places too
	private Set<Shipment> receivedInOffice = new HashSet<>();


	//creating the relationship between office and logisctics company n:1
	/**
	 * The logistics company of the office.
	 * It is a many-to-one relationship between office and logistics company.
	 */
	@ManyToOne
	private LogisticsCompany logisticsCompany;
	
	//Constructorsa
	public Office(){
	}

	/**
	 * @param officeName
	 * @param city
	 * @param postcode
	 * @param address
	 */
	public Office(String officeName, String city, int postcode, String address){
		this.officeName = officeName;
		this.city = city;
		this.postcode = postcode;
		this.address = address;
	}
	
	public Office(String officeName, String city, int postcode, String address, Set<Employee> employees, Set<Customer> customers, Set<Shipment> sentFromOffice, Set<Shipment> receivedInOffice, LogisticsCompany logisticsCompany) {
		this.officeName = officeName;
		this.city = city;
		this.postcode = postcode;
		this.address = address;
		this.employees = employees;
		this.customers = customers;
		this.sentFromOffice = sentFromOffice;
		this.receivedInOffice = receivedInOffice;
		this.logisticsCompany = logisticsCompany;
	}
	
	public Office(long id, String officeName, String city, int postcode, String address, Set<Employee> employees, Set<Customer> customers, Set<Shipment> sentFromOffice, Set<Shipment> receivedInOffice, LogisticsCompany logisticsCompany) {
		this.id = id;
		this.officeName = officeName;
		this.city = city;
		this.postcode = postcode;
		this.address = address;
		this.employees = employees;
		this.customers = customers;
		this.sentFromOffice = sentFromOffice;
		this.receivedInOffice = receivedInOffice;
		this.logisticsCompany = logisticsCompany;
	}
	//Getters and setters
	
	public LogisticsCompany getLogisticsCompany() {
		return logisticsCompany;
	}
	
	public void setLogisticsCompany(LogisticsCompany logisticsCompany) {
		this.logisticsCompany = logisticsCompany;
	}
	
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
				", employees=" + employees +
				", customers=" + customers +
				", sentFromOffice=" + sentFromOffice +
				", receivedInOffice=" + receivedInOffice +
				", logisticsCompany=" + logisticsCompany +
				'}';
	}
}
