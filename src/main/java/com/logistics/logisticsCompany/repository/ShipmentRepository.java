package com.logistics.logisticsCompany.repository;

import com.logistics.logisticsCompany.entities.orders.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
    // Add custom queries if needed

    boolean existsById(long id);
}