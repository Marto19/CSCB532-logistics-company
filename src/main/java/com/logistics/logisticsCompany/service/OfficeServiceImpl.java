package com.logistics.logisticsCompany.service;

import com.logistics.logisticsCompany.customExceptions.EntityNotFoundException;
import com.logistics.logisticsCompany.entities.offices.Office;
import com.logistics.logisticsCompany.repository.OfficeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OfficeServiceImpl implements OfficeService {

    @Autowired
    private OfficeRepository officeRepository;

    @Override
    public void createOffice(Office office) {
        //Checks if office with the given address already exists
        if (officeRepository.existsByAddress(office.getAddress())) {
            throw new IllegalArgumentException("Office with the provided address already exists");
        }

        officeRepository.save(office);
    }

    @Override
    public List<Office> getAllOffices() {
        return officeRepository.findAll();
    }

    @Override
    public void updateOffice(long officeId, Office updatedOffice) {
        if (!officeRepository.existsById(officeId)) {
            throw new EntityNotFoundException("Office with the provided id doesn't exist");
        }
            updatedOffice.setId(officeId);
            officeRepository.save(updatedOffice);
    }

    @Override
    public void deleteOffice(long officeId) {
        if (!officeRepository.existsById(officeId)) {
            throw new EntityNotFoundException("Office with the provided id doesn't exist");
        }
            officeRepository.deleteById(officeId);
    }
    
    @Override
    public Optional<Office> getOfficeById(Long id) {
        return officeRepository.findById(id);
    }
}
