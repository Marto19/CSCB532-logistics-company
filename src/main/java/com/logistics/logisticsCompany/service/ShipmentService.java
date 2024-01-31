package com.logistics.logisticsCompany.service;

import com.logistics.logisticsCompany.DTO.ShipmentDTO;
import com.logistics.logisticsCompany.entities.orders.Shipment;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ShipmentService {
    @Transactional
    Shipment createShipment(ShipmentDTO shipmentDto);
	
	@Transactional
	void markShipmentAsDelivered(Long shipmentId, Long employeeId);
	
	void registerSentShipment(Shipment shipment);
    void registerReceivedShipment(Shipment shipment);
//    void createShipment(Shipment shipment);
    List<Shipment> getAllShipments();
    Shipment getShipmentById(long shipmentId);
    void updateShipment(long shipmentId, Shipment updatedShipment);
    void deleteShipment(long shipmentId);
    
    //5.d
    List<Shipment> getAllSentShipmentsByEmployeeId(Long employeeId);
    
    //not being used yet
    List<Shipment> getAllReceivedShipmentsByEmployeeId(Long employeeId);
    
    //5.e.------------------------
    List<ShipmentDTO> getShipmentsSentButNotReceived();
    
    //5.f.------------------------
    List<ShipmentDTO> getShipmentsBySenderCustomerId(Long customerId);
    
    //5.g.------------------------
    List<ShipmentDTO> getShipmentsByReceiverCustomerId(Long customerId);
}