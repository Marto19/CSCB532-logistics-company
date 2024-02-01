package com.logistics.logisticsCompany.service;

import com.logistics.logisticsCompany.entities.logisticsCompany.LogisticsCompany;
import com.logistics.logisticsCompany.entities.offices.Office;
import com.logistics.logisticsCompany.repository.LogisticsCompanyRepository;
import com.logistics.logisticsCompany.repository.OfficeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LogisticsCompanyServiceImpl implements LogisticsCompanyService {

    private final OfficeRepository officeRepository;
    private final LogisticsCompanyRepository logisticsCompanyRepository;

    @Autowired
    public LogisticsCompanyServiceImpl(OfficeRepository officeRepository, LogisticsCompanyRepository logisticsCompanyRepository) {
        this.officeRepository = officeRepository;
        this.logisticsCompanyRepository = logisticsCompanyRepository;
    }

    @Override
    public void addOffice(Office office) {
        officeRepository.save(office);
    }

    @Override
    public List<Office> getAllOffices() {
        return officeRepository.findAll();
    }


    @Override
    public void createLogisticsCompany(LogisticsCompany logisticsCompany) {
        logisticsCompanyRepository.save(logisticsCompany);
    }

    @Override
    public List<LogisticsCompany> getAllLogisticsCompanies() {
        return logisticsCompanyRepository.findAll();
    }
    public Optional<LogisticsCompany> getLogisticsCompanyById(long id) {
        return logisticsCompanyRepository.findById(id);
    }


    @Override
    @Transactional
    public void updateLogisticsCompany(long companyId, LogisticsCompany updatedCompany) {
        Optional<LogisticsCompany> optionalCompany = logisticsCompanyRepository.findById(companyId);
        if (optionalCompany.isPresent()) {
            LogisticsCompany existingCompany = optionalCompany.get();
            // Update the attributes of the existing company with the attributes of updatedCompany
            existingCompany.setName(updatedCompany.getName());
            existingCompany.setIncome(updatedCompany.getIncome());
            // Set other attributes as needed

            logisticsCompanyRepository.save(existingCompany);
        } else {
            // Handle case where the company with the given ID is not found
            throw new IllegalArgumentException("LogisticsCompany with ID " + companyId + " not found");
        }
    }

    @Override
    public void deleteLogisticsCompany(long companyId) {
        logisticsCompanyRepository.deleteById(companyId);
    }
}
