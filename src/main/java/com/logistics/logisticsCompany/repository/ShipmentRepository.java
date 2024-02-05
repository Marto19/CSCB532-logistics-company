package com.logistics.logisticsCompany.repository;

import com.logistics.logisticsCompany.entities.orders.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.math.BigDecimal;
import java.time.LocalDate;

public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
    boolean existsById(long id);
	
	//find all sent shipments registered by employee with the given ID (jpa query)
	List<Shipment> findBySenderEmployeeId(Long employeeId);
	
	//find all received shipments registered by employee with the given ID (jpa query) idk if needed...
	List<Shipment> findByReceiverEmployeeId(Long employeeId);
	
	// This method assumes you want to find shipments that have been sent but not received.
	// It uses a subquery to find all shipments that have a status history of "SENT" but do not have a status of "RECEIVED" or "DELIVERED"
	List<Shipment> findByStatusNot(String status);

	List<Shipment> findBySenderCustomerId(Long customerId);
	List<Shipment> findBySenderCustomerPhone(String phone);
	
	List<Shipment> findByReceiverCustomerId(Long customerId);
	
	//5.h.
	@Query("SELECT SUM(s.priceDelivery) FROM Shipment s WHERE s.senderEmployee.logisticsCompany.id = :companyId AND s.shipmentDate = :date AND s.isPaidDelivery = true")
	BigDecimal calculateIncomeForCompanyAndDate(@Param("companyId") Long companyId, @Param("date") LocalDate date);

	
}