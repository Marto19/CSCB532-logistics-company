package com.logistics.logisticsCompany.repository;

import com.logistics.logisticsCompany.entities.orders.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Repository interface for Shipment entities
 */
public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
	/**
	 * Check if a shipment exists with the given ID.
	 * @param id
	 * @return
	 */
    boolean existsById(long id);

	/**
	 * Find all sent shipments registered by employee with the given ID (jpa query)
	 * @param employeeId
	 * @return
	 */
	List<Shipment> findBySenderEmployeeId(Long employeeId);
	

	/**
	 * Find all shipments received by employee with the given ID (jpa query)
	 * @param employeeId
	 * @return
	 */
	List<Shipment> findByReceiverEmployeeId(Long employeeId);

	/**
	 * This method assumes you want to find shipments that have been sent but not received.
	 * It uses a subquery to find all shipments that have a status history of "SENT" but do not have a status of "RECEIVED" or "DELIVERED"
	 * @param status
	 * @return
	 */
	List<Shipment> findByStatusNot(String status);

	/**
	 * Find all shipments received by customer with the given ID (jpa query)
	 * @param customerId
	 * @return
	 */
	List<Shipment> findBySenderCustomerId(Long customerId);

	/**
	 * Find all shipments received by customer with the given phone number
	 * @param phone
	 * @return
	 */
	List<Shipment> findBySenderCustomerPhone(String phone);


	/**
	 * Find all shipments received by customer with the given ID
	 * @param customerId
	 * @return
	 */
	List<Shipment> findByReceiverCustomerId(Long customerId);
	
	//5.h.
	/**
	 * This method calculates the total income for a specific logistics company on a specific date.
	 * It uses a custom query to sum up the delivery price of all shipments where:
	 * - The sender employee belongs to the specified logistics company,
	 * - The shipment date is the specified date, and
	 * - The delivery has been paid.
	 *
	 * @param companyId the ID of the logistics company
	 * @param date the date for which the income is calculated
	 * @return the total income for the specified logistics company on the specified date
	 */
	@Query("SELECT SUM(s.priceDelivery) FROM Shipment s WHERE s.senderEmployee.logisticsCompany.id = :companyId AND s.shipmentDate = :date AND s.isPaidDelivery = true")
	BigDecimal calculateIncomeForCompanyAndDate(@Param("companyId") Long companyId, @Param("date") LocalDate date);

	
}