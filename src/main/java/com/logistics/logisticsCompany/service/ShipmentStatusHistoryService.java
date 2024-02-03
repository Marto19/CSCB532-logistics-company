package com.logistics.logisticsCompany.service;

import com.logistics.logisticsCompany.entities.orders.Shipment;
import com.logistics.logisticsCompany.entities.orders.ShipmentStatusHistory;

import java.util.List;

public interface ShipmentStatusHistoryService {
	
	
	// Overloaded method without the notes parameter
	void recordShipmentStatusChange(Long shipmentId, String statusName);
	
	void recordShipmentStatusChange(Long shipmentId, String statusName, String notes);
	
	void deleteShipmentStatusHistory(long id);
	
	List<ShipmentStatusHistory> getShipmentStatusHistoriesByShipmentId(long shipmentId);
}
