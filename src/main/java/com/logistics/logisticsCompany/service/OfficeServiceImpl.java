package com.logistics.logisticsCompany.service;

import com.logistics.logisticsCompany.entities.offices.Office;
import com.logistics.logisticsCompany.repository.OfficeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfficeServiceImpl {

    @Autowired
    private OfficeRepository officeRepository;

    public void createOffice(Office office) {
        officeRepository.save(office);
    }

    public List<Office> getAllOffices() {
        return officeRepository.findAll();
    }

    public void updateOffice(long officeId, Office updatedOffice) {
        // Check if the office with the given ID exists
        if (officeRepository.existsById((int) officeId)) {
            // Set the ID for the updated office
            updatedOffice.setId(officeId);
            officeRepository.save(updatedOffice);
        } else {
            // Handle case where the office with the given ID does not exist
            // You may throw an exception or handle it according to your requirements
            //TODO: ADD CUSTOM EXCEPTION
        }
    }

    public void deleteOffice(long officeId) {
        // Check if the office with the given ID exists
        if (officeRepository.existsById((int) officeId)) {
            officeRepository.deleteById((int) officeId);
        } else {
            // Handle case where the office with the given ID does not exist
            // You may throw an exception or handle it according to your requirements
            //TODO: ADD CUSTOM EXCEPTION

        }
    }
}
