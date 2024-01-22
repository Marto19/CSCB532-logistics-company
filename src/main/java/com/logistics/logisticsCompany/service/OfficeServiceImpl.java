package com.logistics.logisticsCompany.service;

import com.logistics.logisticsCompany.entities.offices.Office;
import com.logistics.logisticsCompany.repository.OfficeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfficeServiceImpl implements OfficeService {

    @Autowired
    private OfficeRepository officeRepository;

    @Override
    public void createOffice(Office office) {
        officeRepository.save(office);
    }

    @Override
    public List<Office> getAllOffices() {
        return officeRepository.findAll();
    }

    @Override
    public void updateOffice(long officeId, Office updatedOffice) {
        // Implementation of updateOffice method
        if (officeRepository.existsById((int) officeId)) {
            updatedOffice.setId(officeId);
            officeRepository.save(updatedOffice);
        } else {
            // Handle case where the office with the given ID does not exist
            // TODO: ADD CUSTOM EXCEPTION
        }
    }

    @Override
    public void deleteOffice(long officeId) {
        // Implementation of deleteOffice method
        if (officeRepository.existsById((int) officeId)) {
            officeRepository.deleteById((int) officeId);
        } else {
            // Handle case where the office with the given ID does not exist
            // TODO: ADD CUSTOM EXCEPTION
        }
    }
}
