package com.logistics.logisticsCompany.DTO.incomeHistory;

import java.time.LocalDate;

/**
 * The IncomeIntervalRequest class is used to represent a request for retrieving income history within a specified interval.
 * It contains the start date, end date, and logistics company ID.
 */
public class IncomeIntervalRequest {
	private LocalDate startDate;
	private LocalDate endDate;
	private Long logisticsCompanyId;
	
	// Constructors, getters, and setters
	
public IncomeIntervalRequest() {
	}

	/**
	 * Constructs an IncomeIntervalRequest with the specified start date, end date, and logistics company ID.
	 * @param startDate the start date
	 * @param endDate the end date
	 * @param logisticsCompanyId the logistics company ID
	 */
	public IncomeIntervalRequest(LocalDate startDate, LocalDate endDate, Long logisticsCompanyId) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.logisticsCompanyId = logisticsCompanyId;
	}
	
	public LocalDate getStartDate() {
		return startDate;
	}
	
	public LocalDate getEndDate() {
		return endDate;
	}
	
	public Long getLogisticsCompanyId() {
		return logisticsCompanyId;
	}
	
}