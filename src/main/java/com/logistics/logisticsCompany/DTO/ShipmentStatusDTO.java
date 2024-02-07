package com.logistics.logisticsCompany.DTO;

import com.logistics.logisticsCompany.entities.enums.ShipmentStatus;
import com.logistics.logisticsCompany.entities.users.Customer;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The ShipmentStatusDTO class is used to represent a shipment status data transfer object.
 * It contains the id and shipment status of the shipment status.
 */
public class ShipmentStatusDTO {
	/**
	 * The id of the shipment status.
	 */
	private long id;
	/**
	 * The shipment status of the shipment status.
	 */
	private String shipmentStatus;
	
	// No-argument constructor

	/**
	 * Default constructor for the {@code ShipmentStatusDTO} class.
	 * It is used to create an empty shipment status data transfer object.
	 * This constructor is used by the Jackson library to create an empty shipment status data transfer object during deserialization.
	 * It should not be used explicitly.
	 */
	public ShipmentStatusDTO() {
	}
	
	// All-argument constructor

	/**
	 * Constructs a ShipmentStatusDTO with the specified id and shipment status.
	 * @param id
	 * @param shipmentStatus
	 *
	 */
	public ShipmentStatusDTO(long id, String shipmentStatus) {
		this.id = id;
		this.shipmentStatus = shipmentStatus;
	}

	/**
	 * Constructs a ShipmentStatusDTO with the specified shipment status.
	 * @param shipmentStatus the shipment status
	 */
	public ShipmentStatusDTO(ShipmentStatus shipmentStatus) {
		this.id = shipmentStatus.getId();
		this.shipmentStatus = shipmentStatus.getShipmentStatus();
	}
	
	// Getters and setters
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getShipmentStatus() {
		return shipmentStatus;
	}
	
	public void setShipmentStatus(String shipmentStatus) {
		this.shipmentStatus = shipmentStatus;
	}

	/**
	 * Converts a list of shipment statuses to a list of shipment status data transfer objects.
	 * @param shipmentStatuses
	 * @return a list of shipment status data transfer objects
	 *
	 */
	public static List<ShipmentStatusDTO> toDTOList(List<ShipmentStatus> shipmentStatuses) {
		return shipmentStatuses.stream().map(ShipmentStatusDTO::new).collect(Collectors.toList());
	}

}
