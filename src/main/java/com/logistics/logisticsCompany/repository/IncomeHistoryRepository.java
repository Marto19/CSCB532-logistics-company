package com.logistics.logisticsCompany.repository;

import com.logistics.logisticsCompany.entities.logisticsCompany.IncomeHistory;
import com.logistics.logisticsCompany.entities.logisticsCompany.LogisticsCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
/**
 * Repository interface for IncomeHistory entities.
 */
public interface IncomeHistoryRepository extends JpaRepository<IncomeHistory, Long> {
	/**
	 * Calculate the sum of price delivery for shipments on a specific date.
	 *
	 * @param date The date to calculate the sum for.
	 * @return The total sum of price delivery for the given date.
	 */
	@Query("SELECT SUM(s.priceDelivery) FROM Shipment s WHERE s.shipmentDate = :date")
	BigDecimal sumPriceDeliveryForDate(@Param("date") LocalDate date);
	boolean existsByLogisticsCompanyAndDateRecorded(LogisticsCompany company, LocalDate date);


	/**
	 * Retrieve an income history record by its date recorded and logistics company.
	 *
	 * @param dateRecorded      The date the income history was recorded.
	 * @param logisticsCompany  The logistics company the income history is for.
	 * @return An Optional containing the income history if found, otherwise empty.
	 */
	Optional<IncomeHistory> findByDateRecordedAndLogisticsCompany(LocalDate dateRecorded, LogisticsCompany logisticsCompany);

	/**
	 * Retrieve the total income for a logistics company within a specific date range.
	 *
	 * @param startDate         The start date of the date range.
	 * @param endDate           The end date of the date range.
	 * @param logisticsCompanyId The ID of the logistics company to retrieve the total income for.
	 * @return The total income for the logistics company within the date range.
	 */
	@Query("SELECT SUM(ih.totalIncome) FROM IncomeHistory ih WHERE ih.dateRecorded BETWEEN :startDate AND :endDate AND ih.logisticsCompany.id = :logisticsCompanyId")
	Optional<BigDecimal> findTotalIncomeByDateRangeAndLogisticsCompanyId(LocalDate startDate, LocalDate endDate, Long logisticsCompanyId);
}
