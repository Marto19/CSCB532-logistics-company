package com.logistics.logisticsCompany.entities.orders;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.logistics.logisticsCompany.entities.offices.Office;
import com.logistics.logisticsCompany.entities.users.Customer;
import com.logistics.logisticsCompany.entities.users.Employee;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "shipment")
public class Shipment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "shipment_date", nullable = false)
	private LocalDate shipmentDate;

//	@Column(name = "shipmentstatus", nullable = false)
//	@Enumerated(EnumType.STRING)	//todo
//	private ShipmentStatus shipmentStatus;

	@Column(name = "weight", nullable = false,  precision = 10, scale = 2)
	private BigDecimal weight;

	@Column(name = "price", nullable = false,precision= 10, scale = 2)
	private BigDecimal price;

	@Column(name = "pricepaid", nullable = false)
	private boolean pricePaid;

	@Column(name = "receivedDate", nullable = true)
	private LocalDate receivedDate;
	
	/*
		SENDER RELATIONSHIPS - OFFICE, CUSTOMER, EMPLOYEE
	*/
	@ManyToOne
	@JoinColumn(name = "sender_office_id", nullable = false)
	@JsonBackReference
	private Office senderOffice;
	
	@ManyToOne
	@JoinColumn(name = "sender_customer_id", nullable = false)
	@JsonBackReference
	private Customer senderCustomer;

	@ManyToOne
	@JoinColumn(name = "sender_Employee_id", nullable = false)
	@JsonIgnore
	private Employee senderEmployee;
	
	/*
		RECEIVER RELATIONSHIPS - OFFICE, CUSTOMER, EMPLOYEE
	*/
	
	
	@ManyToOne
	@JoinColumn(name = "receiver_office_id", nullable = false)
	@JsonIgnore
	private Office receiverOffice;

	@ManyToOne
	@JoinColumn(name = "receiver_customer_id", nullable = false)
	@JsonIgnore
	private Customer receiverCustomer;

	@ManyToOne
	@JoinColumn(name = "receiver_employee_id", nullable = true)
	@JsonIgnore
	private Employee receiverEmployee;

	////////////////////////////////CREATING THE RELATIONSHIPS/////////////////////////
	//relationship shipment/order_history - 1:n
	@OneToMany(mappedBy = "shipment", cascade = CascadeType.ALL)
	private List<OrderHistory> orderHistories;//todo: think whether List or Set? (caki)
	//its one to many because when trasposrting something, the employee usually transports more than one goods, therefore itll be assiciated with more than one order history

	//Constructors
	public Shipment() {
	}

	public Shipment(LocalDate shipmentDate, BigDecimal weight, BigDecimal price, boolean pricePaid, LocalDate receivedDate, Integer senderOfficeID, Integer senderCustomerID, Integer senderEmployeeID, Integer receiverOfficeID, Integer receiverCustomerID, Integer receiverEmployeeID) {
		this.shipmentDate = shipmentDate;
		this.weight = weight;
		this.price = price;
		this.pricePaid = pricePaid;
		this.receivedDate = receivedDate;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public LocalDate getShipmentDate() {
		return shipmentDate;
	}

	public void setShipmentDate(LocalDate shipmentDate) {
		this.shipmentDate = shipmentDate;
	}

	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public boolean isPricePaid() {
		return pricePaid;
	}

	public void setPricePaid(boolean pricePaid) {
		this.pricePaid = pricePaid;
	}

	public LocalDate getReceivedDate() {
		return receivedDate;
	}

	public void setReceivedDate(LocalDate receivedDate) {
		this.receivedDate = receivedDate;
	}

	public Office getSenderOffice() {
		return senderOffice;
	}

	public void setSenderOffice(Office senderOffice) {
		this.senderOffice = senderOffice;
	}

	public Customer getSenderCustomer() {
		return senderCustomer;
	}

	public void setSenderCustomer(Customer senderCustomer) {
		this.senderCustomer = senderCustomer;
	}

	public Employee getSenderEmployee() {
		return senderEmployee;
	}

	public void setSenderEmployee(Employee senderEmployee) {
		this.senderEmployee = senderEmployee;
	}

	public Office getReceiverOffice() {
		return receiverOffice;
	}

	public void setReceiverOffice(Office receiverOffice) {
		this.receiverOffice = receiverOffice;
	}

	public Customer getReceiverCustomer() {
		return receiverCustomer;
	}

	public void setReceiverCustomer(Customer receiverCustomer) {
		this.receiverCustomer = receiverCustomer;
	}

	public Employee getReceiverEmployee() {
		return receiverEmployee;
	}

	public void setReceiverEmployee(Employee receiverEmployee) {
		this.receiverEmployee = receiverEmployee;
	}

	public List<OrderHistory> getOrderHistories() {
		return orderHistories;
	}

	public void setOrderHistories(List<OrderHistory> orderHistories) {
		this.orderHistories = orderHistories;
	}

	@Override
	public String toString() {
		return "Shipment{" +
				"id=" + id +
				", shipmentDate=" + shipmentDate +
				", weight=" + weight +
				", price=" + price +
				", pricePaid=" + pricePaid +
				", receivedDate=" + receivedDate +
				", senderOffice=" + senderOffice +
				", senderCustomer=" + senderCustomer +
				", senderEmployee=" + senderEmployee +
				", receiverOffice=" + receiverOffice +
				", receiverCustomer=" + receiverCustomer +
				", receiverEmployee=" + receiverEmployee +
				'}';
	}
}
