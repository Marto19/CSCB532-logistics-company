package com.logistics.logisticsCompany.service;

import com.logistics.logisticsCompany.DTO.EntityDtoMapper;
import com.logistics.logisticsCompany.DTO.OfficeDTO;
import com.logistics.logisticsCompany.customExceptions.EntityNotFoundException;
import com.logistics.logisticsCompany.entities.offices.Office;
import com.logistics.logisticsCompany.repository.LogisticsCompanyRepository;
import com.logistics.logisticsCompany.repository.OfficeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * The {@code OfficeServiceImpl} class implements the {@code OfficeService} interface.
 * It provides the business logic for managing offices.
 */
@Service
public class OfficeServiceImpl implements OfficeService {

    /**
     * The {@code OfficeRepository} instance used for office-related database operations.
     */

    private final OfficeRepository officeRepository;
    private final LogisticsCompanyRepository logisticsCompanyRepository;
    private final EntityDtoMapper entityDtoMapper;
    
    @Autowired
    public OfficeServiceImpl(OfficeRepository officeRepository, EntityDtoMapper entityDtoMapper, LogisticsCompanyRepository logisticsCompanyRepository){
        this.officeRepository = officeRepository;
        this.entityDtoMapper = entityDtoMapper;
        this.logisticsCompanyRepository = logisticsCompanyRepository;
    }
    
    @Transactional
    @Override
    public void createOffice(OfficeDTO officeDTO) {
        
        if (officeRepository.existsByAddress(officeDTO.getAddress())) {
            throw new IllegalArgumentException("Office with the provided address already exists.");
        }
        
        Office office = entityDtoMapper.convertOfficeDtoToEntity(officeDTO);
        long companyId = Long.parseLong(officeDTO.getCompanyId());
        office.setLogisticsCompany(logisticsCompanyRepository.getLogisticsCompanyById(companyId)
                .orElseThrow(() -> new EntityNotFoundException("Logistics company not found for user ID: " + officeDTO.getCompanyId())));
        
        officeRepository.save(office);
    }

    /**
     * Retrieves all offices.
     * @return a list of all offices
     */
    @Override
    public List<Office> getAllOffices() {
        return officeRepository.findAll();
    }

    /**
     * Updates an existing office.
     * @param officeId the id of the office to update
     * @param updatedOffice the updated office
     */
    @Override
    public void updateOffice(long officeId, Office updatedOffice) {
        if (!officeRepository.existsById(officeId)) {
            throw new EntityNotFoundException("Office with the provided id doesn't exist");
        }
            updatedOffice.setId(officeId);
            officeRepository.save(updatedOffice);
    }

    /**
     * Deletes an existing office.
     * @param officeId the id of the office to delete
     */
    @Override
    public void deleteOffice(long officeId) {
        if (!officeRepository.existsById(officeId)) {
            throw new EntityNotFoundException("Office with the provided id doesn't exist");
        }
            officeRepository.deleteById(officeId);
    }

    /**
     * Retrieves an office by id.
     * @param id the id of the office
     * @return an Optional containing the office if it exists, or an empty Optional otherwise
     */
    @Override
    public Optional<Office> getOfficeById(Long id) {
        return officeRepository.findById(id);
    }
    
    @Override
    public Office getOfficeByEmployeeId(Long employeeId) {
        return officeRepository.findByEmployeesId(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found for user ID: " + employeeId));
    }
}
