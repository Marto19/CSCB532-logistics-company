package com.logistics.logisticsCompany.service;

import com.logistics.logisticsCompany.entities.orders.Shipment;

import java.util.List;

public interface ShipmentService {
    void registerSentShipment(Shipment shipment);
    void registerReceivedShipment(Shipment shipment);
//    void createShipment(Shipment shipment);
    List<Shipment> getAllShipments();
    Shipment getShipmentById(long shipmentId);
    void updateShipment(long shipmentId, Shipment updatedShipment);
    void deleteShipment(long shipmentId);



    }
