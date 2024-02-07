package com.logistics.logisticsCompany.DTO;

import com.logistics.logisticsCompany.entities.enums.DeliveryPaymentType;
import com.logistics.logisticsCompany.entities.enums.ShipmentStatus;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The DeliveryPaymentTypeDTO class is used to represent a delivery payment type data transfer object.
 * It contains the id and payment type of the delivery payment type.
 */
public class DeliveryPaymentTypeDTO {
	/**
	 * The id of the delivery payment type.
	 */
	private long id;
	/**
	 * The payment type of the delivery payment type.
	 */
	private String paymentType;

	
	// No-argument constructor


	public DeliveryPaymentTypeDTO() {
	}
	
	// All-argument constructor

	/**
	 * Constructs a DeliveryPaymentTypeDTO with the specified id and payment type.
	 * @param id the id
	 * @param paymentType the payment type
	 */
	public DeliveryPaymentTypeDTO(long id, String paymentType) {
		this.id = id;
		this.paymentType = paymentType;
	}

	/**
	 * Constructs a DeliveryPaymentTypeDTO with the specified delivery payment type.
	 * @param deliveryPaymentType the delivery payment type
	 */
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

	/**
	 * Converts a list of delivery payment types to a list of delivery payment type DTOs.
	 * @param deliveryPaymentTypes the list of delivery payment types
	 * @return the list of delivery payment type DTOs
	 */
	public static List<DeliveryPaymentTypeDTO> toDTOList(List<DeliveryPaymentType> deliveryPaymentTypes) {
		return deliveryPaymentTypes.stream().map(DeliveryPaymentTypeDTO::new).collect(Collectors.toList());
	}
}
