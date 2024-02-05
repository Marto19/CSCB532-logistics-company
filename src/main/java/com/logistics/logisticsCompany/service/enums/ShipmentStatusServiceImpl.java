package com.logistics.logisticsCompany.service.enums;

import com.logistics.logisticsCompany.entities.enums.ShipmentStatus;
import com.logistics.logisticsCompany.repository.ShipmentStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShipmentStatusServiceImpl implements ShipmentStatusService{
	@Autowired
	private ShipmentStatusRepository shipmentStatusRepository;
	
	
	@Override
	public void createShipmentStatus(ShipmentStatus shipmentStatus) {
		if (shipmentStatus.getShipmentStatus() == null || shipmentStatus.getShipmentStatus().isEmpty()) {
			throw new IllegalArgumentException("Type name must not be null or empty");
		}
		shipmentStatusRepository.save(shipmentStatus);
	}
	@Override
	public void deleteShipmentStatus(long shipmentStatusId) {
		shipmentStatusRepository.deleteById(shipmentStatusId);
	}
	
	@Override
	public List<ShipmentStatus> getAllShipmentStatuses() {
		return shipmentStatusRepository.findAll();
	}
	
	@Override
	public boolean existsByShipmentStatus(String shipmentStatus) {
		return shipmentStatusRepository.existsByShipmentStatus(shipmentStatus);
	}

	@Override
	public boolean existsByShipmentStatusOrId(String shipmentStatus, long shipmentStatusId) {
		return shipmentStatusRepository.existsByShipmentStatusOrId(shipmentStatus, shipmentStatusId);
	}
	//    @Override
	//    public boolean existsByPhone(String phone) {
	//        return customerRepository.existsByPhone(phone);
	//    }

}