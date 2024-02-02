package com.logistics.logisticsCompany.DTO;

import com.logistics.logisticsCompany.entities.enums.ShipmentStatus;
import com.logistics.logisticsCompany.entities.users.Customer;

import java.util.List;
import java.util.stream.Collectors;

public class ShipmentStatusDTO {
	private long id;
	private String shipmentStatus;
	
	// No-argument constructor
	public ShipmentStatusDTO() {
	}
	
	// All-argument constructor
	public ShipmentStatusDTO(long id, String shipmentStatus) {
		this.id = id;
		this.shipmentStatus = shipmentStatus;
	}
	
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
	
	public static List<ShipmentStatusDTO> toDTOList(List<ShipmentStatus> shipmentStatuses) {
		return shipmentStatuses.stream().map(ShipmentStatusDTO::new).collect(Collectors.toList());
	}

}
