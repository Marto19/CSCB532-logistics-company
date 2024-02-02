package com.logistics.logisticsCompany.entities.logisticsCompany;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "income_history")
public class IncomeHistory {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "date_recorded")
	private LocalDate dateRecorded;
	
	@Column(name = "total_income", precision = 19, scale = 2)
	private BigDecimal totalIncome;
	
	@ManyToOne
	@JoinColumn(name = "logistics_company_id", nullable = false)
	private LogisticsCompany logisticsCompany;
	
	// Constructors, getters, and setters
	
	public IncomeHistory() {
	}
	
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
