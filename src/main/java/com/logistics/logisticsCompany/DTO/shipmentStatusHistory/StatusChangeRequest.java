package com.logistics.logisticsCompany.DTO.shipmentStatusHistory;


/**
 * The StatusChangeRequest class is used to represent a request for changing the status of a shipment.
 * It contains the status name and optional notes.
 */
public class StatusChangeRequest {
	private String statusName;
	private String notes; // Optional notes field
	
	// Getters and Setters
	public String getStatusName() {
		return statusName;
	}
	
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	
	public String getNotes() {
		return notes;
	}
	
	public void setNotes(String notes) {
		this.notes = notes;
	}
}
