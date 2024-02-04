package com.logistics.logisticsCompany.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.logistics.logisticsCompany.entities.users.Employee;
import jakarta.validation.constraints.NotNull;

public class EmployeeDTO {

    private long id;
    
    @NotNull(message = "First name cannot be null")
    @JsonProperty("firstName")
    private String firstName;
    
    @NotNull(message = "Second name cannot be null")
    @JsonProperty("secondName")
    private String secondName;
    
    @NotNull(message = "Current office name cannot be null")
    @JsonProperty("currentOfficeName")
    private String currentOfficeName;

    // Other fields if needed
    
    public EmployeeDTO() {}
    public EmployeeDTO(Employee employee) {
        this.id = employee.getId();
        this.firstName = employee.getFirstName();
        this.secondName = employee.getSecondName();

        // If you want to include the current office name in the DTO
        if (employee.getCurrentOffice() != null) {
            this.currentOfficeName = employee.getCurrentOffice().getOfficeName();
        }
        // Add other fields as needed
    }

    public EmployeeDTO(long id, String firstName, String secondName) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
    }

    // Getters and setters for the DTO fields

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
