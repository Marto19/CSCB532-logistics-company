package com.logistics.logisticsCompany.service;

import com.logistics.logisticsCompany.customExceptions.EntityNotFoundException;
import com.logistics.logisticsCompany.entities.orders.ShipmentStatusHistory;
import com.logistics.logisticsCompany.repository.ShipmentStatusHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShipmentStatusHistoryServiceImpl implements ShipmentStatusHistoryService {
	
	private final ShipmentStatusHistoryRepository shipmentStatusHistoryRepository;
	
	@Autowired
	public ShipmentStatusHistoryServiceImpl(ShipmentStatusHistoryRepository shipmentStatusHistoryRepository) {
		this.shipmentStatusHistoryRepository = shipmentStatusHistoryRepository;
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

