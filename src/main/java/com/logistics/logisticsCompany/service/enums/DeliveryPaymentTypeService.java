package com.logistics.logisticsCompany.service.enums;

import com.logistics.logisticsCompany.entities.enums.DeliveryPaymentType;

import java.util.List;

public interface DeliveryPaymentTypeService {
	
	void createDeliveryPaymentType(DeliveryPaymentType deliveryPaymentType);
	
	void deleteDeliveryPaymentType(long deliveryPaymentTypeId);
	
	List<DeliveryPaymentType> getAllDeliveryPaymentType();
	
	boolean existsByDeliveryPaymentTypeOrId(String deliveryPaymentType, long deliveryPaymentTypeId);
}
