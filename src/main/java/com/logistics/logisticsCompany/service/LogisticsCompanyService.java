package com.logistics.logisticsCompany.service;

import com.logistics.logisticsCompany.entities.offices.Office;

import java.util.List;

public interface LogisticsCompanyService {

    void addOffice(Office office);

    List<Office> getAllOffices();

    // Add similar methods for other entities
}