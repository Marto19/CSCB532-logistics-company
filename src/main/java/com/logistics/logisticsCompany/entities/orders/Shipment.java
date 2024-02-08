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

/**
 * The Shipment class is used to represent a shipment entity.
 * It contains the id, shipment date, weight, price, is paid, total price, received date, sender office, sender customer, sender employee, receiver office, receiver customer, receiver employee, and status histories of the shipment.
 */
@Entity
@Table(name = "shipment")
public class Shipment {

	/**
	 * The id of the shipment.
	 * It is a unique identifier for the shipment.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	/**
	 * The shipment date of the shipment.
	 */
	@Column(name = "shipment_date", nullable = false)
	private LocalDate shipmentDate;

//	@Column(name = "shipmentstatus", nullable = false)
//	@Enumerated(EnumType.STRING)	//todo. update 27.01 : new table was created for shipment status (goods status)
//	private ShipmentStatus shipmentStatus;

	/**
	 * The weight of the shipment.
	 */
	@Column(name = "weight", nullable = false,  precision = 10, scale = 2)
	private BigDecimal weight;

	/**
	 * The price of the shipment.
	 */
	@Column(name = "is_paid_delivery", nullable = false)
	private boolean isPaidDelivery;

	/**
	 * The price of the shipment.
	 */
	@Column(name = "price_delivery", nullable = true, precision= 10, scale = 2)
	private BigDecimal priceDelivery;

	/**
	 * The price of the shipment.
	 */
	@Column(name = "price", nullable = true,precision= 10, scale = 2)
	private BigDecimal price;

	/**
	 * The is paid of the shipment.
	 */
	@Column(name = "is_paid", nullable = false)
	private boolean isPaid;

	/**
	 * The total price of the shipment.
	 */
	@Column(name = "total_price", nullable = true, precision= 10, scale = 2)
	private BigDecimal totalPrice;

	/**
	 * The status of the shipment.
	 */
	@Column(name = "status", nullable = true)
	private String status;

	/**
	 * The received date of the shipment.
	 */
	@Column(name = "received_date", nullable = true)
	private LocalDate receivedDate;

	/**
	 * The notes of the shipment.
	 */
	@Column(name = "notes", nullable = true)
	private String notes;
	/*
		SENDER RELATIONSHIPS - OFFICE, CUSTOMER, EMPLOYEE
	*/
	/**
	 * The sender office of the shipment.
	 * It is a many-to-one relationship between shipment and office.
	 */
	@ManyToOne
	@JoinColumn(name = "sender_office_id", nullable = true)
	private Office senderOffice;

	/**
	 * The sender customer of the shipment.
	 * It is a many-to-one relationship between shipment and customer.
	 */
	@ManyToOne
	@JoinColumn(name = "sender_customer_id", nullable = true)
	private Customer senderCustomer;

	/**
	 * The sender employee of the shipment.
	 * It is a many-to-one relationship between shipment and employee.
	 */
	@ManyToOne
	@JoinColumn(name = "sender_Employee_id", nullable = true)
	private Employee senderEmployee;

	/*
		RECEIVER RELATIONSHIPS - OFFICE, CUSTOMER, EMPLOYEE
	*/

	/**
	 * The receiver office of the shipment.
	 * It is a many-to-one relationship between shipment and office.
	 */
	@ManyToOne
	@JoinColumn(name = "receiver_office_id", nullable = true)
	private Office receiverOffice;

	/**
	 * The receiver customer of the shipment.
	 * It is a many-to-one relationship between shipment and customer.
	 */
	@ManyToOne
	@JoinColumn(name = "receiver_customer_id", nullable = true)
	private Customer receiverCustomer;

	/**
	 * The receiver employee of the shipment.
	 * It is a many-to-one relationship between shipment and employee.
	 */
	@ManyToOne
	@JoinColumn(name = "receiver_employee_id", nullable = true)
	private Employee receiverEmployee;

	////////////////////////////////CREATING THE RELATIONSHIPS/////////////////////////
	//relationship shipment/order_history - 1:n
	/**
	 * The set of status histories of the shipment.
	 * It is a one-to-many relationship between shipment and shipment status history.
	 */
	@OneToMany(mappedBy = "shipment",cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ShipmentStatusHistory> statusHistories = new ArrayList<>();//todo: think whether List or Set? (caki)

	/**
	 * The goods type of the shipment.
	 * It is a many-to-one relationship between shipment and goods type.
	 */
	@ManyToOne
	@JoinColumn(name = "goods_type_id", nullable = true)
	private GoodsType goodsType;

	/**
	 * The delivery payment type of the shipment.
	 * It is a many-to-one relationship between shipment and delivery payment type.
	 */
	@ManyToOne
	@JoinColumn(name = "delivery_payment_type_id", nullable = true)
	private DeliveryPaymentType deliveryPaymentType;

	
	
	//Constructors
	public Shipment() {
	}

	/**
	 *
	 * @param shipmentDate
	 * @param weight
	 * @param isPaidDelivery
	 * @param priceDelivery
	 * @param price
	 * @param isPaid
	 * @param receivedDate
	 * @param senderOffice
	 * @param senderCustomer
	 * @param senderEmployee
	 * @param receiverOffice
	 * @param receiverCustomer
	 * @param receiverEmployee
	 * @param statusHistories
	 * @param goodsType
	 * @param deliveryPaymentType
	 */
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