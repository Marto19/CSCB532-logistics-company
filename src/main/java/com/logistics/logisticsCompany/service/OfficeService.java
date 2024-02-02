package com.logistics.logisticsCompany.service;

import com.logistics.logisticsCompany.entities.offices.Office;

import java.util.List;
import java.util.Optional;

public interface OfficeService {

    void createOffice(Office office);

    List<Office> getAllOffices();

    void updateOffice(long officeId, Office updatedOffice);

    void deleteOffice(long officeId);
	
	Optional<Office> getOfficeById(Long id);
}
