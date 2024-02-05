package com.logistics.logisticsCompany.service.enums;

import com.logistics.logisticsCompany.entities.enums.ShipmentStatus;

import java.util.List;

public interface ShipmentStatusService {
	void createShipmentStatus(ShipmentStatus shipmentStatus);
	
	void deleteShipmentStatus(long shipmentStatusId);
	
	List<ShipmentStatus> getAllShipmentStatuses();
	
	boolean existsByShipmentStatus(String shipmentStatus);
	
	boolean existsByShipmentStatusOrId(String shipmentStatus, long shipmentStatusId);
}
