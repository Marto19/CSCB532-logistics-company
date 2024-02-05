package com.logistics.logisticsCompany.entities.orders;

import com.logistics.logisticsCompany.entities.enums.GoodsType;
import com.logistics.logisticsCompany.entities.offices.Office;
import com.logistics.logisticsCompany.entities.users.Customer;
import com.logistics.logisticsCompany.entities.users.Employee;
import com.logistics.logisticsCompany.entities.enums.DeliveryPaymentType;
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
//	@Enumerated(EnumType.STRING)	//todo. update 27.01 : new table was created for shipment status (goods status)
//	private ShipmentStatus shipmentStatus;

	@Column(name = "weight", nullable = false,  precision = 10, scale = 2)
	private BigDecimal weight;

	@Column(name = "is_paid_delivery", nullable = false)
	private boolean isPaidDelivery;
	
	@Column(name = "price_delivery", nullable = true, precision= 10, scale = 2)
	private BigDecimal priceDelivery;
	
	@Column(name = "price", nullable = true,precision= 10, scale = 2)
	private BigDecimal price;

	@Column(name = "is_paid", nullable = false)
	private boolean isPaid;

	@Column(name = "total_price", nullable = true, precision= 10, scale = 2)
	private BigDecimal totalPrice;
	
	@Column(name = "status", nullable = true)
	private String status;
	@Column(name = "received_date", nullable = true)
	private LocalDate receivedDate;

	@Column(name = "notes", nullable = true)
	private String notes;
	/*
		SENDER RELATIONSHIPS - OFFICE, CUSTOMER, EMPLOYEE
	*/
	@ManyToOne
	@JoinColumn(name = "sender_office_id", nullable = true)
	private Office senderOffice;

	@ManyToOne
	@JoinColumn(name = "sender_customer_id", nullable = true)
	private Customer senderCustomer;

	@ManyToOne
	@JoinColumn(name = "sender_Employee_id", nullable = true)
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
	@JoinColumn(name = "goods_type_id", nullable = true)
	private GoodsType goodsType;
	
	@ManyToOne
	@JoinColumn(name = "delivery_payment_type_id", nullable = true)
	private DeliveryPaymentType deliveryPaymentType;

	
	
	//Constructors
	public Shipment() {
	}
	
	public Shipment(LocalDate shipmentDate, BigDecimal weight, boolean isPaidDelivery, BigDecimal priceDelivery, BigDecimal price, boolean isPaid, LocalDate receivedDate, Office senderOffice, Customer senderCustomer, Employee senderEmployee, Office receiverOffice, Customer receiverCustomer, Employee receiverEmployee, List<ShipmentStatusHistory> statusHistories, GoodsType goodsType, DeliveryPaymentType deliveryPaymentType) {
		this.shipmentDate = shipmentDate;
		this.weight = weight;
		this.isPaidDelivery = isPaidDelivery;
		this.priceDelivery = priceDelivery;
		this.price = price;
		this.isPaid = isPaid;
		this.receivedDate = receivedDate;
		this.senderOffice = senderOffice;
		this.senderCustomer = senderCustomer;
		this.senderEmployee = senderEmployee;
		this.receiverOffice = receiverOffice;
		this.receiverCustomer = receiverCustomer;
		this.receiverEmployee = receiverEmployee;
		this.statusHistories = statusHistories;
		this.goodsType = goodsType;
		this.deliveryPaymentType = deliveryPaymentType;
	}
	
	public Shipment(LocalDate shipmentDate, BigDecimal weight, BigDecimal price, boolean isPaid, BigDecimal priceDelivery, LocalDate receivedDate, Integer senderOfficeID, Integer senderCustomerID, Integer senderEmployeeID, Integer receiverOfficeID, Integer receiverCustomerID, Integer receiverEmployeeID, GoodsType goodsType, DeliveryPaymentType deliveryPaymentType) {
		this.shipmentDate = shipmentDate;
		this.weight = weight;
		this.price = price;
		this.isPaid = isPaid;
		this.receivedDate = receivedDate;
		this.goodsType = goodsType;
		this.deliveryPaymentType = deliveryPaymentType;
		// Initialize other fields as necessary
	}
	public Shipment(LocalDate shipmentDate, BigDecimal weight, BigDecimal price, boolean isPaid, LocalDate receivedDate) {
		this.shipmentDate = shipmentDate;
		this.weight = weight;
		this.price = price;
		this.isPaid = isPaid;
		this.receivedDate = receivedDate;
	}

	//Getters and Setters
	
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	public String getStatus() {
		return status;
	}
	
	public String getNotes() {
		return notes;
	}
	
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}
	
	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	public void setIsPaidDelivery(boolean isPaidDelivery) {
		this.isPaidDelivery = isPaidDelivery;
	}
	
	public void setPriceDelivery(BigDecimal priceDelivery) {
		this.priceDelivery = priceDelivery;
	}
	
	public void setIsPaid(boolean isPaid) {
		this.isPaid = isPaid;
	}
	
	public void setGoodsType(GoodsType goodsType) {
		this.goodsType = goodsType;
	}
	
	public void setDeliveryPaymentType(DeliveryPaymentType deliveryPaymentType) {
		this.deliveryPaymentType = deliveryPaymentType;
	}
	
	public boolean getIsPaidDelivery() {
		return isPaidDelivery;
	}
	
	public BigDecimal getPriceDelivery() {
		return priceDelivery;
	}
	
	public GoodsType getGoodsType() {
		return goodsType;
	}
	
	public DeliveryPaymentType getDeliveryPaymentType() {
		return deliveryPaymentType;
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

	public boolean getIsPaid() {
		return isPaid;
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
				", totalPrice=" + totalPrice +
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