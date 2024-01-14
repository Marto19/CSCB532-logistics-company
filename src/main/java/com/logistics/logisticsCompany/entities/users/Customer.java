package com.logistics.logisticsCompany.entities.users;

import com.logistics.logisticsCompany.entities.offices.Office;
import com.logistics.logisticsCompany.entities.orders.Shipment;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "customer")
public class Customer {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "firstName", nullable = false, length = 25)
    private String firstName;
    
    @Column(name = "secondName", nullable = false, length = 25)
    private String secondName;
    
    @Column(name = "phone", nullable = false, length = 13)
    private String phone;
    
    // Many-to-One relationship with User
    @ManyToOne
    @JoinColumn(name = "userId")
    private User users;
    
    // Many-to-One relationship with Office
    @ManyToOne
    @JoinColumn(name = "lastOfficeId")
    private Office lastOffice;
    
    @OneToMany(mappedBy = "senderCustomer")
    private Set<Shipment> sentShipments = new HashSet<>();
    
    @OneToMany(mappedBy = "receiverCustomer")
    private Set<Shipment> receivedShipments = new HashSet<>();
    
    
    //Constructors
    public Customer(){
    }
    
}