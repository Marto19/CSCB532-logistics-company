package com.logistics.logisticsCompany.entities.offices;


import com.logistics.logisticsCompany.entities.users.Employee;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "office")
public class Office {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int officeID;
	
	@Column(name = "office_name", nullable = false, length = 50)//
	private String officeName;
	
	@Column(name="city", nullable = false, length = 50)
	private String city;
	
	@Column(name="postcode", nullable = false)
	private int postcode;
	
	@Column(name="address", nullable = false, length = 250)
	private String address;

	////////////////////////////////CREATING THE RELATIONSHIPS/////////////////////////
	//relationship office/employee - 1:n
	@OneToMany(mappedBy = "office")
	private Set<Employee> employeeSet = new HashSet<>();

	
	//Constructors
	public Office(){
	}
	
	public Office(String officeName, String city, int postcode, String address){
		this.officeName = officeName;
		this.city = city;
		this.postcode = postcode;
		this.address = address;
	}
	
	//Getters and setters
	public int getOfficeID(){
		return this.officeID;
	}
	
	public void setOfficeID(int officeID){
		this.officeID = officeID;
	}
	
	public String getOfficeName(){
		return this.officeName;
	}
	
	public void setOfficeName(String officeName){
		this.officeName = officeName;
	}
	
	public String getCity(){
		return this.city;
	}
	
	public void setCity(String city){
		this.city = city;
	}
	
	public int getPostcode(){
		return this.postcode;
	}
	
	public void setPostcode(int postcode){
		this.postcode = postcode;
	}
	
	public String getAddress(){
		return this.address;
	}
	
	public void setAddress(String address){
		this.address = address;
	}
	
	//toString method
	@Override
	public String toString() {
		return "Office{" +
				"officeID=" + officeID +
				", officeName='" + officeName + '\'' +
				", city='" + city + '\'' +
				", postcode='" + postcode + '\'' +
				", address='" + address + '\'' +
				'}';
	}
}
