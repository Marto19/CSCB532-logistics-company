package com.logistics.logisticsCompany.service;

import com.logistics.logisticsCompany.entities.offices.Office;
import com.logistics.logisticsCompany.repository.OfficeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogisticsCompanyServiceImpl implements LogisticsCompanyService {

    private final OfficeRepository officeRepository;

    public LogisticsCompanyServiceImpl(OfficeRepository officeRepository) {
        this.officeRepository = officeRepository;
    }

    @Override
    public void addOffice(Office office) {
        officeRepository.save(office);
    }

    @Override
    public List<Office> getAllOffices() {
        return officeRepository.findAll();
    }

    // Implement similar methods for other entities
}
