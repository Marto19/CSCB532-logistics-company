package com.logistics.logisticsCompany.entities.users;

import com.logistics.logisticsCompany.entities.logisticsCompany.LogisticsCompany;
import com.logistics.logisticsCompany.entities.orders.Shipment;
import com.logistics.logisticsCompany.entities.offices.Office;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "second_name", nullable = false, length = 50)
    private String secondName;

    //Many-to-One relationships with User and Office
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User users;

    @ManyToOne
    @JoinColumn(name = "current_office_id")
    private Office currentOffice;
    
    //One-to-Many relationships with Shipment (sender and receiver)
    @OneToMany(mappedBy = "senderEmployee")
    private Set<Shipment> sentShipments = new HashSet<>();
    
    @OneToMany(mappedBy = "receiverEmployee")
    private Set<Shipment> receivedShipments = new HashSet<>();

    //adding relationship between employee-company n:1
    @ManyToOne
    private LogisticsCompany logisticsCompany;



    public Employee(String firstName, String secondName) {
        this.firstName = firstName;
        this.secondName = secondName;
    }

    //Constructors
    public Employee(){
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

    public User getUsers() {
        return users;
    }

    public void setUsers(User users) {
        this.users = users;
    }

    public Office getCurrentOffice() {
        return currentOffice;
    }

    public void setCurrentOffice(Office currentOffice) {
        this.currentOffice = currentOffice;
    }

    public Set<Shipment> getSentShipments() {
        return sentShipments;
    }

    public void setSentShipments(Set<Shipment> sentShipments) {
        this.sentShipments = sentShipments;
    }

    public Set<Shipment> getReceivedShipments() {
        return receivedShipments;
    }

    public void setReceivedShipments(Set<Shipment> receivedShipments) {
        this.receivedShipments = receivedShipments;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                '}';
    }
}