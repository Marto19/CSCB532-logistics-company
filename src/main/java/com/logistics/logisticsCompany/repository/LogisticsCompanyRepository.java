package com.logistics.logisticsCompany.repository;

import com.logistics.logisticsCompany.entities.logisticsCompany.LogisticsCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogisticsCompanyRepository extends JpaRepository<LogisticsCompany, Long> {
    boolean existsByName(String name);
    boolean existsById(long id);
}
