package com.logistics.logisticsCompany.entities.users;

public class Employee {
    private String FirstName;
    private String SecondName;
    private int UserID;
    private int CurrentOfficeID;

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

    public int getUserid(){
        return this.UserID;
    }

    public void setUserid(int UserID){
        this.UserID = UserID;
    }

    public int getCurrentofficeid(){
        return this.CurrentOfficeID;
    }

    public void setCurrentofficeid(int CurrentOfficeID){
        this.CurrentOfficeID = CurrentOfficeID;
    }
}
