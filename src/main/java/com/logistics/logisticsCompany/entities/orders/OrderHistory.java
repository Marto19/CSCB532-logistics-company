package com.logistics.logisticsCompany.entities.orders;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "orderhistory")
public class OrderHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "status", nullable = false)
	private int status;

	@Column(name = "updateDate", nullable = false)
	private LocalDateTime updateDate;

	@Column(name = "notes", nullable = true)
	private String notes;

	////////////////////////////////CREATING THE RELATIONSHIPS/////////////////////////
	//relationship order_history/shipment - n:1
	@ManyToOne
	@JoinColumn(name = "shipmentid")
	private Shipment shipment;

	//Constructors
	public OrderHistory(){
	}

	public OrderHistory(int shipmentID, int status, LocalDateTime updateDate, String notes){
		this.status = status;
		this.updateDate = updateDate;
		this.notes = notes;
	}

	//Getters and setters

	
}
