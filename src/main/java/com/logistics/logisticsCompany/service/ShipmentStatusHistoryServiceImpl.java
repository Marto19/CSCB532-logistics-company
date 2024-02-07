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
/**
 * Service class for managing shipment status histories.
 */
@Service
public class ShipmentStatusHistoryServiceImpl implements ShipmentStatusHistoryService {
	
	private final ShipmentStatusHistoryRepository shipmentStatusHistoryRepository;
	private final ShipmentStatusRepository shipmentStatusRepository;
	private final ShipmentRepository shipmentRepository;

	/**
	 * Constructor for ShipmentStatusHistoryServiceImpl.
	 *
	 * @param shipmentStatusHistoryRepository Repository for managing shipment status histories.
	 * @param shipmentStatusRepository Repository for managing shipment statuses.
	 * @param shipmentRepository Repository for managing shipments.
	 */
	@Autowired
	public ShipmentStatusHistoryServiceImpl(ShipmentStatusHistoryRepository shipmentStatusHistoryRepository, ShipmentStatusRepository shipmentStatusRepository, ShipmentRepository shipmentRepository) {
		this.shipmentStatusHistoryRepository = shipmentStatusHistoryRepository;
		this.shipmentStatusRepository = shipmentStatusRepository;
		this.shipmentRepository = shipmentRepository;
	}

	/**
	 * Records a shipment status change without notes.
	 *
	 * @param shipmentId The ID of the shipment.
	 * @param statusName The name of the status.
	 */
	@Override
	// Overloaded method without the notes parameter
	public void recordShipmentStatusChange(Long shipmentId, String statusName) {
		recordShipmentStatusChange(shipmentId, statusName, null);
	}

	/**
	 * Records a shipment status change with optional notes.
	 *
	 * @param shipmentId The ID of the shipment.
	 * @param statusName The name of the status.
	 * @param notes Optional notes about the status change.
	 */
	@Override
	public void recordShipmentStatusChange(Long shipmentId, String statusName, String notes) {
		ShipmentStatusHistory statusHistory = new ShipmentStatusHistory();
		statusHistory.setUpdateDate(LocalDateTime.now());
		
		Shipment shipment = shipmentRepository.findById(shipmentId)
				.orElseThrow(() -> new EntityNotFoundException("Shipment not found with ID: " + shipmentId));
		statusHistory.setShipment(shipment);
		
		ShipmentStatus shipmentStatus = shipmentStatusRepository.findByShipmentStatus(statusName)
				.orElseThrow(() -> new EntityNotFoundException("ShipmentStatus '" + statusName + "' not found"));
		statusHistory.setShipmentStatus(shipmentStatus);
		
		// Set notes if provided
		if (notes != null && !notes.trim().isEmpty()) {
			statusHistory.setNotes(notes);
		}
		
		shipmentStatusHistoryRepository.save(statusHistory);
		
		// Update the status column in Shipment entity with the latest status
		shipment.setStatus(statusName);
		shipmentRepository.save(shipment);
	}

	/**
	 * Deletes a shipment status history.
	 *
	 * @param id The ID of the shipment status history to delete.
	 */
	@Override
	public void deleteShipmentStatusHistory(long id) {
		if (!shipmentStatusHistoryRepository.existsById(id)) {
			throw new EntityNotFoundException("ShipmentStatusHistory not found with ID: " + id);
		}
		shipmentStatusHistoryRepository.deleteById(id);
	}

	/**
	 * Retrieves all shipment status histories for a specific shipment.
	 *
	 * @param shipmentId The ID of the shipment.
	 * @return A list of all shipment status histories for the shipment.
	 */
	@Override
	public List<ShipmentStatusHistory> getShipmentStatusHistoriesByShipmentId(long shipmentId) {
		return shipmentStatusHistoryRepository.findByShipmentId(shipmentId);
	}
}

