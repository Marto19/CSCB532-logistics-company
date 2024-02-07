package com.logistics.logisticsCompany.service;

import com.logistics.logisticsCompany.DTO.ShipmentDTO;
import com.logistics.logisticsCompany.entities.orders.Shipment;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ShipmentService {
    
    //creation of shipment
    @Transactional
    Shipment createShipment(ShipmentDTO shipmentDto);
	
    //marking shipment as delivered
	@Transactional
	void markShipmentAsDelivered(Long shipmentId);
	
    
    //todo: remove this method
    //marto implementation that maybe has to be deleted
	void registerSentShipment(Shipment shipment);
    void registerReceivedShipment(Shipment shipment);
//    void createShipment(Shipment shipment);
    List<Shipment> getAllShipments();
    Shipment getShipmentById(long shipmentId);
    void updateShipment(long shipmentId, Shipment updatedShipment);
    void deleteShipment(long shipmentId);
    
    //5.d
    List<ShipmentDTO> getAllSentShipmentsByEmployeeId(Long employeeId);
    
    //5.e.------------------------
    List<ShipmentDTO> getShipmentsSentButNotReceived();
    
    //5.f.------------------------
    List<ShipmentDTO> getShipmentsBySenderCustomerId(Long customerId);
    
    // Alternatively, if using phone number
    List<ShipmentDTO> getShipmentsBySenderCustomerPhone(String phone);
    
    //5.g.------------------------
    List<ShipmentDTO> getShipmentsByReceiverCustomerId(Long customerId);
}