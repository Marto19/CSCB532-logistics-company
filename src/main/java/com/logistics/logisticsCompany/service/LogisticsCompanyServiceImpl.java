package com.logistics.logisticsCompany.service;

import com.logistics.logisticsCompany.DTO.EntityDtoMapper;
import com.logistics.logisticsCompany.DTO.LogisticsCompanyDTO;
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

/**
 * Service class for managing logistics companies and offices.
 */
@Service
public class LogisticsCompanyServiceImpl implements LogisticsCompanyService {

    private final OfficeRepository officeRepository;
    private final LogisticsCompanyRepository logisticsCompanyRepository;

    /**
     * Constructor for LogisticsCompanyServiceImpl.
     *
     * @param officeRepository Repository for managing offices.
     * @param logisticsCompanyRepository Repository for managing logistics companies.
     */
    @Autowired
    public LogisticsCompanyServiceImpl(OfficeRepository officeRepository, LogisticsCompanyRepository logisticsCompanyRepository) {
        this.officeRepository = officeRepository;
        this.logisticsCompanyRepository = logisticsCompanyRepository;
    }
    /**
     * Adds an office to the repository.
     *
     * @param office The office to be added.
     */
    @Override
    public void addOffice(Office office) {
        officeRepository.save(office);
    }

    /**
     * Retrieves all offices from the repository.
     *
     * @return A list of all offices.
     */
    @Override
    public List<Office> getAllOffices() {
        return officeRepository.findAll();
    }

    /**
     * Creates a logistics company and adds it to the repository.
     * Throws an IllegalArgumentException if a company with the same name already exists.
     *
     * @param logisticsCompany The logistics company to be created.
     */
    @Override
    public void createLogisticsCompany(LogisticsCompanyDTO logisticsCompanyDTO) {
        //Check if a logistics company with the given phone already exists
        if (logisticsCompanyRepository.existsByName(logisticsCompanyDTO.getName())) {
            throw new IllegalArgumentException("Logistics company with the same name already exists");
        }

        LogisticsCompany logisticsCompany = EntityDtoMapper.convertLogisticsCompanyDtoToEntity(logisticsCompanyDTO);

        logisticsCompanyRepository.save(logisticsCompany);
    }

    /**
     * Retrieves all logistics companies from the repository.
     *
     * @return A list of all logistics companies.
     */
    @Override
    public List<LogisticsCompany> getAllLogisticsCompanies() {
        return logisticsCompanyRepository.findAll();
    }
    public Optional<LogisticsCompany> getLogisticsCompanyById(long id) {
        return logisticsCompanyRepository.findById(id);
    }

    /**
     * Updates a logistics company in the repository.
     * Throws an EntityNotFoundException if the company does not exist.
     *
     * @param companyId The ID of the company to be updated.
     * @param updatedCompany The updated logistics company.
     */
    @Override
    @Transactional
    public void updateLogisticsCompany(long companyId, LogisticsCompany updatedCompany) {
        if(!logisticsCompanyRepository.existsById(companyId)){
            throw new EntityNotFoundException("Logistics company with the provided id doesn't exist");
        }
        updatedCompany.setId(companyId);
        logisticsCompanyRepository.save(updatedCompany);
    }

    /**
     * Deletes a logistics company from the repository.
     * Throws an EntityNotFoundException if the company does not exist.
     *
     * @param companyId The ID of the company to be deleted.
     */
    @Override
    public void deleteLogisticsCompany(long companyId) {
        if (!logisticsCompanyRepository.existsById(companyId)){
            throw new EntityNotFoundException("Logistics company with the provided id doesn't exist");
        }
        logisticsCompanyRepository.deleteById(companyId);
    }
}
