package com.logistics.logisticsCompany.entities.logisticsCompany;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * The IncomeHistory class is used to represent an income history entity.
 * It contains the id, date recorded, total income, and logistics company of the income history.
 */
@Entity
@Table(name = "income_history")
public class IncomeHistory {

	/**
	 * The id of the income history.
	 * It is a unique identifier for the income history.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	/**
	 * The date recorded of the income history.
	 */
	@Column(name = "date_recorded")
	private LocalDate dateRecorded;

	/**
	 * The total income of the income history.
	 */
	@Column(name = "total_income", precision = 19, scale = 2)
	private BigDecimal totalIncome;

	/**
	 * The logistics company of the income history.
	 */
	@ManyToOne
	@JoinColumn(name = "logistics_company_id", nullable = false)
	private LogisticsCompany logisticsCompany;
	
	// Constructors, getters, and setters


	public IncomeHistory() {
	}

	/**
	 * @param dateRecorded
	 * @param totalIncome
	 * @param logisticsCompany
	 */
	public IncomeHistory(LocalDate dateRecorded, BigDecimal totalIncome, LogisticsCompany logisticsCompany) {
		this.dateRecorded = dateRecorded;
		this.totalIncome = totalIncome;
		this.logisticsCompany = logisticsCompany;
	}
	
	public long getId() {
		return id;
	}
	
	public LocalDate getDateRecorded() {
		return dateRecorded;
	}
	
	public BigDecimal getTotalIncome() {
		return totalIncome;
	}
	
	public LogisticsCompany getLogisticsCompany() {
		return logisticsCompany;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public void setDateRecorded(LocalDate dateRecorded) {
		this.dateRecorded = dateRecorded;
	}
	
	public void setTotalIncome(BigDecimal totalIncome) {
		this.totalIncome = totalIncome;
	}
	
	public void setLogisticsCompany(LogisticsCompany logisticsCompany) {
		this.logisticsCompany = logisticsCompany;
	}
}
