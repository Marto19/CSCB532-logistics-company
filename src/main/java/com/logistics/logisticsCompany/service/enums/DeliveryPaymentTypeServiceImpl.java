package com.logistics.logisticsCompany.service.enums;

import com.logistics.logisticsCompany.entities.enums.DeliveryPaymentType;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.logistics.logisticsCompany.repository.DeliveryPaymentTypeRepository;

import java.util.List;

@Service
public class DeliveryPaymentTypeServiceImpl implements DeliveryPaymentTypeService{
	@Autowired
	private DeliveryPaymentTypeRepository deliveryPaymentTypeRepository;
	
	@Override
	public void createDeliveryPaymentType(DeliveryPaymentType deliveryPaymentType) {
		if (deliveryPaymentType.getPaymentType() == null || deliveryPaymentType.getPaymentType().isEmpty()) {
			throw new IllegalArgumentException("Type name must not be null or empty");
		}
		deliveryPaymentTypeRepository.save(deliveryPaymentType);
	}
	@Override
	public void deleteDeliveryPaymentType(long deliveryPaymentTypeId) {
		deliveryPaymentTypeRepository.deleteById(deliveryPaymentTypeId);
	}
	
	@Override
	public List<DeliveryPaymentType> getAllDeliveryPaymentType() {
		return deliveryPaymentTypeRepository.findAll();
	}
	
	@Override
	public boolean existsByDeliveryPaymentTypeOrId(String deliveryPaymentType, long deliveryPaymentTypeId) {
		return deliveryPaymentTypeRepository.existsByPaymentTypeOrId(deliveryPaymentType, deliveryPaymentTypeId);
	}
	
}