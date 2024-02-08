package com.logistics.logisticsCompany.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.logistics.logisticsCompany.entities.users.Employee;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * The EmployeeDTO class is used to represent an employee data transfer object.
 * It contains the employee's id, first name, second name, and current office name.
 */
public class EmployeeDTO {
    /**
     * The id of the employee.
     */
    private long id;

    /**
     * The first name of the employee.
     */
    @NotNull(message = "First name cannot be null")
    @JsonProperty("firstName")
    private String firstName;

    /**
     * The second name of the employee.
     */
    @NotNull(message = "Second name cannot be null")
    @JsonProperty("secondName")
    private String secondName;
    
    /**
     * The name of the current office of the employee.
     */
    @JsonProperty("currentOfficeName")
    private String currentOfficeName;
    
    
    @JsonProperty("userId")
    @NotBlank(message = "User id cannot be null")
    @Digits(integer = 10, fraction = 0, message = "User id must be a number")
    private String userId;  //user id input OPTIONAL
    
    @JsonProperty("username")
    private String username; //username input OPTIONAL
    
    @JsonProperty("companyId")
    @NotBlank
    @Digits(integer = 10, fraction = 0, message = "Company id must be a number")
    private String companyId;  //company must
    
    @JsonProperty("companyName")
    private String companyName;
    
    @JsonProperty("currentOfficeId")
    @NotBlank
    @Digits(integer = 10, fraction = 0, message = "Office id must be a number")
    private String currentOfficeId;  //office id
    
    

    
    
    /**
     * Default constructor for the {@code EmployeeDTO} class.
     * It is used to create an empty employee data transfer object.
     * This constructor is used by the Jackson library to create an empty employee data transfer object during deserialization.
     * It should not be used explicitly.
     */
    
    public EmployeeDTO() {}

    /**
     * Constructs an EmployeeDTO with the specified employee.
     * @param employee the employee
     */
    public EmployeeDTO(Employee employee) {
        this.id = employee.getId();
        this.firstName = employee.getFirstName();
        this.secondName = employee.getSecondName();

  
        if (employee.getCurrentOffice() != null) {
            this.currentOfficeName = employee.getCurrentOffice().getOfficeName();
        }
    }

    /**
     * Constructs an EmployeeDTO with the specified id, first name, and second name.
     * @param id the id
     * @param firstName the first name
     * @param secondName the second name
     */
    public EmployeeDTO(long id, String firstName, String secondName) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
    }

    // Getters and setters for the DTO fields
    
    
    public String getCompanyName() {
        return companyName;
    }
    
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
    
    public void setCurrentOfficeId(String currentOfficeId) {
        this.currentOfficeId = currentOfficeId;
    }
    
    public String getUserId() {
        return userId;
    }
    
    public String getUsername() {
        return username;
    }
    
    public String getCompanyId() {
        return companyId;
    }
    
    public String getCurrentOfficeId() {
        return currentOfficeId;
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

    public String getCurrentOfficeName() {
        return currentOfficeName;
    }

    public void setCurrentOfficeName(String currentOfficeName) {
        this.currentOfficeName = currentOfficeName;
    }

    // Add other getters and setters as needed
}
