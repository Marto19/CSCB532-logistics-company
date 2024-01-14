package com.logistics.logisticsCompany.entities.orders;

import com.logistics.logisticsCompany.entities.offices.Office;
import com.logistics.logisticsCompany.entities.users.Customer;
import com.logistics.logisticsCompany.entities.users.Employee;
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
	private int id;

	@Column(name = "shipmentDate", nullable = false)
	private LocalDate shipmentDate;

//	@Column(name = "shipmentstatus", nullable = false)
//	@Enumerated(EnumType.STRING)	//todo
//	private ShipmentStatus shipmentStatus;

	@Column(name = "weight", nullable = false,  precision = 3, scale = 2)
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
	@JoinColumn(name = "senderOfficeId", nullable = false)
	private Office senderOffice;
	
	@ManyToOne
	@JoinColumn(name = "sendercustomerId", nullable = false)
	private Customer senderCustomer;

	@ManyToOne
	@JoinColumn(name = "senderEmployeeId", nullable = false)
	private Employee senderEmployee;
	
	/*
		RECEIVER RELATIONSHIPS - OFFICE, CUSTOMER, EMPLOYEE
	*/
	
	
	@ManyToOne
	@JoinColumn(name = "receiverOfficeId", nullable = false)
	private Office receiverOffice;

	@ManyToOne
	@JoinColumn(name = "receiverCustomerId", nullable = false)
	private Customer receiverCustomer;

	@ManyToOne
	@JoinColumn(name = "receiverEmployeeId", nullable = true)
	private Employee receiverEmployee;

	////////////////////////////////CREATING THE RELATIONSHIPS/////////////////////////
	//relationship shipment/order_history - 1:n
	@OneToMany(mappedBy = "shipment")
	private List<OrderHistory> orderHistories = new ArrayList<>();//todo: think whether List or Set? (caki)
	
	
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

	
}
