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
/**
 * This class is a controller for handling requests related to DeliveryPaymentType.
 * It uses Spring's @RestController annotation to indicate that it is a controller and the response bodies should be bound to the web response body.
 * It also uses @RequestMapping to map the web requests.
 */
@RestController
@RequestMapping("/api/v1/delivery-payment-types")
public class DeliveryPaymentTypeController {
	/**
	 * The DeliveryPaymentTypeService instance used for delivery payment type-related operations.
	 */
	@Autowired
	private DeliveryPaymentTypeService deliveryPaymentTypeService;
	/**
	 * The DeliveryPaymentTypeRepository instance used for delivery payment type-related operations.
	 */
	@Autowired
	private DeliveryPaymentTypeRepository deliveryPaymentTypeRepository;
	/**
	 * This method handles the POST requests for creating a delivery payment type.
	 * @param deliveryPaymentType the delivery payment type to create
	 * @return a ResponseEntity with the status and a message
	 */
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
	/**
	 * This method handles the DELETE requests for deleting a delivery payment type.
	 * @param deliveryPaymentTypeId the id of the delivery payment type to delete
	 * @return a ResponseEntity with the status and a message
	 */
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
	/**
	 * This method handles the GET requests for getting all delivery payment types.
	 * @return a list of DeliveryPaymentTypeDTO
	 */
	@GetMapping
	public List<DeliveryPaymentTypeDTO> getAllCustomers() {
		List<DeliveryPaymentType> deliveryPaymentTypes = deliveryPaymentTypeService.getAllDeliveryPaymentType();
		return DeliveryPaymentTypeDTO.toDTOList(deliveryPaymentTypes);
	}
}
