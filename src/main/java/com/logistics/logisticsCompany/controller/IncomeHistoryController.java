package com.logistics.logisticsCompany.controller;

import com.logistics.logisticsCompany.entities.logisticsCompany.IncomeHistory;
import com.logistics.logisticsCompany.repository.IncomeHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/income-history")
public class IncomeHistoryController {
	
	@Autowired
	private IncomeHistoryRepository incomeHistoryRepository;
	
	
	//FIXME: Implement this method
	@GetMapping
	public ResponseEntity<List<IncomeHistory>> getIncomeHistory(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
		return null;
	}
}