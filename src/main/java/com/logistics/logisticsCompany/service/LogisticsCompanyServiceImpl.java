package com.logistics.logisticsCompany.service;

import com.logistics.logisticsCompany.customExceptions.EntityNotFoundException;
import com.logistics.logisticsCompany.entities.logisticsCompany.LogisticsCompany;
import com.logistics.logisticsCompany.entities.offices.Office;
import com.logistics.logisticsCompany.repository.LogisticsCompanyRepository;
import com.logistics.logisticsCompany.repository.OfficeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        //Check if a logistics company with the given phone already exists
        if (logisticsCompanyRepository.existsByName(logisticsCompany.getName())) {
            throw new IllegalArgumentException("Logistics company with the same name already exists");
        }

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
        if(!logisticsCompanyRepository.existsById(companyId)){
            throw new EntityNotFoundException("Logistics company with the provided id doesn't exist");
        }
        updatedCompany.setId(companyId);
        logisticsCompanyRepository.save(updatedCompany);
    }

    @Override
    public void deleteLogisticsCompany(long companyId) {
        if (!logisticsCompanyRepository.existsById(companyId)){
            throw new EntityNotFoundException("Logistics company with the provided id doesn't exist");
        }
        logisticsCompanyRepository.deleteById(companyId);
    }
}
