package com.logistics.logisticsCompany.controller.enums;

import com.logistics.logisticsCompany.DTO.CustomerDTO;
import com.logistics.logisticsCompany.DTO.DeliveryPaymentTypeDTO;
import com.logistics.logisticsCompany.entities.enums.DeliveryPaymentType;
import com.logistics.logisticsCompany.entities.users.Customer;
import com.logistics.logisticsCompany.service.enums.DeliveryPaymentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.logistics.logisticsCompany.repository.DeliveryPaymentTypeRepository;

@RestController
@RequestMapping("/api/v1/delivery-payment-types")
public class DeliveryPaymentTypeController {
	@Autowired
	private DeliveryPaymentTypeService deliveryPaymentTypeService;
	
	@Autowired
	private DeliveryPaymentTypeRepository deliveryPaymentTypeRepository;
	
	@PostMapping
	public ResponseEntity<String> createDeliveryPaymentType(@RequestBody DeliveryPaymentType deliveryPaymentType) {
		
		if (deliveryPaymentTypeService.existsByDeliveryPaymentTypeOrId(deliveryPaymentType.getPaymentType(), deliveryPaymentType.getId())) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Customer with the provided name already exists");
		}
		deliveryPaymentTypeService.createDeliveryPaymentType(deliveryPaymentType);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body("Delivery payment type created successfully");
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteDeliveryPaymentType(@PathVariable(value = "id") long deliveryPaymentTypeId) {
		if (!deliveryPaymentTypeRepository.existsById(deliveryPaymentTypeId)){
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Delivery payment type with the provided id doesn't exist");
		}
		deliveryPaymentTypeService.deleteDeliveryPaymentType(deliveryPaymentTypeId);
		return ResponseEntity.status(HttpStatus.OK)
				.body("Delivery payment type deleted successfully");
	}
	
	@GetMapping
	public List<DeliveryPaymentTypeDTO> getAllCustomers() {
		List<DeliveryPaymentType> deliveryPaymentTypes = deliveryPaymentTypeService.getAllDeliveryPaymentType();
		return DeliveryPaymentTypeDTO.toDTOList(deliveryPaymentTypes);
	}
}
