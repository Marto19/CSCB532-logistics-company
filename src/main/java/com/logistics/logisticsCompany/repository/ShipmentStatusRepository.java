package com.logistics.logisticsCompany.repository;

import com.logistics.logisticsCompany.entities.enums.ShipmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShipmentStatusRepository extends JpaRepository<ShipmentStatus, Long> {
	
	boolean existsByShipmentStatus(String shipmentStatus);
	
	
	//not sure if this is needed
	boolean existsByShipmentStatusOrId(String shipmentStatus, Long id);

	Optional<ShipmentStatus> findByShipmentStatus(String statusName);

}