package com.logistics.logisticsCompany.controller;

import com.logistics.logisticsCompany.entities.orders.ShipmentStatusHistory;
import com.logistics.logisticsCompany.service.ShipmentStatusHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/shipment-status-histories")
public class ShipmentStatusHistoryController {
	
	@Autowired
	private ShipmentStatusHistoryService shipmentStatusHistoryService;
	
	// Other endpoints ...
	
	@GetMapping("/by-shipment/{shipmentId}")
	public ResponseEntity<List<ShipmentStatusHistory>> getShipmentStatusHistoriesByShipmentId(@PathVariable long shipmentId) {
		List<ShipmentStatusHistory> histories = shipmentStatusHistoryService.getShipmentStatusHistoriesByShipmentId(shipmentId);
		return ResponseEntity.ok(histories);
	}
}