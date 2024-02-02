package com.logistics.logisticsCompany.repository;

import com.logistics.logisticsCompany.entities.orders.ShipmentStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShipmentStatusHistoryRepository extends JpaRepository<ShipmentStatusHistory, Long> {
	
	
	List<ShipmentStatusHistory> findByShipmentId(long shipmentId);
}
