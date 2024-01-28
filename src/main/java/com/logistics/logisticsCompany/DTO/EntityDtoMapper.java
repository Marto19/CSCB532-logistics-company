package com.logistics.logisticsCompany.DTO;


import com.logistics.logisticsCompany.entities.offices.Office;
import com.logistics.logisticsCompany.entities.orders.Shipment;
import com.logistics.logisticsCompany.entities.users.Customer;
import com.logistics.logisticsCompany.entities.users.Employee;
import org.springframework.stereotype.Service;

@Service
public class EntityDtoMapper {
	
	public ShipmentDTO convertToShipmentDTO(Shipment shipment) {
		ShipmentDTO dto = new ShipmentDTO();
		dto.setId(shipment.getId());
		dto.setShipmentDate(shipment.getShipmentDate());
		dto.setWeight(shipment.getWeight());
		dto.setPrice(shipment.getPrice());
		dto.setIsPaid(shipment.isPaid());
		dto.setReceivedDate(shipment.getReceivedDate());
		dto.setSenderOffice(convertToOfficeDTO(shipment.getSenderOffice()));
		dto.setSenderCustomer(convertToCustomerDTO(shipment.getSenderCustomer()));
		dto.setSenderEmployee(convertToEmployeeDTO(shipment.getSenderEmployee()));
		dto.setReceiverOffice(convertToOfficeDTO(shipment.getReceiverOffice()));
		dto.setReceiverCustomer(convertToCustomerDTO(shipment.getReceiverCustomer()));
		dto.setReceiverEmployee(convertToEmployeeDTO(shipment.getReceiverEmployee()));
		// Add other fields if necessary
		return dto;
	}
	
	public OfficeDTO convertToOfficeDTO(Office office) {
		OfficeDTO dto = new OfficeDTO();
		dto.setId(office.getId());
		dto.setOfficeName(office.getOfficeName());
		dto.setCity(office.getCity());
		dto.setPostcode(office.getPostcode());
		dto.setAddress(office.getAddress());
		return dto;
	}
	
	public CustomerDTO convertToCustomerDTO(Customer customer) {
		CustomerDTO dto = new CustomerDTO();
		dto.setId(customer.getId());
		dto.setFirstName(customer.getFirstName());
		dto.setSecondName(customer.getSecondName());
		dto.setPhone(customer.getPhone());
		dto.setBalance(customer.getBalance());
		return dto;
	}
	
	public EmployeeDTO convertToEmployeeDTO(Employee employee) {
		if (employee == null) {
			return null;
		}
		EmployeeDTO dto = new EmployeeDTO();
		dto.setId(employee.getId());
		dto.setFirstName(employee.getFirstName());
		dto.setSecondName(employee.getSecondName());
		return dto;
	}
	
	//**********************************************************************************************************************
	//Marto's dtos
	/*private OfficeDTO convertToOfficeDTO(Office office) {
		return new OfficeDTO(
				office.getId(),
				office.getOfficeName(),
				office.getCity(),
				office.getPostcode(),
				office.getAddress()
		);
	}
	
	private CustomerDTO convertToCustomerDTO(Customer customer) {
		return new CustomerDTO(
				customer.getId(),
				customer.getFirstName(),
				customer.getSecondName(),
				customer.getPhone()
		);
	}
	
	private EmployeeDTO convertToEmployeeDTO(Employee employee) {
		
		if (employee == null) {
			return null; // or you can create a default EmployeeDTO or throw an exception
		}
		return new EmployeeDTO(
				employee.getId(),
				employee.getFirstName(),
				employee.getSecondName()
		);
	}*/
}
