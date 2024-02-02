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

@RestController
@RequestMapping("/api/v1/shipment-statuses")
public class ShipmentStatusController {
	
	@Autowired
	private ShipmentStatusService shipmentStatusService;
	
	@Autowired
	private ShipmentStatusRepository shipmentStatusRepository;
	
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
	
	@GetMapping
	public List<ShipmentStatusDTO> getAllCustomers() {
		List<ShipmentStatus> shipmentStatuses = shipmentStatusService.getAllShipmentStatuses();
		return ShipmentStatusDTO.toDTOList(shipmentStatuses);
	}
}