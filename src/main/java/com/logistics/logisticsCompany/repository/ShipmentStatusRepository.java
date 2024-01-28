package com.logistics.logisticsCompany.repository;

import com.logistics.logisticsCompany.entities.enums.ShipmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipmentStatusRepository extends JpaRepository<ShipmentStatus, Long> {
	
	boolean existsByShipmentStatus(String shipmentStatus);
	
	
	//not sure if this is needed
	boolean existsByShipmentStatusOrId(String shipmentStatus, Long id);}