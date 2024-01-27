package com.logistics.logisticsCompany.entities.orders;

import com.logistics.logisticsCompany.entities.enums.GoodsType;
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

	@Column(name = "is_paid", nullable = false)
	private boolean isPaid;

	@Column(name = "receivedDate", nullable = true)
	private LocalDate receivedDate;

	/*
		SENDER RELATIONSHIPS - OFFICE, CUSTOMER, EMPLOYEE
	*/
	@ManyToOne
	@JoinColumn(name = "sender_office_id", nullable = false)
	private Office senderOffice;

	@ManyToOne
	@JoinColumn(name = "sender_customer_id", nullable = false)
	private Customer senderCustomer;

	@ManyToOne
	@JoinColumn(name = "sender_Employee_id", nullable = false)
	private Employee senderEmployee;

	/*
		RECEIVER RELATIONSHIPS - OFFICE, CUSTOMER, EMPLOYEE
	*/

	@ManyToOne
	@JoinColumn(name = "receiver_office_id", nullable = false)
	private Office receiverOffice;

	@ManyToOne
	@JoinColumn(name = "receiver_customer_id", nullable = false)
	private Customer receiverCustomer;

	@ManyToOne
	@JoinColumn(name = "receiver_employee_id", nullable = true)
	private Employee receiverEmployee;

	////////////////////////////////CREATING THE RELATIONSHIPS/////////////////////////
	//relationship shipment/order_history - 1:n
	@OneToMany(mappedBy = "shipment")

	private List<ShipmentStatusHistory> statusHistories = new ArrayList<>();//todo: think whether List or Set? (caki)

	@ManyToOne
	private GoodsType goodsType;

	//Constructors
	public Shipment() {
	}

	public Shipment(LocalDate shipmentDate, BigDecimal weight, BigDecimal price, boolean isPaid, LocalDate receivedDate, Integer senderOfficeID, Integer senderCustomerID, Integer senderEmployeeID, Integer receiverOfficeID, Integer receiverCustomerID, Integer receiverEmployeeID) {
		this.shipmentDate = shipmentDate;
		this.weight = weight;
		this.price = price;
		this.isPaid = isPaid;
		this.receivedDate = receivedDate;
	}

	//Getters and Setters

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

	public boolean isPaid() {
		return isPaid;
	}

	public void setIsPaid(boolean paid) {
		isPaid = paid;
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

	public List<ShipmentStatusHistory> getStatusHistories() {
		return statusHistories;
	}

	public void setStatusHistories(List<ShipmentStatusHistory> orderHistories) {
		this.statusHistories = orderHistories;
	}

	@Override
	public String toString() {
		return "Shipment{" +
				"id=" + id +
				", shipmentDate=" + shipmentDate +
				", weight=" + weight +
				", price=" + price +
				", isPaid=" + isPaid +
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