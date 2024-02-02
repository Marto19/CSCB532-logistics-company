package com.logistics.logisticsCompany.repository;

import com.logistics.logisticsCompany.entities.enums.DeliveryPaymentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeliveryPaymentTypeRepository extends JpaRepository<DeliveryPaymentType, Long> {
	
	
	boolean existsByPaymentTypeOrId(String paymentType, Long id);
	
	//delivery paymenttype(name) return.
	Optional<DeliveryPaymentType> findByPaymentType(String paymentType);
	
}