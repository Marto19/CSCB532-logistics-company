package com.logistics.logisticsCompany.controller;

import com.logistics.logisticsCompany.DTO.*;
import com.logistics.logisticsCompany.customExceptions.EntityNotFoundException;
import com.logistics.logisticsCompany.entities.offices.Office;
import com.logistics.logisticsCompany.entities.orders.Shipment;
import com.logistics.logisticsCompany.entities.users.Customer;
import com.logistics.logisticsCompany.entities.users.Employee;
import com.logistics.logisticsCompany.repository.ShipmentRepository;
import com.logistics.logisticsCompany.service.ShipmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


/**
 * This class is a controller for handling requests related to Shipment.
 * It uses Spring's @RestController annotation to indicate that it is a controller and the response bodies should be bound to the web response body.
 * It also uses @RequestMapping to map the web requests.
 */
@Validated
@RestController
@RequestMapping("/api/v1/shipments")
public class ShipmentController {
    /**
     * The ShipmentService instance used for shipment-related operations.
     */
    private final ShipmentService shipmentService;
    /**
     * The EntityDtoMapper instance used for converting entities to DTOs and vice versa.
     */
    private final EntityDtoMapper entityDtoMapper;
    /**
     * The ShipmentRepository instance used for shipment-related operations.
     */
    private final ShipmentRepository shipmentRepository;

    /**
     * Constructs a ShipmentController with the specified ShipmentService, ShipmentRepository, and EntityDtoMapper.
     * @param shipmentService the ShipmentService
     * @param shipmentRepository the ShipmentRepository
     * @param entityDtoMapper the EntityDtoMapper
     */
    @Autowired
    public ShipmentController(ShipmentService shipmentService, ShipmentRepository shipmentRepository, EntityDtoMapper entityDtoMapper) {
        this.shipmentService = shipmentService;
        this.shipmentRepository = shipmentRepository;
        this.entityDtoMapper = entityDtoMapper;
    }

    /**
     * This method handles the POST requests for creating a shipment.
     * @param shipmentDto the shipment to create
     * @return a ResponseEntity with the status and the created ShipmentDTO
     */
    @PostMapping("/create-shipment-beta")
    public ResponseEntity<ShipmentDTO> createShipment(@Valid @RequestBody ShipmentDTO shipmentDto) {
        Shipment createdShipment = shipmentService.createShipment(shipmentDto);
        ShipmentDTO createdShipmentDTO = entityDtoMapper.convertToShipmentDTO(createdShipment);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdShipmentDTO);
    }

    /**
     * This method handles the POST requests for marking a shipment as delivered.
     * @param shipmentId the id of the shipment to mark as delivered
     * @return a ResponseEntity with the status and a message
     */
    @PostMapping("/mark-as-delivered/{shipmentId}")//enter shipmentid and employeeid in the url
    public ResponseEntity<?> markAsDelivered(@PathVariable Long shipmentId) {
        try {
            shipmentService.markShipmentAsDelivered(shipmentId);
            return ResponseEntity.ok().body("Shipment marked as delivered successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error marking shipment as delivered: " + e.getMessage());
        }
    }
    
    
    //FIXME REMOVE--------------------------------------------------
    /**
     * This method handles the POST requests for registering a sent shipment.
     * @param shipment the sent shipment to register
     * @return a ResponseEntity with the status and a message
     */
    @PostMapping("/sent")
    public ResponseEntity<String> registerSentShipment(@RequestBody Shipment shipment) {
        try {
            shipmentService.registerSentShipment(shipment);
            return ResponseEntity.status(HttpStatus.CREATED).body("Sent shipment registered successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * This method handles the POST requests for registering a received shipment.
     * @param shipment the received shipment to register
     * @return a ResponseEntity with the status and a message
     */

    @PostMapping("/received")
    public ResponseEntity<String> registerReceivedShipment(@RequestBody Shipment shipment) {
        try {
            shipmentService.registerReceivedShipment(shipment);
            return ResponseEntity.status(HttpStatus.CREATED).body("Received shipment registered successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    //FIXME REMOVE--------------------------------------------------

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
    /**
     * This method handles the GET requests for getting all registered shipments.
     * @return a ResponseEntity with the list of ShipmentDTO if it exists, or a not found status
     */
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


    /**
     * This method handles the GET requests for getting a shipment by id.
     * @param shipmentId the id of the shipment
     * @return a ResponseEntity with the ShipmentDTO if it exists, or a not found status
     */
    @GetMapping("/{id}")
    public ResponseEntity<ShipmentDTO> getShipmentsById(@PathVariable(value = "id") long shipmentId) {
        if (!shipmentRepository.existsById(shipmentId)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Shipment shipment = shipmentService.getShipmentById(shipmentId);
        ShipmentDTO shipmentDTO = entityDtoMapper.convertToShipmentDTO(shipment);

        return new ResponseEntity<>(shipmentDTO, HttpStatus.OK);
    }
    
    /**
     * This method handles the PUT requests for updating a shipment.
     * @param shipmentId the id of the shipment to update
     * @param updatedShipment the updated shipment details
     * @return a ResponseEntity with the status and a message
     */
    @PutMapping("/{id}")    //TODO:FIX THIS "MISMATCH IN ID'S"
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

    /**
     * This method handles the DELETE requests for deleting a shipment.
     * @param shipmentId the id of the shipment to delete
     * @return a ResponseEntity with the status and a message
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteShipment(@PathVariable(value = "id") long shipmentId) {
        try {
            shipmentService.deleteShipment(shipmentId);
            return ResponseEntity.ok("Shipment deleted successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Shipment with the provided id doesn't exist");
        }
    }
    
    
    //5.d.------ Get all shipments by customer id DONE
    /**
     * This method handles the GET requests for getting all shipments by an employee.
     * @param employeeId the id of the employee
     * @return a ResponseEntity with the list of ShipmentDTO if it exists, or an ok status
     */
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<ShipmentDTO>> getShipmentsByEmployee(@PathVariable Long employeeId) {
        List<ShipmentDTO> shipmentDTOs = shipmentService.getAllSentShipmentsByEmployeeId(employeeId);
        return ResponseEntity.ok(shipmentDTOs);
    }
    
    //5.e.------ Get all shipments sent not recieved DONE
    /**
     * This method handles the GET requests for getting all shipments that have been sent but not received.
     * @return a ResponseEntity with the list of ShipmentDTO if it exists, or a not found status
     */
    @GetMapping("/sent-not-received")
    public ResponseEntity<List<ShipmentDTO>> getShipmentsSentButNotReceived() {
        List<ShipmentDTO> shipmentDTOs = shipmentService.getShipmentsSentButNotReceived();
        if (shipmentDTOs.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(shipmentDTOs);
    }
    
    //5.f.------ Get all shipments sent of customer by id
    // Using customer ID
    /**
     * This method handles the GET requests for getting all shipments sent by a customer by id.
     * @param customerId the id of the customer
     * @return a ResponseEntity with the list of ShipmentDTO if it exists, or a not found status
     */
    @GetMapping("/sent-by-client/{customerId}")
    public ResponseEntity<List<ShipmentDTO>> getShipmentsBySenderCustomerId(@PathVariable Long customerId) {
        List<ShipmentDTO> shipmentDTOs = shipmentService.getShipmentsBySenderCustomerId(customerId);
        if (shipmentDTOs.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(shipmentDTOs);
    }
    
    // Alternatively, if using phone number
    /**
     * This method handles the GET requests for getting all shipments sent by a customer by phone.
     * @param phone the phone of the customer
     * @return a ResponseEntity with the list of ShipmentDTO if it exists, or a not found status
     */
    @GetMapping("/by-sender-phone")
    public ResponseEntity<List<ShipmentDTO>> getShipmentsBySenderCustomerPhone(@RequestParam String phone) {
        List<ShipmentDTO> shipmentDTOs = shipmentService.getShipmentsBySenderCustomerPhone(phone);
        if (shipmentDTOs.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(shipmentDTOs);
    }
    
    //5.g.------ Get all shipments recieved of customer by id
    /**
     * This method handles the GET requests for getting all shipments received by a customer by id.
     * @param clientId the id of the client
     * @return a ResponseEntity with the list of ShipmentDTO if it exists, or a not found status
     */
    @GetMapping("/received-by-client/{clientId}")
    public ResponseEntity<List<ShipmentDTO>> getShipmentsByReceiverCustomerId(@PathVariable Long clientId) {
        List<ShipmentDTO> shipmentDTOs = shipmentService.getShipmentsByReceiverCustomerId(clientId);
        if (shipmentDTOs.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(shipmentDTOs);
    }
    
    
}
