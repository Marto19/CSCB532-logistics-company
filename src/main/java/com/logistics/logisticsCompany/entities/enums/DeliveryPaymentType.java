package com.logistics.logisticsCompany.entities.enums;

import jakarta.persistence.*;

/**
 * The DeliveryPaymentType class is used to represent a delivery payment type entity.
 * It contains the id and payment type of the delivery payment type.
 * Its supposed to be an enum but I made it an entity to be able to add more payment types in the future.
 */
@Entity
@Table(name = "delivery_payment_type")
public class DeliveryPaymentType {
	/**
	 * The id of the delivery payment type.
	 * It is a unique identifier for the delivery payment type.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	/**
	 * The payment type of the delivery payment type.
	 */
	@Column(name = "payment_type", nullable = false)
	private String paymentType;
	
	// Constructors, getters, and setters
	public DeliveryPaymentType() {
	}
	
	public DeliveryPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getPaymentType() {
		return paymentType;
	}
	
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
}