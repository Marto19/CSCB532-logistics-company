package com.logistics.logisticsCompany.entities.users;

public class User{
    private String Username;
    private String Password;
    private String Role;

    public String getUsername(){
        return this.Username;
    }

    public void setUsername(String Username){
        this.Username = Username;
    }

    public String getPassword(){
        return this.Password;
    }

    public void setPassword(String Password){
        this.Password = Password;
    }

    public String getRole(){
        return this.Role;
    }

    public void setRole(String Role){
        this.Role = Role;
    }
}
