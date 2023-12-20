package com.logistics.logisticsCompany.entities.users;


import javax.persistence.*;

@Entity
@Table(name = "employee")
public class Employee {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int employeeID;
    
    @Column(name = "firstname", nullable = false, length = 50)
    private String firstName;
    
    @Column(name = "secondname", nullable = false, length = 50)
    private String secondName;
    
    @Column(name = "userid", nullable = true, unique = true)
    private Integer userID;
    
    @Column(name = "currentofficeid", nullable = true)
    private Integer currentOfficeID;
    
    //Constructors
    public Employee(){
    }
    
    public Employee(String firstName, String secondName, Integer userID, Integer currentOfficeID){
        this.firstName = firstName;
        this.secondName = secondName;
        this.userID = userID;
        this.currentOfficeID = currentOfficeID;
    }
    
    //Getters and setters
    public String getFirstName(){
        return this.firstName;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public String getSecondName(){
        return this.secondName;
    }

    public void setSecondName(String secondName){
        this.secondName = secondName;
    }
    
    public Integer getUserID() {
        return this.userID;
    }
    
    public void setUserID(Integer userID) {
        this.userID = userID;
    }
    
    public Integer getCurrentOfficeID() {
        return this.currentOfficeID;
    }
    
    public void setCurrentOfficeID(Integer currentOfficeID) {
        this.currentOfficeID = currentOfficeID;
    }
    
    //toString method
    @Override
    public String toString() {
        return "Employee{" +
                "employeeId=" + employeeID +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", userID='" + userID + '\'' +
                ", currentOfficeID='" + currentOfficeID + '\'' +
                '}';
    }
}