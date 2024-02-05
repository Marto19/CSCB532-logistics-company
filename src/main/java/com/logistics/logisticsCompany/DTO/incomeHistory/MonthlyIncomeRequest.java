package com.logistics.logisticsCompany.DTO.incomeHistory;

public class MonthlyIncomeRequest {
	private int year;
	private int month;
	private Long logisticsCompanyId;
	
	// Constructors, getters, and setters
	
	public MonthlyIncomeRequest() {
	}
	
	public MonthlyIncomeRequest(int year, int month, Long logisticsCompanyId) {
		this.year = year;
		this.month = month;
		this.logisticsCompanyId = logisticsCompanyId;
	}
	
	public void setYear(int year) {
		this.year = year;
	}
	
	public void setMonth(int month) {
		this.month = month;
	}
	
	public void setLogisticsCompanyId(Long logisticsCompanyId) {
		this.logisticsCompanyId = logisticsCompanyId;
	}
	
	public int getYear() {
		return year;
	}
	
	public int getMonth() {
		return month;
	}
	
	public Long getLogisticsCompanyId() {
		return logisticsCompanyId;
	}
}