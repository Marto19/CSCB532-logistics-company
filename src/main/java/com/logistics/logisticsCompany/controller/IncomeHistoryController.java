package com.logistics.logisticsCompany.controller;

import com.logistics.logisticsCompany.DTO.incomeHistory.IncomeIntervalRequest;
import com.logistics.logisticsCompany.DTO.incomeHistory.MonthlyIncomeRequest;
import com.logistics.logisticsCompany.entities.logisticsCompany.IncomeHistory;
import com.logistics.logisticsCompany.repository.IncomeHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;
import com.logistics.logisticsCompany.service.IncomeHistoryService;
import com.logistics.logisticsCompany.DTO.incomeHistory.MonthlyIncomeRequest;


/**
 * This class is a controller for handling requests related to income history.
 * It uses Spring's @RestController annotation to indicate that it is a controller and the response bodies should be bound to the web response body.
 * It also uses @RequestMapping to map the web requests.
 */
@RestController
@RequestMapping("/api/v1/income-history")
public class IncomeHistoryController {


	/**
	 * The IncomeHistoryRepository instance used for income history-related operations.
	 */
	@Autowired
	private IncomeHistoryRepository incomeHistoryRepository;

	/**
	 * The IncomeHistoryService instance used for income history-related operations.
	 */
	@Autowired
	private IncomeHistoryService incomeHistoryService;
	
	

	// Endpoint for monthly income

	/**
	 * This method handles the POST requests for getting the monthly income.
	 * @param request the request containing the year, month and logistics company id
	 * @return the monthly income
	 */
	@PostMapping("/monthly")
	public ResponseEntity<BigDecimal> getMonthlyIncome(@RequestBody MonthlyIncomeRequest request) {//check MonthlyIncomeRequest inside dtos
		LocalDate startDate = LocalDate.of(request.getYear(), request.getMonth(), 1);
		LocalDate endDate = startDate.with(TemporalAdjusters.lastDayOfMonth());
		BigDecimal monthlyIncome = incomeHistoryService.calculatePeriodIncome(startDate, endDate, request.getLogisticsCompanyId());
		return ResponseEntity.ok(monthlyIncome);
	}
	
	//FIXME
	// Endpoint for income over a custom interval

	/**
	 * This method handles the POST requests for getting the income over a custom interval.
	 * @param request the request containing the start date, end date and logistics company id
	 * @return the income over the interval
	 */
	@PostMapping("/interval")
	public ResponseEntity<BigDecimal> getIncomeByInterval(@RequestBody IncomeIntervalRequest request) {//check IncomeIntervalRequest inside dtos
		LocalDate startDate = request.getStartDate();
		LocalDate endDate = request.getEndDate();
		Long logisticsCompanyId = request.getLogisticsCompanyId();
		
		BigDecimal income = incomeHistoryService.calculatePeriodIncome(startDate, endDate, logisticsCompanyId);
		return ResponseEntity.ok(income);
	}
	
	
	
	//FIXME CHOOSE BETWEEN THIS OR THE UPPER METHOD.

	/**
	 * This method handles the GET requests for getting the income over a custom interval.
	 * @param startDate the start date of the interval
	 * @param endDate the end date of the interval
	 * @param logisticsCompanyId the id of the logistics company
	 * @return the income over the interval
	 */
	@GetMapping("/interval")
	public ResponseEntity<BigDecimal> getIncomeByInterval(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
	                                                      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
	                                                      @RequestParam Long logisticsCompanyId) {
		BigDecimal totalIncome = incomeHistoryService.calculatePeriodIncome(startDate, endDate, logisticsCompanyId);
		return ResponseEntity.ok(totalIncome);
	}
	
	
}