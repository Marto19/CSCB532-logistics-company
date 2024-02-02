package com.logistics.logisticsCompany.DTO.incomeHistory;

import java.time.LocalDate;

public class IncomeIntervalRequest {
	private LocalDate startDate;
	private LocalDate endDate;
	private Long logisticsCompanyId;
	
	// Constructors, getters, and setters
	
public IncomeIntervalRequest() {
	}
	
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
	
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	
	public void setLogisticsCompanyId(Long logisticsCompanyId) {
		this.logisticsCompanyId = logisticsCompanyId;
	}
}