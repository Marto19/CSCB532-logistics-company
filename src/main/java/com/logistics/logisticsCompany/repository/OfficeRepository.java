package com.logistics.logisticsCompany.repository;

import com.logistics.logisticsCompany.entities.logisticsCompany.LogisticsCompany;
import com.logistics.logisticsCompany.entities.offices.Office;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OfficeRepository extends JpaRepository<Office, Integer> {
    boolean existsByOfficeName(String officeName);
    boolean existsByAddress(String address);
    boolean existsById(long id);
    Optional<Office> getOfficeById(long id);



}
