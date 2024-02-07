package com.logistics.logisticsCompany.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.logistics.logisticsCompany.entities.users.Customer;
import com.logistics.logisticsCompany.validation.ValidPhoneNumber;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.NumberFormat;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The CustomerDTO class is used to represent a customer data transfer object.
 * It contains the customer's id, balance, first name, second name, phone, user id, and username.
 */
public class CustomerDTO {
    /**
     * The id of the customer.
     */
    private long id;
    /**
     * The balance of the customer.
     */
    private BigDecimal balance;
    /**
     * The first name of the customer.
     */
    @NotBlank(message = "First name cannot be null")
    private String firstName;
    /**
     * The second name of the customer.
     */
    @NotBlank(message = "Second name cannot be null")
    private String secondName;
    /**
     * The phone number of the customer.
     */
    @ValidPhoneNumber
    private String phone;

    /**
     * The user id of the customer.
     */
    //TODO make it as string and then convert it to Long
    //so there can be validation if string is entered instead of number (long cant be tracked)
    @JsonProperty("userId")
    @Digits(integer = 10, fraction = 0, message = "User id must be a number")
    private String userId;  //user id input OPTIONAL
    /**
     * The username of the customer.
     */
    @JsonProperty("username")
    private String username; //username input OPTIONAL

    /**
     * Default constructor for the {@code CustomerDTO} class.
     */
    // Default constructor
    public CustomerDTO() {
    }

    /**
     * Constructor for the {@code CustomerDTO} class using a {@code Customer} entity.
     * @param customer the {@code Customer} entity
     */
    // Constructor using Customer entity
    public CustomerDTO(Customer customer) {
        this.id = customer.getId();
        this.balance = customer.getBalance();
        this.firstName = customer.getFirstName();
        this.secondName = customer.getSecondName();
        this.phone = customer.getPhone();
        
        this.username = customer.getUsers().getUsername();
        
        // Map other necessary fields here
    }

    /**
     * Constructor for the {@code CustomerDTO} class using all fields.
     * @param id the id of the customer
     * @param balance the balance of the customer
     * @param firstName the first name of the customer
     * @param secondName the second name of the customer
     * @param phone the phone number of the customer
     * @param userId the user id of the customer
     * @param username the username of the customer
     */
    public CustomerDTO(long id,BigDecimal balance, String firstName, String secondName, String phone, String userId, String username) {
        this.id = id;
        this.balance = balance;
        this.firstName = firstName;
        this.secondName = secondName;
        this.phone = phone;
        this.userId = userId;
        this.username = username;
        
    }

    // Getters and setters

    // Getters and setters

    /**
     * Gets the balance of the customer.
     * @return the balance of the customer
     */
    public BigDecimal getBalance() {
        return balance;
    }
    /**
     * Sets the balance of the customer.
     * @param balance the balance of the customer
     */
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
    /**
     * Gets the id of the customer.
     * @return the id of the customer
     */
    public long getId() {
        return id;
    }
    /**
     * Sets the id of the customer.
     * @param id the id of the customer
     */
    public void setId(long id) {
        this.id = id;
    }
    /**
     * Gets the user id of the customer.
     * @return the user id of the customer
     */
    public String getUserId() {
        return userId;
    }
    /**
     * Sets the user id of the customer.
     * @param userId the user id of the customer
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }
    /**
     * Sets the username of the customer.
     * @param username the username of the customer
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the username of the customer.
     * @return the username of the customer
     */
    public String getUsername() {
        return username;
    }
    /**
     * Gets the first name of the customer.
     * @return the first name of the customer
     */
    public String getFirstName() {
        return firstName;
    }
    /**
     * Sets the first name of the customer.
     * @param firstName the first name of the customer
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    /**
     * Gets the second name of the customer.
     * @return the second name of the customer
     */
    public String getSecondName() {
        return secondName;
    }
    /**
     * Sets the second name of the customer.
     * @param secondName the second name of the customer
     */
    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }
    /**
     * Gets the phone number of the customer.
     * @return the phone number of the customer
     */
    public String getPhone() {
        return phone;
    }
    /**
     * Sets the phone number of the customer.
     * @param phone the phone number of the customer
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    // Additional methods if needed
    /**
     * Converts a list of {@code Customer} entities to a list of {@code CustomerDTO}.
     * @param customers the list of {@code Customer} entities
     * @return the list of {@code CustomerDTO}
     */
    public static List<CustomerDTO> toDTOList(List<Customer> customers) {
        return customers.stream().map(CustomerDTO::new).collect(Collectors.toList());
    }
}
