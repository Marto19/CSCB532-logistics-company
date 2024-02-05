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

@RestController
@RequestMapping("/api/v1/shipment-status-history")
public class ShipmentStatusHistoryController {
	
	private final ShipmentStatusHistoryService shipmentStatusHistoryService;
	
	@Autowired
	public ShipmentStatusHistoryController(ShipmentStatusHistoryService shipmentStatusHistoryService) {
		this.shipmentStatusHistoryService = shipmentStatusHistoryService;
	}
	
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
	@GetMapping("/{shipmentId}")
	public ResponseEntity<List<ShipmentStatusHistory>> getShipmentStatusHistoriesByShipmentId(@PathVariable long shipmentId) {
		List<ShipmentStatusHistory> histories = shipmentStatusHistoryService.getShipmentStatusHistoriesByShipmentId(shipmentId);
		return ResponseEntity.ok(histories);
	}
	
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