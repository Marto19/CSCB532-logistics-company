package com.logistics.logisticsCompany.repository;

import com.logistics.logisticsCompany.entities.orders.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
    boolean existsById(long id);
	
	//find all sent shipments registered by employee with the given ID (jpa query)
	List<Shipment> findBySenderEmployeeId(Long employeeId);
	
	//find all received shipments registered by employee with the given ID (jpa query) idk if needed...
	List<Shipment> findByReceiverEmployeeId(Long employeeId);
	
	// This method assumes you want to find shipments that have been sent but not received.
	// It uses a subquery to find all shipments that have a status history of "SENT" but do not have a status of "RECEIVED" or "DELIVERED"
	@Query("SELECT s FROM Shipment s WHERE EXISTS (" +
			"SELECT sh FROM ShipmentStatusHistory sh WHERE sh.shipment = s AND sh.shipmentStatus.shipmentStatus = :sentStatus) " +
			"AND NOT EXISTS (" +
			"SELECT sh FROM ShipmentStatusHistory sh WHERE sh.shipment = s AND sh.shipmentStatus.shipmentStatus IN (:notReceivedStatuses))")
	List<Shipment> findShipmentsSentButNotReceived(@Param("sentStatus") String sentStatus, @Param("notReceivedStatuses") List<String> notReceivedStatuses);
	

	List<Shipment> findBySenderCustomerId(Long customerId);
	
	List<Shipment> findByReceiverCustomerId(Long customerId);
}