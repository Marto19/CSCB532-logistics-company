package com.logistics.logisticsCompany.entities.enums;

import jakarta.persistence.*;

@Entity
@Table(name = "delivery_payment_type")
public class DeliveryPaymentType {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "type_name", nullable = false)
	private String typeName;
	
	// Constructors, getters, and setters
	public DeliveryPaymentType() {
	}
	
	public DeliveryPaymentType(String typeName) {
		this.typeName = typeName;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getTypeName() {
		return typeName;
	}
	
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
}