package com.logistics.logisticsCompany.service;

import com.logistics.logisticsCompany.DTO.OfficeDTO;
import com.logistics.logisticsCompany.entities.offices.Office;

import java.util.List;
import java.util.Optional;

public interface OfficeService {

	
	void createOffice(OfficeDTO officeDTO);
	
	List<Office> getAllOffices();

    void updateOffice(long officeId, Office updatedOffice);

    void deleteOffice(long officeId);
	
	Optional<Office> getOfficeById(Long id);
	
	Office getOfficeByEmployeeId(Long employeeId);
}
