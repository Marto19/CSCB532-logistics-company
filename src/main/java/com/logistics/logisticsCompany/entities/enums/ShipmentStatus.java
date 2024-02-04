package com.logistics.logisticsCompany.entities.enums;

import com.logistics.logisticsCompany.entities.orders.ShipmentStatusHistory;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "shipment_status")   //this table is an enum, it will contain: REGISTERED,SENT, SHIPPED, DELIVERED
public class ShipmentStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "shipment_status")
    private String shipmentStatus;

    @OneToMany(mappedBy = "shipmentStatus")
    private Set<ShipmentStatusHistory> shipmentStatusHistorySet;


    public ShipmentStatus(String shipmentStatus) {
        this.shipmentStatus = shipmentStatus;
    }

    public ShipmentStatus(){}
    
    

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getShipmentStatus() {
        return shipmentStatus;
    }

    public void setShipmentStatus(String shipmentStatus) {
        this.shipmentStatus = shipmentStatus;
    }

    @Override
    public String toString() {
        return "ShipmentStatus{" +
                "id=" + id +
                ", shipmentStatus='" + shipmentStatus + '\'' +
                '}';
    }
}