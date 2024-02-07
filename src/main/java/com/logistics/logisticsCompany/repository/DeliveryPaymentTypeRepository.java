package com.logistics.logisticsCompany.repository;

import com.logistics.logisticsCompany.entities.enums.DeliveryPaymentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for DeliveryPaymentType entities.
 */
@Repository
public interface DeliveryPaymentTypeRepository extends JpaRepository<DeliveryPaymentType, Long> {

	/**
	 * Check if a delivery payment type exists with the given payment type or ID.
	 *
	 * @param paymentType The payment type to check for existence.
	 * @param id          The ID of the delivery payment type to check for existence.
	 * @return True if a delivery payment type with the given payment type or ID exists, false otherwise.
	 */
	boolean existsByPaymentTypeOrId(String paymentType, Long id);
	
	//delivery paymenttype(name) return.
	/**
	 * Retrieve a delivery payment type by its name.
	 *
	 * @param paymentType The name of the payment type to search for.
	 * @return An Optional containing the delivery payment type if found, otherwise empty.
	 */
	Optional<DeliveryPaymentType> findByPaymentType(String paymentType);
	
}