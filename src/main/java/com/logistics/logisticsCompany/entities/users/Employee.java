package com.logistics.logisticsCompany.entities.users;

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
    private int id;

    @Column(name = "firstName", nullable = false, length = 50)
    private String firstName;

    @Column(name = "secondName", nullable = false, length = 50)
    private String secondName;

    //Many-to-One relationships with User and Office
    @ManyToOne
    @JoinColumn(name = "userId")
    private User users;

    @ManyToOne
    @JoinColumn(name = "currentOfficeId")
    private Office currentOffice;
    
    //One-to-Many relationships with Shipment (sender and receiver)
    @OneToMany(mappedBy = "senderEmployee")
    private Set<Shipment> sentShipments = new HashSet<>();
    
    @OneToMany(mappedBy = "receiverEmployee")
    private Set<Shipment> receivedShipments = new HashSet<>();
    

    //Constructors
    public Employee(){
    }
    
}