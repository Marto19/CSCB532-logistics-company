package com.logistics.logisticsCompany.repository;

import com.logistics.logisticsCompany.entities.orders.ShipmentStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for ShipmentStatusHistory entities
 */
public interface ShipmentStatusHistoryRepository extends JpaRepository<ShipmentStatusHistory, Long> {
	

	/**
	 * Retrieve a list of shipment status history records by shipment ID.
	 *
	 * @param shipmentId The ID of the shipment to retrieve status history for.
	 * @return A list of shipment status history records associated with the specified shipment.
	 */
	List<ShipmentStatusHistory> findByShipmentId(long shipmentId);
}
