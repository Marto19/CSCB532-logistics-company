package com.logistics.logisticsCompany.entities.orders;


import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "orderhistory")
public class OrderHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int orderHistoryID;

	@Column(name = "shipmentid", nullable = false)
	private int shipmentID;

	@Column(name = "status", nullable = false)
	private int status;

	@Column(name = "updatedate", nullable = false)
	private LocalDateTime updateDate;

	@Column(name = "notes", nullable = true)
	private String notes;

	////////////////////////////////CREATING THE RELATIONSHIPS/////////////////////////
	//relationship order_history/shipment - n:1
//	@ManyToOne
//	@JoinColumn(name = "shipmentid")
//	private Shipment shipment;

	//Constructors
	public OrderHistory(){
	}

	public OrderHistory(int shipmentID, int status, LocalDateTime updateDate, String notes){
		this.shipmentID = shipmentID;
		this.status = status;
		this.updateDate = updateDate;
		this.notes = notes;
	}

	//Getters and setters

	public int getOrderHistoryID(){
		return this.orderHistoryID;
	}

	public void setOrderHistoryID(int orderHistoryID){
		this.orderHistoryID = orderHistoryID;
	}

	public int getShipmentID(){
		return this.shipmentID;
	}

	public void setShipmentID(int shipmentID){
		this.shipmentID = shipmentID;
	}

	public int getStatus(){
		return this.status;
	}

	public void setStatus(int status){
		this.status = status;
	}

	public LocalDateTime getUpdateDate(){
		return this.updateDate;
	}

	public void setUpdateDate(LocalDateTime updateDate){
		this.updateDate = updateDate;
	}

	public String getNotes(){
		return this.notes;
	}

	public void setNotes(String notes){
		this.notes = notes;
	}

	//toString method

	@Override
	public String toString() {
		return "OrderHistory{" +
				"orderID=" + orderHistoryID +
				", shipmentID='" + shipmentID + '\'' +
				", statusID='" + status + '\'' +
				", updateDate='" + updateDate + '\'' +
				", notes='" + notes + '\'' +
				'}';
	}

}
