package com.logistics.logisticsCompany.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.logistics.logisticsCompany.entities.logisticsCompany.IncomeHistory;
import com.logistics.logisticsCompany.entities.offices.Office;
import com.logistics.logisticsCompany.entities.users.Employee;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Set;


public class LogisticsCompanyDTO {
	private long id;
	
	private String name;
	
	private BigDecimal income;
	
	public LogisticsCompanyDTO() {
	
	}
	public LogisticsCompanyDTO(long id, String name, BigDecimal income) {
		this.id = id;
		this.name = name;
		this.income = income;
	}
	
	public long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public BigDecimal getIncome() {
		return income;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setIncome(BigDecimal income) {
		this.income = income;
	}
}
