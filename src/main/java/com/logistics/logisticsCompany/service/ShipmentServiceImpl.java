
package com.logistics.logisticsCompany.service;

import com.logistics.logisticsCompany.customExceptions.EntityNotFoundException;
import com.logistics.logisticsCompany.entities.orders.Shipment;
import com.logistics.logisticsCompany.repository.ShipmentRepository;
import com.logistics.logisticsCompany.service.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ShipmentServiceImpl implements ShipmentService {

    private final ShipmentRepository shipmentRepository;

    @Autowired
    public ShipmentServiceImpl(ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
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


    @Override
    public void createShipment(Shipment shipment) {
        if (shipment.getId() != 0) {
            throw new RuntimeException("Cannot create shipment with predefined ID");
        }
        shipmentRepository.save(shipment);
    }

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
        existingShipment.setPricePaid(updatedShipment.isPricePaid());
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


}
