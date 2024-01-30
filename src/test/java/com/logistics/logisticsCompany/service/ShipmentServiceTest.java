package com.logistics.logisticsCompany.service;

import com.logistics.logisticsCompany.DTO.ShipmentDTO;
import com.logistics.logisticsCompany.entities.enums.DeliveryPaymentType;
import com.logistics.logisticsCompany.entities.orders.Shipment;
import com.logistics.logisticsCompany.entities.users.Customer;
import com.logistics.logisticsCompany.entities.users.Employee;
import com.logistics.logisticsCompany.repository.DeliveryPaymentTypeRepository;
import com.logistics.logisticsCompany.repository.ShipmentRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.logistics.logisticsCompany.service.CustomerService;
import com.logistics.logisticsCompany.service.EmployeeService;
import com.logistics.logisticsCompany.service.ShipmentService;
import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ShipmentServiceTest {
	
	@Autowired
	private ShipmentService shipmentService;
	
	@MockBean
	private ShipmentRepository shipmentRepository;
	
	@MockBean
	private CustomerService customerService;
	
	@MockBean
	private EmployeeService employeeService;
	
	@MockBean
	private DeliveryPaymentTypeRepository deliveryPaymentTypeRepository;
	
	@Test
	public void testCreateShipment() {
		// Arrange
		ShipmentDTO shipmentDTO = new ShipmentDTO(
				new BigDecimal("10.00"), // weight
				"1234567890", // sender phone
				"0987654321", // receiver phone
				1L, // employee ID
				1L // delivery payment type ID
		);
		
		// Mock the necessary service/repository calls
		Mockito.when(customerService.getCustomerByPhoneNumber("1234567890"))
				.thenReturn(new Customer()); // Mock customer
		Mockito.when(employeeService.getEmployeeById(1L))
				.thenReturn(Optional.of(new Employee())); // Mock employee
		Mockito.when(deliveryPaymentTypeRepository.findById(1L))
				.thenReturn(Optional.of(new DeliveryPaymentType())); // Mock delivery type
		
		// Act
		Shipment createdShipment = shipmentService.createShipment(shipmentDTO);
		
		// Assert
		assertNotNull(createdShipment);
		assertEquals(new BigDecimal("10.00"), createdShipment.getWeight());
		// Other assertions based on your business logic
	}
}
