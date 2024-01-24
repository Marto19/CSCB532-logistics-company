package com.logistics.logisticsCompany.DTO;

import com.logistics.logisticsCompany.entities.users.Customer;

import java.util.List;
import java.util.stream.Collectors;

public class CustomerDTO {
    private long id;
    private String firstName;
    private String secondName;
    private String phone;

    // Default constructor
    public CustomerDTO() {
    }

    // Constructor using Customer entity
    public CustomerDTO(Customer customer) {
        this.id = customer.getId();
        this.firstName = customer.getFirstName();
        this.secondName = customer.getSecondName();
        this.phone = customer.getPhone();
        // Map other necessary fields here
    }

    // Getters and setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    // Additional methods if needed

    public static List<CustomerDTO> toDTOList(List<Customer> customers) {
        return customers.stream().map(CustomerDTO::new).collect(Collectors.toList());
    }
}
