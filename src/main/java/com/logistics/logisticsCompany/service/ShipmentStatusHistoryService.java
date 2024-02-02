package com.logistics.logisticsCompany.service;

import com.logistics.logisticsCompany.entities.orders.ShipmentStatusHistory;

import java.util.List;

public interface ShipmentStatusHistoryService {
	void createShipmentStatusHistory(ShipmentStatusHistory shipmentStatusHistory);
	
	List<ShipmentStatusHistory> getAllShipmentStatusHistories();
	
	ShipmentStatusHistory getShipmentStatusHistoryById(long id);
	
	void updateShipmentStatusHistory(long id, ShipmentStatusHistory shipmentStatusHistory);
	
	void deleteShipmentStatusHistory(long id);
	
	List<ShipmentStatusHistory> getShipmentStatusHistoriesByShipmentId(long shipmentId);
}
