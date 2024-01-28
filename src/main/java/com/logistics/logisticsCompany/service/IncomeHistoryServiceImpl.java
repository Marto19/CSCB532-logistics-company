package com.logistics.logisticsCompany.service;

import com.logistics.logisticsCompany.entities.logisticsCompany.IncomeHistory;
import com.logistics.logisticsCompany.entities.logisticsCompany.LogisticsCompany;
import com.logistics.logisticsCompany.repository.IncomeHistoryRepository;
import com.logistics.logisticsCompany.repository.LogisticsCompanyRepository;
import com.logistics.logisticsCompany.repository.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.List;
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
	
	@Scheduled(cron = "0 0 0 * * *") // Runs at midnight every day
	public void recordDailyIncomeForAllCompanies() {
		LocalDate yesterday = LocalDate.now().minusDays(1);
		List<LogisticsCompany> companies = logisticsCompanyRepository.findAll();
		
		for (LogisticsCompany company : companies) {
			if (!incomeHistoryExistsForDate(company, yesterday)) {
				BigDecimal dailyIncome = calculateIncomeForCompanyAndDate(company.getId(), yesterday);
				saveIncomeHistoryRecord(company, yesterday, dailyIncome);
			}
		}
	}
	
	//cast one time, when implemented only
	public void backfillIncomeForCompany(Long companyId, LocalDate startDate, LocalDate endDate) {
		LogisticsCompany company = logisticsCompanyRepository.findById(companyId)
				.orElseThrow(() -> new RuntimeException("Company not found"));
		
		for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
			if (!incomeHistoryExistsForDate(company, date)) {
				BigDecimal dailyIncome = calculateIncomeForCompanyAndDate(companyId, date);
				saveIncomeHistoryRecord(company, date, dailyIncome);
			}
		}
	}
	
	private boolean incomeHistoryExistsForDate(LogisticsCompany company, LocalDate date) {
		return incomeHistoryRepository.existsByLogisticsCompanyAndDateRecorded(company, date);
	}
	
	private BigDecimal calculateIncomeForCompanyAndDate(Long companyId, LocalDate date) {
		return shipmentRepository.calculateIncomeForCompanyAndDate(companyId, date);
	}
	
	private void saveIncomeHistoryRecord(LogisticsCompany company, LocalDate date, BigDecimal income) {
		IncomeHistory incomeHistory = new IncomeHistory();
		incomeHistory.setLogisticsCompany(company);
		incomeHistory.setDateRecorded(date);
		incomeHistory.setTotalIncome(income);
		incomeHistoryRepository.save(incomeHistory);
	}
}
