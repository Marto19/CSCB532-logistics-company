package com.logistics.logisticsCompany.service;

import com.logistics.logisticsCompany.entities.orders.Shipment;

import java.util.List;

public interface ShipmentService {
	List<Shipment> getAllShipments();
	Shipment getShipmentById(long shipmentId);
	Shipment addShipment(Shipment shipment);
	void updateShipment(long shipmentId, Shipment shipment);
	void deleteShipment(long shipmentId);
}