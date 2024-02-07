package com.logistics.logisticsCompany.controller;

import com.logistics.logisticsCompany.DTO.shipmentStatusHistory.StatusChangeRequest;
import com.logistics.logisticsCompany.customExceptions.EntityNotFoundException;
import com.logistics.logisticsCompany.entities.orders.ShipmentStatusHistory;
import com.logistics.logisticsCompany.service.ShipmentStatusHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The ShipmentStatusHistoryController class handles HTTP requests related to shipment status history operations.
 * It provides endpoints for recording shipment status changes, retrieving shipment status histories, and deleting shipment status histories.
 */
@RestController
@RequestMapping("/api/v1/shipment-status-history")
public class ShipmentStatusHistoryController {


	/**
	 * The ShipmentStatusHistoryService instance used for shipment status history-related operations.
	 */
	private final ShipmentStatusHistoryService shipmentStatusHistoryService;


	/**
	 * This constructor is used to inject the ShipmentStatusHistoryService instance.
	 * @param shipmentStatusHistoryService the ShipmentStatusHistoryService instance
	 */
	@Autowired
	public ShipmentStatusHistoryController(ShipmentStatusHistoryService shipmentStatusHistoryService) {
		this.shipmentStatusHistoryService = shipmentStatusHistoryService;
	}

	/**
	 * This method handles the POST requests for recording a shipment status change.
	 * @param shipmentId the ID of the shipment
	 * @param statusChangeRequest the status change request
	 * @return a ResponseEntity indicating the result of the operation
	 */
	@PostMapping("/{shipmentId}")
	public ResponseEntity<?> recordShipmentStatusChange(@PathVariable Long shipmentId, @RequestBody StatusChangeRequest statusChangeRequest) {
		try {
			shipmentStatusHistoryService.recordShipmentStatusChange(shipmentId, statusChangeRequest.getStatusName(), statusChangeRequest.getNotes());
			return ResponseEntity.ok().body("Shipment status updated successfully");
		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating shipment status: " + e.getMessage());
		}
	}

	/**
	 * This method handles the GET requests for retrieving shipment status histories by shipment ID.
	 * @param shipmentId the ID of the shipment
	 * @return a ResponseEntity containing the shipment status histories
	 */
	@GetMapping("/{shipmentId}")
	public ResponseEntity<List<ShipmentStatusHistory>> getShipmentStatusHistoriesByShipmentId(@PathVariable long shipmentId) {
		List<ShipmentStatusHistory> histories = shipmentStatusHistoryService.getShipmentStatusHistoriesByShipmentId(shipmentId);
		return ResponseEntity.ok(histories);
	}


	/**
	 * This method handles the DELETE requests for deleting a shipment status history.
	 * @param id the ID of the shipment status history
	 * @return a ResponseEntity indicating the result of the operation
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteShipmentStatusHistory(@PathVariable Long id) {
		try {
			shipmentStatusHistoryService.deleteShipmentStatusHistory(id);
			return ResponseEntity.ok().body("Shipment status history deleted successfully");
		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting shipment status history: " + e.getMessage());
		}
	}
}