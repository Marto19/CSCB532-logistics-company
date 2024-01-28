package com.logistics.logisticsCompany.DTO;

import com.logistics.logisticsCompany.entities.enums.DeliveryPaymentType;
import com.logistics.logisticsCompany.entities.enums.ShipmentStatus;

import java.util.List;
import java.util.stream.Collectors;

public class DeliveryPaymentTypeDTO {
	private long id;
	private String paymentType;
	
	// No-argument constructor
	public DeliveryPaymentTypeDTO() {
	}
	
	// All-argument constructor
	public DeliveryPaymentTypeDTO(long id, String paymentType) {
		this.id = id;
		this.paymentType = paymentType;
	}
	
	public DeliveryPaymentTypeDTO(DeliveryPaymentType deliveryPaymentType) {
		this.id = deliveryPaymentType.getId();
		this.paymentType = deliveryPaymentType.getPaymentType();
	}
	
	// Getters and setters
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
	
	public static List<DeliveryPaymentTypeDTO> toDTOList(List<DeliveryPaymentType> deliveryPaymentTypes) {
		return deliveryPaymentTypes.stream().map(DeliveryPaymentTypeDTO::new).collect(Collectors.toList());
	}
}
