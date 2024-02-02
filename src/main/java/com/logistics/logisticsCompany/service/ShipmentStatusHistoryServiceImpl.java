package com.logistics.logisticsCompany.service;

import com.logistics.logisticsCompany.customExceptions.EntityNotFoundException;
import com.logistics.logisticsCompany.entities.enums.ShipmentStatus;
import com.logistics.logisticsCompany.entities.orders.Shipment;
import com.logistics.logisticsCompany.entities.orders.ShipmentStatusHistory;
import com.logistics.logisticsCompany.repository.ShipmentStatusHistoryRepository;
import com.logistics.logisticsCompany.repository.ShipmentStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import com.logistics.logisticsCompany.repository.ShipmentRepository;
@Service
public class ShipmentStatusHistoryServiceImpl implements ShipmentStatusHistoryService {
	
	private final ShipmentStatusHistoryRepository shipmentStatusHistoryRepository;
	private final ShipmentStatusRepository shipmentStatusRepository;
	private final ShipmentRepository shipmentRepository;
	
	@Autowired
	public ShipmentStatusHistoryServiceImpl(ShipmentStatusHistoryRepository shipmentStatusHistoryRepository, ShipmentStatusRepository shipmentStatusRepository, ShipmentRepository shipmentRepository) {
		this.shipmentStatusHistoryRepository = shipmentStatusHistoryRepository;
		this.shipmentStatusRepository = shipmentStatusRepository;
		this.shipmentRepository = shipmentRepository;
	}
	//TODO IMPLEMENT NOTES? (CONTROLLER NEEDS TO BE ADDED ALSO)
	@Override
	public void recordShipmentStatusChange(Long shipmentId, String statusName) {
		ShipmentStatusHistory statusHistory = new ShipmentStatusHistory();
		statusHistory.setUpdateDate(LocalDateTime.now());
		
		// Fetch the Shipment entity by ID
		Shipment shipment = shipmentRepository.findById(shipmentId)
				.orElseThrow(() -> new EntityNotFoundException("Shipment not found with ID: " + shipmentId));
		statusHistory.setShipment(shipment);
		
		ShipmentStatus shipmentStatus = shipmentStatusRepository.findByShipmentStatus(statusName)
				.orElseThrow(() -> new EntityNotFoundException("ShipmentStatus '" + statusName + "' not found"));
		statusHistory.setShipmentStatus(shipmentStatus);
		
		shipmentStatusHistoryRepository.save(statusHistory);
		
		// Update the status column in Shipment entity with the latest status
		shipment.setStatus(statusName); // Assuming there's a setStatus method in Shipment entity
		shipmentRepository.save(shipment); // Save the updated Shipment entity
	}
	
	
	//most important method
	@Override
	public void createShipmentStatusHistory(ShipmentStatusHistory shipmentStatusHistory) {
		shipmentStatusHistoryRepository.save(shipmentStatusHistory);
	}
	
	@Override
	public List<ShipmentStatusHistory> getAllShipmentStatusHistories() {
		return shipmentStatusHistoryRepository.findAll();
	}
	
	@Override
	public ShipmentStatusHistory getShipmentStatusHistoryById(long id) {
		return shipmentStatusHistoryRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("ShipmentStatusHistory not found with id: " + id));
	}
	
	@Override
	public void updateShipmentStatusHistory(long id, ShipmentStatusHistory shipmentStatusHistory) {
		ShipmentStatusHistory existingShipmentStatusHistory = shipmentStatusHistoryRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("ShipmentStatusHistory not found with id: " + id));
		
	/*	// Update the fields of the existing shipment status history
		existingShipmentStatusHistory.setStatus(shipmentStatusHistory.getStatus());
		existingShipmentStatusHistory.setUpdateDate(shipmentStatusHistory.getUpdateDate());
		existingShipmentStatusHistory.setNotes(shipmentStatusHistory.getNotes());
		existingShipmentStatusHistory.setShipment(shipmentStatusHistory.getShipment());
		existingShipmentStatusHistory.setCustomer(shipmentStatusHistory.getCustomer());
		existingShipmentStatusHistory.setShipmentStatus(shipmentStatusHistory.getShipmentStatus());
		*/
		shipmentStatusHistoryRepository.save(existingShipmentStatusHistory);
	}
	
	@Override
	public void deleteShipmentStatusHistory(long id) {
		if (!shipmentStatusHistoryRepository.existsById(id)) {
			throw new EntityNotFoundException("ShipmentStatusHistory not found with id: " + id);
		}
		shipmentStatusHistoryRepository.deleteById(id);
	}
	
	@Override
	public List<ShipmentStatusHistory> getShipmentStatusHistoriesByShipmentId(long shipmentId) {
		return shipmentStatusHistoryRepository.findByShipmentId(shipmentId);
	}
}

