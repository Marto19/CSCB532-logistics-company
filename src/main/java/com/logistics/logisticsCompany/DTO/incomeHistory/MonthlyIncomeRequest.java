package com.logistics.logisticsCompany.DTO.incomeHistory;

/**
 * The MonthlyIncomeRequest class is used to represent a request for retrieving monthly income history.
 * It contains the year, month, and logistics company ID.
 */
public class MonthlyIncomeRequest {
	private int year;
	private int month;
	private Long logisticsCompanyId;
	
	// Constructors, getters, and setters

	public MonthlyIncomeRequest() {
	}

	/**
	 * Constructs a MonthlyIncomeRequest with the specified year, month, and logistics company ID.
	 * @param year the year
	 * @param month the month
	 * @param logisticsCompanyId the logistics company ID
	 */
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