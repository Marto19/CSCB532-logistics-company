package com.logistics.logisticsCompany.DTO;

import com.logistics.logisticsCompany.entities.users.Customer;
import com.logistics.logisticsCompany.validation.ValidPhoneNumber;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class CustomerDTO {
    private long id;
    private BigDecimal balance;
    
    @NotNull(message = "First name cannot be null")
    private String firstName;
    
    @NotNull(message = "Second name cannot be null")
    private String secondName;
    
    @ValidPhoneNumber
    private String phone;

    // Default constructor
    public CustomerDTO() {
    }

    // Constructor using Customer entity
    public CustomerDTO(Customer customer) {
        this.id = customer.getId();
        this.balance = customer.getBalance();
        this.firstName = customer.getFirstName();
        this.secondName = customer.getSecondName();
        this.phone = customer.getPhone();
        // Map other necessary fields here
    }

    public CustomerDTO(long id,BigDecimal balance, String firstName, String secondName, String phone) {
        this.id = id;
        this.balance = balance;
        this.firstName = firstName;
        this.secondName = secondName;
        this.phone = phone;
    }

    // Getters and setters
    
    
    public BigDecimal getBalance() {
        return balance;
    }
    
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
    
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
