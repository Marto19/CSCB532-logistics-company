package com.logistics.logisticsCompany.entities;


import javax.persistence.*;

@Entity
@Table(name = "customer")
public class Customer {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "firstname", nullable = false, length = 25)
    private String firstName;
    @Column(name = "secondname", nullable = false, length = 25)
    private String secondName;
    @Column(name = "phone", nullable = false, length = 13)
    private String phone;
    @Column(name = "userid", nullable = true, unique = true, length = 20)
    private Integer userID;
    @Column(name = "lastofficeid", nullable = true, length = 20)
    private Integer lastOfficeID;

    ////////////////////////////////CREATING THE RELATIONSHIPS/////////////////////////
    //relationship office/customer - 1:n
    @ManyToOne
    @JoinColumn(name = "last_office_id")
    private Office office;

    //relationship customer/user - n:1
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    //Constructors
    public Customer(){
    }
    
    public Customer(String firstName, String secondName, String phone, Integer userID, Integer lastOfficeID){
        this.firstName = firstName;
        this.secondName = secondName;
        this.phone = phone;
        this.userID = userID;
        this.lastOfficeID = lastOfficeID;
    }
    
    
    //Getters and setters
    public int getId(){
        return this.id;
    }
    
    public void setId(int id){
        this.id = id;
    }
    
    public String getFirstname(){
        return this.firstName;
    }

    public void setFirstname(String firstName){
        this.firstName = firstName;
    }

    public String getSecondName(){
        return this.secondName;
    }

    public void setSecondName(String secondName){
        this.secondName = secondName;
    }

    public String getPhone(){
        return this.phone;
    }

    public void setPhone(String phone){
        this.phone = phone;
    }

    public Integer getUserID(){
        return this.userID;
    }

    public void setUserID(Integer userID){
        this.userID = userID;
    }

    public Integer getLastOfficeID(){
        return this.lastOfficeID;
    }

    public void setLastOfficeID(Integer lastOfficeID){
        this.lastOfficeID = lastOfficeID;
    }
    
    //toString method
    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", phone='" + phone + '\'' +
                ", userID=" + userID +
                ", lastOfficeID=" + lastOfficeID +
                '}';
    }
}