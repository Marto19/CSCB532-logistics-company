package com.logistics.logisticsCompany.service;

import com.logistics.logisticsCompany.entities.logisticsCompany.IncomeHistory;
import com.logistics.logisticsCompany.entities.logisticsCompany.LogisticsCompany;
import com.logistics.logisticsCompany.repository.IncomeHistoryRepository;
import com.logistics.logisticsCompany.repository.LogisticsCompanyRepository;
import com.logistics.logisticsCompany.repository.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class IncomeHistoryServiceImpl implements IncomeHistoryService{
	
	@Autowired
	private LogisticsCompanyRepository logisticsCompanyRepository;
	
	@Autowired
	private ShipmentRepository shipmentRepository;
	
	@Autowired
	private IncomeHistoryRepository incomeHistoryRepository;
	
	
	@Override
	public void recordDailyIncome(BigDecimal income, Long logisticsCompanyId) {
		LocalDate today = LocalDate.now();
		
		LogisticsCompany logisticsCompany = logisticsCompanyRepository.findById(logisticsCompanyId)
				.orElseThrow(() -> new RuntimeException("Logistics company not found with ID: " + logisticsCompanyId));
		
		IncomeHistory incomeHistory = incomeHistoryRepository
				.findByDateRecordedAndLogisticsCompany(today, logisticsCompany)
				.orElse(new IncomeHistory(today, BigDecimal.ZERO, logisticsCompany));
		
		incomeHistory.setTotalIncome(incomeHistory.getTotalIncome().add(income));
		incomeHistoryRepository.save(incomeHistory);
	}
	
	@Override
	public BigDecimal calculatePeriodIncome(LocalDate startDate, LocalDate endDate, Long logisticsCompanyId) {
		return incomeHistoryRepository.findTotalIncomeByDateRangeAndLogisticsCompanyId(startDate, endDate, logisticsCompanyId)
				.orElse(BigDecimal.ZERO); // Assuming you have a method in the repository to aggregate income
	}
}
