package com.logistics.logisticsCompany.service;

import com.logistics.logisticsCompany.entities.orders.Shipment;
import com.logistics.logisticsCompany.repository.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ShipmentServiceImpl implements ShipmentService {
	
	private final ShipmentRepository shipmentRepository;
	
	@Autowired
	public ShipmentServiceImpl(ShipmentRepository shipmentRepository) {
		this.shipmentRepository = shipmentRepository;
	}
	
	@Override
	@Transactional
	public List<Shipment> getAllShipments() {
		return shipmentRepository.findAll();
	}
	
	@Override
	@Transactional
	public Shipment getShipmentById(long shipmentId) {
		return shipmentRepository.findById(shipmentId)
				.orElseThrow(() -> new RuntimeException("Shipment not found"));
	}
	
	@Override
	@Transactional
	public Shipment addShipment(Shipment shipment) {
		return shipmentRepository.save(shipment);
	}
	
	@Override
	@Transactional
	public void updateShipment(long shipmentId, Shipment updatedShipment) {
		Shipment existingShipment = getShipmentById(shipmentId);
		
		// Update basic properties
		existingShipment.setShipmentDate(updatedShipment.getShipmentDate());
		existingShipment.setWeight(updatedShipment.getWeight());
		existingShipment.setPrice(updatedShipment.getPrice());
		existingShipment.setIsPaid(updatedShipment.isPaid());
		existingShipment.setReceivedDate(updatedShipment.getReceivedDate());
		
		// Update sender relationships
		existingShipment.setSenderOffice(updatedShipment.getSenderOffice());
		existingShipment.setSenderCustomer(updatedShipment.getSenderCustomer());
		existingShipment.setSenderEmployee(updatedShipment.getSenderEmployee());
		
		// Update receiver relationships
		existingShipment.setReceiverOffice(updatedShipment.getReceiverOffice());
		existingShipment.setReceiverCustomer(updatedShipment.getReceiverCustomer());
		existingShipment.setReceiverEmployee(updatedShipment.getReceiverEmployee());
		
		// Update status histories if necessary
		// Note: Be careful with updating collections like this. It might be better to handle this in a separate method
		existingShipment.setStatusHistories(updatedShipment.getStatusHistories());
		
		shipmentRepository.save(existingShipment);
	}
	
	@Override
	@Transactional
	public void deleteShipment(long shipmentId) {
		shipmentRepository.deleteById(shipmentId);
	}
}
