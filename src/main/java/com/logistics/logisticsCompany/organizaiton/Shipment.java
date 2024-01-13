package com.logistics.logisticsCompany.organizaiton;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "shipment")
public class Shipment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int shipmentID;

	@Column(name = "shipmentdate", nullable = false)
	private LocalDate shipmentDate;

//	@Column(name = "shipmentstatus", nullable = false)
//	@Enumerated(EnumType.STRING)	//todo
//	private ShipmentStatus shipmentStatus;

	@Column(name = "weight", nullable = false /*precision = 3, scale = 2*/)
	private double weight;

	@Column(name = "price", nullable = false/*, precision = 10, scale = 2*/)
	private BigDecimal price;

	@Column(name = "pricepaid", nullable = false)
	private boolean pricePaid;

	@Column(name = "receiveddate", nullable = true)
	private LocalDate receivedDate;

	@Column(name = "senderofficeid", nullable = false)
	private Integer senderOfficeID;

	@Column(name = "sendercustomerid", nullable = false)
	private Integer senderCustomerID;

	@Column(name = "senderemployeeid", nullable = false)
	private Integer senderEmployeeID;

	@Column(name = "receiverofficeid", nullable = false)
	private Integer receiverOfficeID;

	@Column(name = "receivercustomerid", nullable = false)
	private Integer receiverCustomerID;

	@Column(name = "receiveremployeeid", nullable = true)
	private Integer receiverEmployeeID;

	////////////////////////////////CREATING THE RELATIONSHIPS/////////////////////////
	//relationship shipment/order_history - 1:n
//	@OneToMany(mappedBy = "shipment")
//	private List<OrderHistory> orderHistoryListFromShipment = new ArrayList<>();

	//Constructors
	public Shipment() {
	}

	public Shipment(LocalDate shipmentDate, double weight, BigDecimal price, boolean pricePaid, LocalDate receivedDate, Integer senderOfficeID, Integer senderCustomerID, Integer senderEmployeeID, Integer receiverOfficeID, Integer receiverCustomerID, Integer receiverEmployeeID) {
		this.shipmentDate = shipmentDate;
		this.weight = weight;
		this.price = price;
		this.pricePaid = pricePaid;
		this.receivedDate = receivedDate;
		this.senderOfficeID = senderOfficeID;
		this.senderCustomerID = senderCustomerID;
		this.senderEmployeeID = senderEmployeeID;
		this.receiverOfficeID = receiverOfficeID;
		this.receiverCustomerID = receiverCustomerID;
		this.receiverEmployeeID = receiverEmployeeID;
	}

	public int getShipmentID() {
		return this.shipmentID;
	}

	public void setShipmentID(int shipmentID) {
		this.shipmentID = shipmentID;
	}

	public LocalDate getShipmentDate() {
		return this.shipmentDate;
	}

	public void setShipmentDate(LocalDate shipmentDate) {
		this.shipmentDate = shipmentDate;
	}

//	public ShipmentStatus getShipmentStatus() {
//		return this.shipmentStatus;
//	}
//
//	public void setShipmentStatus(ShipmentStatus shipmentStatus) {
//		this.shipmentStatus = shipmentStatus;
//	}

	public double getWeight() {
		return this.weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public boolean getPricePaid() {
		return this.pricePaid;
	}

	public void setPricePaid(boolean pricePaid) {
		this.pricePaid = pricePaid;
	}

	public LocalDate getReceivedDate() {
		return this.receivedDate;
	}

	public void setReceivedDate(LocalDate receivedDate) {
		this.receivedDate = receivedDate;
	}

	public Integer getSenderOfficeID() {
		return this.senderOfficeID;
	}

	public void setSenderOfficeID(Integer senderOfficeID) {
		this.senderOfficeID = senderOfficeID;
	}

	public Integer getSenderCustomerID() {
		return this.senderCustomerID;
	}

	public void setSenderCustomerID(Integer senderCustomerID) {
		this.senderCustomerID = senderCustomerID;
	}

	public Integer getSenderEmployeeID() {
		return this.senderEmployeeID;
	}

	public void setSenderEmployeeID(Integer senderEmployeeID) {
		this.senderEmployeeID = senderEmployeeID;
	}

	public Integer getReceiverOfficeID() {
		return this.receiverOfficeID;
	}

	public void setReceiverOfficeID(Integer receiverOfficeID) {
		this.receiverOfficeID = receiverOfficeID;
	}

	public Integer getReceiverCustomerID() {
		return this.receiverCustomerID;
	}

	public void setReceiverCustomerID(Integer receiverCustomerID) {
		this.receiverCustomerID = receiverCustomerID;
	}

	public Integer getReceiverEmployeeID() {
		return this.receiverEmployeeID;
	}

	public void setReceiverEmployeeID(Integer receiverEmployeeID) {
		this.receiverEmployeeID = receiverEmployeeID;
	}

	//toString method


	@Override
	public String toString() {
		return "Shipment{" +
				"shipmentID=" + shipmentID +
				", shipmentDate=" + shipmentDate +
//				", shipmentStatus=" + shipmentStatus +
				", weight=" + weight +
				", price=" + price +
				", pricePaid=" + pricePaid +
				", receivedDate=" + receivedDate +
				", senderOfficeID=" + senderOfficeID +
				", senderCustomerID=" + senderCustomerID +
				", senderEmployeeID=" + senderEmployeeID +
				", receiverOfficeID=" + receiverOfficeID +
				", receiverCustomerID=" + receiverCustomerID +
				", receiverEmployeeID=" + receiverEmployeeID +
				'}';
	}
}
