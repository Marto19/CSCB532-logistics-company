package com.logistics.logisticsCompany.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.logistics.logisticsCompany.entities.logisticsCompany.IncomeHistory;
import com.logistics.logisticsCompany.entities.offices.Office;
import com.logistics.logisticsCompany.entities.users.Employee;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Set;


/**
 * The LogisticsCompanyDTO class is used to represent a logistics company data transfer object.
 * It contains the id, name, and income of the logistics company.
 */
public class LogisticsCompanyDTO {
	/**
	 * The id of the logistics company.
	 */
	private long id;

	/**
	 * The name of the logistics company.
	 */
	private String name;

	/**
	 * The income of the logistics company.
	 */
	private BigDecimal income;

	/**
	 * Default constructor for the {@code LogisticsCompanyDTO} class.
	 * It is used to create an empty logistics company data transfer object.
	 * This constructor is used by the Jackson library to create an empty logistics company data transfer object during deserialization.
	 * It should not be used explicitly.
	 */
	public LogisticsCompanyDTO() {
	
	}

	/**
	 * Constructs a LogisticsCompanyDTO with the specified id, name, and income.
	 * @param id the id
	 * @param name the name
	 * @param income the income
	 */
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
