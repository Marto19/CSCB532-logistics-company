package com.logistics.logisticsCompany.entities.users;

import javax.persistence.*;
@Entity
@Table(name = "user")
public class User{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userID;
    
    @Column(name = "username", unique = true, nullable = false, length = 50)
    
    private String username;
    @Column(name = "password", nullable = false, length = 50)
    
    private String password;
    @Column(name = "role", nullable = false, length = 50)
    private String role;
    
    public User(){
    }
    
    public User(String username, String password, String role){
        this.username = username;
        this.password = password;
        this.role = role;
    }
    
    public int getUserID(){
        return this.userID;
    }
    
    public void setUserID(int userID){
        this.userID = userID;
    }
    
    public String getUsername(){
        return this.username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getPassword(){
        return this.password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getRole(){
        return this.role;
    }

    public void setRole(String role){
        this.role = role;
    }
    
    //toString method for debugging purposes only - remove password from toString method for security reasons
    @Override
    public String toString() {
        return "User{" +
                "userId=" + userID +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
