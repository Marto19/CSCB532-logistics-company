package com.logistics.logisticsCompany.entities.orders;


import com.logistics.logisticsCompany.entities.enums.ShipmentStatus;
import com.logistics.logisticsCompany.entities.users.Customer;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "shipment_status_history")
public class ShipmentStatusHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "update_date", nullable = false)
	private LocalDateTime updateDate;

	@Column(name = "notes", nullable = true)
	private String notes;

	////////////////////////////////CREATING THE RELATIONSHIPS/////////////////////////
	//relationship order_history/shipment - n:1
	@ManyToOne
	@JoinColumn(name = "shipment_id")
	private Shipment shipment;
	
	@ManyToOne
	@JoinColumn(name = "shipment_status_id")
	private ShipmentStatus shipmentStatus;

	//Constructors
	public ShipmentStatusHistory(){
	}

	public ShipmentStatusHistory(LocalDateTime updateDate, String notes){

		this.updateDate = updateDate;
		this.notes = notes;
	}

	//Getters and setters

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

//	public int getStatus() {
//		return status;
//	}
//
//	public void setStatus(int status) {
//		this.status = status;
//	}

	public LocalDateTime getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(LocalDateTime updateDate) {
		this.updateDate = updateDate;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Shipment getShipment() {
		return shipment;
	}

	public void setShipment(Shipment shipment) {
		this.shipment = shipment;
	}
	
	
	
	public void setShipmentStatus(ShipmentStatus shipmentStatus) {
		this.shipmentStatus = shipmentStatus;
	}
	
	
	
	public ShipmentStatus getShipmentStatus() {
		return shipmentStatus;
	}
	
	@Override
	public String toString() {
		return "ShipmentStatusHistory{" +
				"id=" + id +
				", updateDate=" + updateDate +
				", notes='" + notes + '\'' +
				'}';
	}
}
