package com.logistics.logisticsCompany.controller;

import com.logistics.logisticsCompany.DTO.*;
import com.logistics.logisticsCompany.customExceptions.EntityNotFoundException;
import com.logistics.logisticsCompany.entities.offices.Office;
import com.logistics.logisticsCompany.entities.orders.Shipment;
import com.logistics.logisticsCompany.entities.users.Customer;
import com.logistics.logisticsCompany.entities.users.Employee;
import com.logistics.logisticsCompany.repository.ShipmentRepository;
import com.logistics.logisticsCompany.service.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/shipments")
public class ShipmentController {

    private final ShipmentService shipmentService;

    private final EntityDtoMapper entityDtoMapper;
    
    private final ShipmentRepository shipmentRepository;
    @Autowired
    public ShipmentController(ShipmentService shipmentService, EntityDtoMapper entityDtoMapper, ShipmentRepository shipmentRepository) {
        this.shipmentService = shipmentService;
        this.entityDtoMapper = entityDtoMapper;
        this.shipmentRepository = shipmentRepository;
    }
    
    
    @PostMapping("/create-shipment-beta")//it needs logged employee id
    public ResponseEntity<Shipment> createShipment(@RequestBody ShipmentDTO shipmentDto) {
        Shipment shipment = shipmentService.createShipment(shipmentDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(shipment);
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
    
    //todo marto review this and delete the commented code below it if u accept it.
    @GetMapping
    public ResponseEntity<List<ShipmentDTO>> getAllRegisteredShipments() {
        List<Shipment> registeredShipments = shipmentService.getAllShipments();
        if (registeredShipments.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<ShipmentDTO> shipmentDTOs = registeredShipments.stream()
                .map(entityDtoMapper::convertToShipmentDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(shipmentDTOs, HttpStatus.OK);
    }
    /*@GetMapping---------------------------------------------------
    public List<ShipmentDTO> getAllShipments() {
        List<Shipment> shipments = shipmentService.getAllShipments();
        return shipments.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }*///*COMMENTED CODE----------------------------------------------
    


    @GetMapping("/{id}")
    public ResponseEntity<ShipmentDTO> getShipmentsById(@PathVariable(value = "id") long shipmentId) {
        if (!shipmentRepository.existsById(shipmentId)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Shipment shipment = shipmentService.getShipmentById(shipmentId);
        ShipmentDTO shipmentDTO = entityDtoMapper.convertToShipmentDTO(shipment);

        return new ResponseEntity<>(shipmentDTO, HttpStatus.OK);
    }
    
    
    
    @PutMapping("/{id}")    //TODO:FIX THIS "MISMATCH IN ID'S"
    public ResponseEntity<String> updateShipment(@PathVariable(value = "id") long shipmentId,
                                                 @RequestBody Shipment updatedShipment) {
        if(!shipmentRepository.existsById(shipmentId)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Company with the provided id doesn't exist");
        }
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
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Shipment with the provided id doesn't exist");
        }
    }
    
    
    //5.d.------ Get all shipments by customer id
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<ShipmentDTO>> getShipmentsByEmployee(@PathVariable Long employeeId) {
        List<ShipmentDTO> shipmentDTOs = shipmentService.getAllSentShipmentsByEmployeeId(employeeId)
                .stream()
                .map(entityDtoMapper::convertToShipmentDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(shipmentDTOs);
    }
    
    //5.e.------ Get all shipments sent not recieved
    @GetMapping("/sent-not-received")
    public ResponseEntity<List<ShipmentDTO>> getShipmentsSentButNotReceived() {
        List<ShipmentDTO> shipmentDTOs = shipmentService.getShipmentsSentButNotReceived();
        if (shipmentDTOs.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(shipmentDTOs);
    }
    
    //5.f.------ Get all shipments sent of customer by id
    @GetMapping("/sent-by-client/{clientId}")
    public ResponseEntity<List<ShipmentDTO>> getShipmentsBySenderCustomerId(@PathVariable Long clientId) {
        List<ShipmentDTO> shipmentDTOs = shipmentService.getShipmentsBySenderCustomerId(clientId);
        if (shipmentDTOs.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(shipmentDTOs);
    }
    
    //5.g.------ Get all shipments recieved of customer by id
    @GetMapping("/received-by-client/{clientId}")
    public ResponseEntity<List<ShipmentDTO>> getShipmentsByReceiverCustomerId(@PathVariable Long clientId) {
        List<ShipmentDTO> shipmentDTOs = shipmentService.getShipmentsByReceiverCustomerId(clientId);
        if (shipmentDTOs.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(shipmentDTOs);
    }
    
    
}
