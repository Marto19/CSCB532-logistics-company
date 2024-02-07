package com.logistics.logisticsCompany.entities.enums;

import com.logistics.logisticsCompany.entities.orders.ShipmentStatusHistory;
import jakarta.persistence.*;

import java.util.Set;

/**
 * The ShipmentStatus class is used to represent a shipment status entity.
 * It contains the id and shipment status of the shipment status.
 * Its supposed to be an enum but I made it an entity to be able to add more shipment statuses in the future.
 */
@Entity
@Table(name = "shipment_status")   //this table is an enum, it will contain: REGISTERED,SENT, SHIPPED, DELIVERED
public class ShipmentStatus {
    /**
     *
     * The id of the shipment status.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * The shipment status of the shipment status.
     */
    @Column(name = "shipment_status")
    private String shipmentStatus;

    /**
     * The set of shipment status histories of the shipment status.
     */
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
