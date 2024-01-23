package com.logistics.logisticsCompany.service;

import com.logistics.logisticsCompany.entities.logisticsCompany.LogisticsCompany;
import com.logistics.logisticsCompany.entities.offices.Office;

import java.util.List;

public interface LogisticsCompanyService {

    void addOffice(Office office);

    List<Office> getAllOffices();

    void createLogisticsCompany(LogisticsCompany logisticsCompany);
    List<LogisticsCompany> getAllLogisticsCompanies();
    void updateLogisticsCompany(long companyId, LogisticsCompany updatedCompany);
    void deleteLogisticsCompany(long companyId);
}
