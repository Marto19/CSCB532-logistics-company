package com.logistics.logisticsCompany.service;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface IncomeHistoryService {
	void recordDailyIncome(BigDecimal income, Long logisticsCompanyId);
	
	BigDecimal calculatePeriodIncome(LocalDate startDate, LocalDate endDate, Long logisticsCompanyId);
}
