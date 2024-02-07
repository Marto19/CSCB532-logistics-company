package com.logistics.logisticsCompany.controller.enums;

import com.logistics.logisticsCompany.DTO.CustomerDTO;
import com.logistics.logisticsCompany.DTO.ShipmentStatusDTO;
import com.logistics.logisticsCompany.entities.enums.ShipmentStatus;
import com.logistics.logisticsCompany.entities.orders.Shipment;
import com.logistics.logisticsCompany.entities.users.Customer;
import com.logistics.logisticsCompany.repository.ShipmentStatusRepository;
import com.logistics.logisticsCompany.service.enums.ShipmentStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



import java.util.List;
/**
 * This class is a controller for handling requests related to ShipmentStatus.
 * It uses Spring's @RestController annotation to indicate that it is a controller and the response bodies should be bound to the web response body.
 * It also uses @RequestMapping to map the web requests.
 */
@RestController
@RequestMapping("/api/v1/shipment-statuses")
public class ShipmentStatusController {
	/**
	 * The ShipmentStatusService instance used for shipment status-related operations.
	 */
	@Autowired
	private ShipmentStatusService shipmentStatusService;
	/**
	 * The ShipmentStatusRepository instance used for shipment status-related operations.
	 */
	@Autowired
	private ShipmentStatusRepository shipmentStatusRepository;
	/**
	 * This method handles the POST requests for creating a shipment status.
	 * @param shipmentStatus the shipment status to create
	 * @return a ResponseEntity with the status and a message
	 */
	@PostMapping
	public ResponseEntity<String> createShipmentStatus(@RequestBody ShipmentStatus shipmentStatus) {
		// Check if a customer with the given phone already exists
		if (shipmentStatusService.existsByShipmentStatusOrId(shipmentStatus.getShipmentStatus(), shipmentStatus.getId())) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Shipment status with the provided name already exists");
		}
		// If the customer with the phone number doesn't exist, proceed with saving the customer
		shipmentStatusService.createShipmentStatus(shipmentStatus);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body("Shipment status created successfully");
	}

	/**
	 * This method handles the DELETE requests for deleting a shipment status.
	 * @param shipmentStatusId the id of the shipment status to delete
	 * @return a ResponseEntity with the status and a message
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteShipmentStatus(@PathVariable(value = "id") long shipmentStatusId) {
		if (!shipmentStatusRepository.existsById(shipmentStatusId)){
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Shipment status with the provided id doesn't exist");
		}
		shipmentStatusService.deleteShipmentStatus(shipmentStatusId);
		return ResponseEntity.status(HttpStatus.OK)
				.body("Shipment status deleted successfully");
	}

	/**
	 * This method handles the GET requests for getting all shipment statuses.
	 * @return a list of ShipmentStatusDTO
	 */
	@GetMapping
	public List<ShipmentStatusDTO> getAllCustomers() {
		List<ShipmentStatus> shipmentStatuses = shipmentStatusService.getAllShipmentStatuses();
		return ShipmentStatusDTO.toDTOList(shipmentStatuses);
	}
}