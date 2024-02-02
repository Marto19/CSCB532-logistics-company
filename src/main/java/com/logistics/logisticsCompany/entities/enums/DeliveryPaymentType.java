package com.logistics.logisticsCompany.entities.enums;

import jakarta.persistence.*;

@Entity
@Table(name = "delivery_payment_type")
public class DeliveryPaymentType {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
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