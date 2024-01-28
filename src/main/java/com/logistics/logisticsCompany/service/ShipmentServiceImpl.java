
package com.logistics.logisticsCompany.service;

import com.logistics.logisticsCompany.DTO.*;
import com.logistics.logisticsCompany.customExceptions.EntityNotFoundException;
import com.logistics.logisticsCompany.entities.orders.Shipment;
import com.logistics.logisticsCompany.repository.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShipmentServiceImpl implements ShipmentService {

    private final ShipmentRepository shipmentRepository;
    private final EntityDtoMapper entityDtoMapper;
    
    @Autowired
    public ShipmentServiceImpl(ShipmentRepository shipmentRepository, EntityDtoMapper entityDtoMapper) {
        this.shipmentRepository = shipmentRepository;
        this.entityDtoMapper = entityDtoMapper;
    }

    @Override
    public void registerSentShipment(Shipment shipment) {
        // Check if the shipment has a predefined ID
        if (shipment.getId() != 0) {
            throw new RuntimeException("Cannot register sent shipment with predefined ID");
        }

        // Set additional details for a sent shipment
        shipment.setShipmentDate(LocalDate.now()); // Set the shipment date to the current date
        shipment.setReceivedDate(null); // Reset received date for sent shipments

        // Implement other logic as needed

        // Save the shipment
        shipmentRepository.save(shipment);
    }

    @Override
    public void registerReceivedShipment(Shipment shipment) {
        // Check if the shipment has a predefined ID
        if (shipment.getId() != 0) {
            throw new RuntimeException("Cannot register received shipment with predefined ID");
        }

        // Set additional details for a received shipment
        shipment.setReceivedDate(LocalDate.now()); // Set the received date to the current date
        // Implement other logic as needed
        // Save the shipment
        shipmentRepository.save(shipment);
    }

//
//    @Override
//    public void createShipment(Shipment shipment) {
//        if (shipment.getId() != 0) {
//            throw new RuntimeException("Cannot create shipment with predefined ID");
//        }
//        shipmentRepository.save(shipment);
//    }

    @Override
    public List<Shipment> getAllShipments() {
        return shipmentRepository.findAll();
    }

    @Override
    public Shipment getShipmentById(long shipmentId) {
        return shipmentRepository.findById(shipmentId)
                .orElseThrow(() -> new EntityNotFoundException("Shipment not found with id: " + shipmentId));
    }

    @Override
    public void updateShipment(long shipmentId, Shipment updatedShipment) {
        Shipment existingShipment = shipmentRepository.findById(shipmentId)
                .orElseThrow(() -> new EntityNotFoundException("Shipment not found with id: " + shipmentId));

        // Add protection to ensure IDs match
        if (existingShipment.getId() != updatedShipment.getId()) {
            throw new RuntimeException("Mismatched IDs in the request");
        }

        // Update logic if needed
        existingShipment.setShipmentDate(updatedShipment.getShipmentDate());
        existingShipment.setWeight(updatedShipment.getWeight());
        existingShipment.setPrice(updatedShipment.getPrice());
        existingShipment.setIsPaid(updatedShipment.isPaid());
        existingShipment.setReceivedDate(updatedShipment.getReceivedDate());

        shipmentRepository.save(existingShipment);
    }

    @Override
    public void deleteShipment(long shipmentId) {
        if (shipmentRepository.existsById(shipmentId)) {
            shipmentRepository.deleteById(shipmentId);
        } else {
            throw new EntityNotFoundException("Shipment not found with id: " + shipmentId);
        }
    }
    
    
    //5.d.------------------------
    @Override
    public List<Shipment> getAllSentShipmentsByEmployeeId(Long employeeId) {
        return shipmentRepository.findBySenderEmployeeId(employeeId);
    }
    @Override
    public List<Shipment> getAllReceivedShipmentsByEmployeeId(Long employeeId) {
        return shipmentRepository.findByReceiverEmployeeId(employeeId);
    }
    
    //5.e.------------------------
    public List<ShipmentDTO> getShipmentsSentButNotReceived() {
        String sentStatus = "SENT";
        List<String> notReceivedStatuses = Arrays.asList("RECEIVED", "DELIVERED");
        List<Shipment> shipments = shipmentRepository.findShipmentsSentButNotReceived(sentStatus, notReceivedStatuses);
        return shipments.stream()
                .map(entityDtoMapper::convertToShipmentDTO)
                .collect(Collectors.toList());
    }
    
    //5.f.------------------------
    @Override
    public List<ShipmentDTO> getShipmentsBySenderCustomerId(Long customerId) {
        List<Shipment> shipments = shipmentRepository.findBySenderCustomerId(customerId);
        return shipments.stream()
                .map(entityDtoMapper::convertToShipmentDTO)
                .collect(Collectors.toList());
    }
    
    //5.g.------------------------
    @Override
    public List<ShipmentDTO> getShipmentsByReceiverCustomerId(Long customerId) {
        List<Shipment> shipments = shipmentRepository.findByReceiverCustomerId(customerId);
        return shipments.stream()
                .map(entityDtoMapper::convertToShipmentDTO)
                .collect(Collectors.toList());
    }
    
}
