package com.logistics.logisticsCompany.entities.users;

public class Customer {
    private String FirstName;
    private String SecondName;
    private String Phone;
    private int UserID;
    private int LastOfficeID;

    public String getFirstname(){
        return this.FirstName;
    }

    public void setFirstname(String FirstName){
        this.FirstName = FirstName;
    }

    public String getSecondname(){
        return this.SecondName;
    }

    public void setSecondname(String SecondName){
        this.SecondName = SecondName;
    }

    public String getPhone(){
        return this.Phone;
    }

    public void setPhone(String Phone){
        this.Phone = Phone;
    }

    public int getUserid(){
        return this.UserID;
    }

    public void setUserid(int UserID){
        this.UserID = UserID;
    }

    public int getLastofficeid(){
        return this.LastOfficeID;
    }

    public void setLastofficeid(int LastOfficeID){
        this.LastOfficeID = LastOfficeID;
    }
}
