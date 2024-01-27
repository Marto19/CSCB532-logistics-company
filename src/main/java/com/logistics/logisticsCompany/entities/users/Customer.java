package com.logistics.logisticsCompany.entities.users;

import com.logistics.logisticsCompany.entities.offices.Office;
import com.logistics.logisticsCompany.entities.orders.OrderHistory;
import com.logistics.logisticsCompany.entities.orders.Shipment;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "customer")
public class Customer {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column(name = "first_name", nullable = false, length = 25)
    private String firstName;
    
    @Column(name = "second_name", nullable = false, length = 25)
    private String secondName;
    
    @Column(name = "phone", nullable = false, length = 13)
    private String phone;
    
    // Many-to-One relationship with User
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User users;
    
    // Many-to-One relationship with Office
    @ManyToOne
    @JoinColumn(name = "last_office_id")
    private Office lastOffice;
    
    @OneToMany(mappedBy = "senderCustomer")
    private Set<Shipment> sentShipments = new HashSet<>();
    
    @OneToMany(mappedBy = "receiverCustomer")
    private Set<Shipment> receivedShipments = new HashSet<>();

    @OneToMany(mappedBy = "customer")
    private Set<OrderHistory> orderHistorySet;
    
    
    //Constructors
    public Customer(){
    }

    public Customer(String firstName, String secondName, String phone) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.phone = phone;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public void setUsers(User users) {
        this.users = users;
    }
    
    public void setLastOffice(Office lastOffice) {
        this.lastOffice = lastOffice;
    }
    
    public void setSentShipments(Set<Shipment> sentShipments) {
        this.sentShipments = sentShipments;
    }
    
    public void setReceivedShipments(Set<Shipment> receivedShipments) {
        this.receivedShipments = receivedShipments;
    }
    
    public long getId() {
        return id;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public String getSecondName() {
        return secondName;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public User getUsers() {
        return users;
    }
    
    public Office getLastOffice() {
        return lastOffice;
    }
    
    public Set<Shipment> getSentShipments() {
        return sentShipments;
    }
    
    public Set<Shipment> getReceivedShipments() {
        return receivedShipments;
    }
    
    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
    

}