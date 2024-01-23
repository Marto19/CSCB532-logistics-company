package com.logistics.logisticsCompany.entities.orders;


import com.logistics.logisticsCompany.entities.goods.Goods;
import com.logistics.logisticsCompany.entities.users.Customer;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "orderhistory")
public class OrderHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "status", nullable = false)
	private int status;

	@Column(name = "update_date", nullable = false)
	private LocalDateTime updateDate;

	@Column(name = "notes", nullable = true)
	private String notes;

	////////////////////////////////CREATING THE RELATIONSHIPS/////////////////////////
	//relationship order_history/shipment - n:1
	@ManyToOne
	@JoinColumn(name = "shipment_id")
	private Shipment shipment;

	@OneToMany(mappedBy = "orderHistory")
	private List<Goods> goodsList;

	@ManyToOne
	private Customer customer;


	//Constructors
	public OrderHistory(){
	}

	public OrderHistory(int shipmentID, int status, LocalDateTime updateDate, String notes){
		this.status = status;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

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

	@Override
	public String toString() {
		return "OrderHistory{" +
				"id=" + id +
				", status=" + status +
				", updateDate=" + updateDate +
				", notes='" + notes + '\'' +
				'}';
	}
}
