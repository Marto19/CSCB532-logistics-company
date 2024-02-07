package com.logistics.logisticsCompany.DTO;

import java.util.Map;

public class ApiErrorResponse {
	private String error;
	private Map<String, String> details;
	
	public ApiErrorResponse(String error, Map<String, String> details) {
		this.error = error;
		this.details = details;
	}
	
	// Getters
	public String getError() {
		return error;
	}
	
	public Map<String, String> getDetails() {
		return details;
	}
}
