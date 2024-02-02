package com.logistics.logisticsCompany.DTO;


import com.logistics.logisticsCompany.entities.enums.DeliveryPaymentType;
import com.logistics.logisticsCompany.entities.enums.ShipmentStatus;
import com.logistics.logisticsCompany.entities.logisticsCompany.LogisticsCompany;
import com.logistics.logisticsCompany.entities.offices.Office;
import com.logistics.logisticsCompany.entities.orders.Shipment;
import com.logistics.logisticsCompany.entities.users.Customer;
import com.logistics.logisticsCompany.entities.users.Employee;
import com.logistics.logisticsCompany.entities.users.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Service
public class EntityDtoMapper {
	
	public ShipmentDTO convertToShipmentDTO(Shipment shipment) {
		ShipmentDTO dto = new ShipmentDTO();
		
		dto.setWeight(shipment.getWeight());
		dto.setPrice(shipment.getPrice());
		dto.setIsPaid(shipment.getIsPaid());
		dto.setPriceDelivery(shipment.getPriceDelivery());
		dto.setIsPaidDelivery(shipment.getIsPaidDelivery());
		dto.setTotalPrice(shipment.getTotalPrice());
		if (shipment.getSenderCustomer() != null) {
			dto.setSenderCustomerPhoneNumber(shipment.getSenderCustomer().getPhone());
		}
		if (shipment.getReceiverCustomer() != null) {
			dto.setReceiverCustomerPhoneNumber(shipment.getReceiverCustomer().getPhone());
		}
		
		if (shipment.getSenderEmployee() != null) {
			dto.setSenderEmployeeId(shipment.getSenderEmployee().getId());
		}
		if (shipment.getReceiverEmployee() != null) {
			dto.setReceiverEmployeeId(shipment.getReceiverEmployee().getId());
		}
		if (shipment.getDeliveryPaymentType() != null) {
			dto.setDeliveryPaymentTypeId(shipment.getDeliveryPaymentType().getId());
		}
		
		if (shipment.getReceiverOffice() != null) {
			dto.setReceiverOfficeId(shipment.getReceiverOffice().getId());
		}
		
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
	
	public UserDTO convertToUserDTO(User user){
		UserDTO dto = new UserDTO();
		
		dto.setId(user.getId());
		dto.setUsername(user.getUsername());
		
		
		
		return dto;
	}
	public LogisticsCompanyDTO convertToLogisticsCompanyDTO(LogisticsCompany logisticsCompany) {
		LogisticsCompanyDTO dto = new LogisticsCompanyDTO();
		dto.setId(logisticsCompany.getId());
		dto.setName(logisticsCompany.getName());
		dto.setIncome(logisticsCompany.getIncome());
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
	
	public DeliveryPaymentTypeDTO convertToDeliveryPaymentTypeDTO(DeliveryPaymentType deliveryPaymentType) {
		DeliveryPaymentTypeDTO dto = new DeliveryPaymentTypeDTO();
		dto.setId(deliveryPaymentType.getId());
		dto.setPaymentType(deliveryPaymentType.getPaymentType());
		return dto;
	}
	
	public ShipmentStatusDTO convertToShipmentStatusDTO(ShipmentStatus shipmentStatus) {
		ShipmentStatusDTO dto = new ShipmentStatusDTO();
		dto.setId(shipmentStatus.getId());
		dto.setShipmentStatus(shipmentStatus.getShipmentStatus());
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
