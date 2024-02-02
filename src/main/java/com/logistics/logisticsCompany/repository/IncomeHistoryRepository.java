package com.logistics.logisticsCompany.repository;

import com.logistics.logisticsCompany.entities.logisticsCompany.IncomeHistory;
import com.logistics.logisticsCompany.entities.logisticsCompany.LogisticsCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface IncomeHistoryRepository extends JpaRepository<IncomeHistory, Long> {
	@Query("SELECT SUM(s.priceDelivery) FROM Shipment s WHERE s.shipmentDate = :date")
	BigDecimal sumPriceDeliveryForDate(@Param("date") LocalDate date);
	
	boolean existsByLogisticsCompanyAndDateRecorded(LogisticsCompany company, LocalDate date);
}
