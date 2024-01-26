package com.logistics.logisticsCompany.controller;

import com.logistics.logisticsCompany.customExceptions.EntityNotFoundException;
import com.logistics.logisticsCompany.entities.orders.Shipment;
import com.logistics.logisticsCompany.service.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shipments")
public class ShipmentController {
	
	private final ShipmentService shipmentService;
	
	@Autowired
	public ShipmentController(ShipmentService shipmentService) {
		this.shipmentService = shipmentService;
	}
	
	@GetMapping
	public ResponseEntity<List<Shipment>> getAllShipments() {
		List<Shipment> shipments = shipmentService.getAllShipments();
		return ResponseEntity.ok(shipments);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Shipment> getShipmentById(@PathVariable long id) {
		Shipment shipment = shipmentService.getShipmentById(id);
		return ResponseEntity.ok(shipment);
	}
	
	@PostMapping
	public ResponseEntity<Shipment> addShipment(@RequestBody Shipment shipment) {
		Shipment newShipment = shipmentService.addShipment(shipment);
		return ResponseEntity.ok(newShipment);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Void> updateShipment(@PathVariable long id, @RequestBody Shipment shipment) {
		shipmentService.updateShipment(id, shipment);
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteShipment(@PathVariable long id) {
		shipmentService.deleteShipment(id);
		return ResponseEntity.ok().build();
	}
}