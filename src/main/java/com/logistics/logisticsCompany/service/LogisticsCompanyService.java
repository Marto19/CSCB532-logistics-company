package com.logistics.logisticsCompany.service;

import com.logistics.logisticsCompany.DTO.LogisticsCompanyDTO;
import com.logistics.logisticsCompany.entities.logisticsCompany.LogisticsCompany;
import com.logistics.logisticsCompany.entities.offices.Office;

import java.util.List;
import java.util.Optional;

public interface LogisticsCompanyService {
    void addOffice(Office office);

    List<Office> getAllOffices();

    void createLogisticsCompany(LogisticsCompanyDTO logisticsCompanyDTO);

    List<LogisticsCompany> getAllLogisticsCompanies();

    Optional<LogisticsCompany> getLogisticsCompanyById(long id);

    void updateLogisticsCompany(long companyId, LogisticsCompany updatedCompany);

    void deleteLogisticsCompany(long companyId);
}
