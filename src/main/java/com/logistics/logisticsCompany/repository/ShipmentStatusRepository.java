package com.logistics.logisticsCompany.repository;

import com.logistics.logisticsCompany.entities.enums.ShipmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for ShipmentStatus entities.
 */
@Repository
public interface ShipmentStatusRepository extends JpaRepository<ShipmentStatus, Long> {


	/**
	 * Check if a shipment status exists with the given status name.
	 *
	 * @param shipmentStatus The status name of the shipment to check for existence.
	 * @return True if a shipment status with the given status name exists, false otherwise.
	 */
	boolean existsByShipmentStatus(String shipmentStatus);
	
	
	//not sure if this is needed
	/**
	 * Check if a shipment status exists with the given status name or ID.
	 *
	 * @param shipmentStatus The status name of the shipment to check for existence.
	 * @param id The ID of the shipment to check for existence.
	 * @return True if a shipment status with the given status name or ID exists, false otherwise.
	 */
	boolean existsByShipmentStatusOrId(String shipmentStatus, Long id);

	/**
	 * Retrieve a shipment status by its status name.
	 *
	 * @param statusName The status name of the shipment to retrieve.
	 * @return An Optional containing the shipment status if found, otherwise empty.
	 */
	Optional<ShipmentStatus> findByShipmentStatus(String statusName);

}