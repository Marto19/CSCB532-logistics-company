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
@RequestMapping("/api/v1/shipments")
public class ShipmentController {

    private final ShipmentService shipmentService;

    @Autowired
    public ShipmentController(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    @PostMapping("/sent")
    public ResponseEntity<String> registerSentShipment(@RequestBody Shipment shipment) {
        try {
            shipmentService.registerSentShipment(shipment);
            return ResponseEntity.status(HttpStatus.CREATED).body("Sent shipment registered successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/received")
    public ResponseEntity<String> registerReceivedShipment(@RequestBody Shipment shipment) {
        try {
            shipmentService.registerReceivedShipment(shipment);
            return ResponseEntity.status(HttpStatus.CREATED).body("Received shipment registered successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

//    @PostMapping
//    public ResponseEntity<String> createShipment(@RequestBody Shipment shipment) {
//        try {
//            shipmentService.createShipment(shipment);
//            return ResponseEntity.status(HttpStatus.CREATED).body("Shipment created successfully");
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//        }
//    }

    @GetMapping
    public List<Shipment> getAllShipments() {
        return shipmentService.getAllShipments();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateShipment(@PathVariable(value = "id") long shipmentId,
                                                 @RequestBody Shipment updatedShipment) {
        try {
            shipmentService.updateShipment(shipmentId, updatedShipment);
            return ResponseEntity.ok("Shipment updated successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteShipment(@PathVariable(value = "id") long shipmentId) {
        try {
            shipmentService.deleteShipment(shipmentId);
            return ResponseEntity.ok("Shipment deleted successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
