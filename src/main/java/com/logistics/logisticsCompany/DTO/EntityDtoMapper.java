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

/**
 * The EntityDtoMapper class is used to convert entities to DTOs and vice versa.
 * It contains methods for converting shipments, customers, offices, users, logistics companies, employees, delivery payment types, and shipment statuses.
 * @see ShipmentDTO
 * @see CustomerDTO
 * @see OfficeDTO
 * @see UserDTO
 * @see LogisticsCompanyDTO
 * @see EmployeeDTO
 * @see DeliveryPaymentTypeDTO
 * @see ShipmentStatusDTO
 */
@Service
public class EntityDtoMapper {

	/**
	 * Converts the specified shipment to a shipment data transfer object.
	 * @param shipment the shipment
	 * @return the shipment data transfer object
	 */
	public ShipmentDTO convertToShipmentDTO(Shipment shipment) {
		ShipmentDTO dto = new ShipmentDTO();
		
		dto.setShipmentDate(shipment.getShipmentDate());
		dto.setWeight(shipment.getWeight());
		dto.setPrice(shipment.getPrice());
		dto.setIsPaidDelivery(shipment.getIsPaidDelivery());
		if (shipment.getSenderCustomer() != null) {
			dto.setSenderCustomerPhoneNumber(shipment.getSenderCustomer().getPhone());
		}
		if (shipment.getReceiverCustomer() != null) {
			dto.setReceiverCustomerPhoneNumber(shipment.getReceiverCustomer().getPhone());
		}
		
		if (shipment.getSenderEmployee() != null) {
			dto.setSenderEmployeeId(shipment.getSenderEmployee().getId());
		}
		
		if (shipment.getReceiverOffice() != null) {
			dto.setReceiverOfficeId(shipment.getReceiverOffice().getId());
		}
		
		return dto;
	}

	/**
	 * Converts the specified shipment data transfer object to a shipment.
	 * @param shipmentDTO the shipment data transfer object
	 * @return the shipment
	 */
	public Shipment convertShipmentDtoToEntity(ShipmentDTO shipmentDTO) {
		Shipment shipment = new Shipment();
		shipment.setWeight(shipmentDTO.getWeight());
		shipment.setPrice(shipmentDTO.getPrice());
		shipment.setIsPaidDelivery(shipmentDTO.getIsPaidDelivery());
		return shipment;
	}

	/**
	 * Converts the specified customer to a customer data transfer object.
	 * @param customer the customer
	 * @return the customer data transfer object
	 */
	public CustomerDTO convertToCustomerDTO(Customer customer) {
		CustomerDTO dto = new CustomerDTO();
		dto.setId(customer.getId());
		dto.setFirstName(customer.getFirstName());
		dto.setSecondName(customer.getSecondName());
		dto.setPhone(customer.getPhone());
		dto.setBalance(customer.getBalance());
		return dto;
	}

	/**
	 * Converts the specified customer data transfer object to a customer.
	 * @param customerDTO the customer data transfer object
	 * @return the customer
	 */
	public Customer convertCustomerDtoToEntity(CustomerDTO customerDTO) {
		Customer customer = new Customer();
		customer.setId(customerDTO.getId());
		customer.setFirstName(customerDTO.getFirstName());
		customer.setSecondName(customerDTO.getSecondName());
		customer.setPhone(customerDTO.getPhone());
/*		if (customerDTO.getUserId() != null){
			customer.setUsers(new User(customerDTO.getUserId()));
		}*/
		
		return customer;
	}

	/**
	 * Converts the specified office to an office data transfer object.
	 * @param office the office
	 * @return the office data transfer object
	 */
	public OfficeDTO convertToOfficeDTO(Office office) {
		OfficeDTO dto = new OfficeDTO();
		dto.setId(office.getId());
		dto.setOfficeName(office.getOfficeName());
		dto.setCity(office.getCity());
		dto.setPostcode(office.getPostcode());
		dto.setAddress(office.getAddress());
		return dto;
	}

	public Office convertOfficeDtoToEntity(OfficeDTO officeDTO) {
		Office office = new Office();
		office.setOfficeName(officeDTO.getOfficeName());
		office.setCity(officeDTO.getCity());
		office.setPostcode(officeDTO.getPostcode());
		office.setAddress(officeDTO.getAddress());
		return office;
	}
	/**
	 * Converts the specified user to a user data transfer object.
	 * @param user the user
	 * @return the user data transfer object
	 */
	public UserDTO convertToUserDTO(User user) {
		UserDTO dto = new UserDTO();
		
		dto.setId(user.getId());
		dto.setUsername(user.getUsername());
		
		
		return dto;
	}

	/**
	 * Converts the specified logistics company to a logistics company data transfer object.
	 * @param logisticsCompany the logistics company
	 * @return the logistics company data transfer object
	 */
	public LogisticsCompanyDTO convertToLogisticsCompanyDTO(LogisticsCompany logisticsCompany) {
		LogisticsCompanyDTO dto = new LogisticsCompanyDTO();
		dto.setId(logisticsCompany.getId());
		dto.setName(logisticsCompany.getName());
		dto.setIncome(logisticsCompany.getIncome());
		return dto;
	}
	

	/**
	 * Converts the specified logistics company data transfer object to a logistics company.
	 * @param logisticsCompanyDTO the logistics company data transfer object
	 * @return the logistics company
	 */
	//name, income -> LogisticsCompany
	public static LogisticsCompany convertLogisticsCompanyDtoToEntity(LogisticsCompanyDTO logisticsCompanyDTO) {
		LogisticsCompany logisticsCompany = new LogisticsCompany();
		logisticsCompany.setName(logisticsCompanyDTO.getName());
		logisticsCompany.setIncome(logisticsCompanyDTO.getIncome());
		return logisticsCompany;
	}
	
	


		public static Employee convertEmployeeDtoToEntity(EmployeeDTO employeeDTO) {
		Employee employee = new Employee();
		employee.setFirstName(employeeDTO.getFirstName());
		employee.setSecondName(employeeDTO.getSecondName());

		return employee;
	}
	
	/**
	 * Converts the specified employee to an employee data transfer object.
	 * @param employee the employee
	 * @return the employee data transfer object
	 */

	public EmployeeDTO convertToEmployeeDTO(Employee employee) {
		if (employee == null) {
			return null;
		}
		EmployeeDTO dto = new EmployeeDTO();
		dto.setId(employee.getId());
		dto.setFirstName(employee.getFirstName());
		dto.setSecondName(employee.getSecondName());
		
		// Check if the current office is not null before accessing its name
		if (employee.getCurrentOffice() != null) {
			dto.setCurrentOfficeName(employee.getCurrentOffice().getOfficeName());
		} else {
			dto.setCurrentOfficeName(null); // Or set a default value or leave it unset
		}
		
		// Check if the logistics company is not null before accessing its details
		if (employee.getLogisticsCompany() != null) {
			dto.setCompanyName(employee.getLogisticsCompany().getName()); // Assuming you have a getCompanyName() method
		} else {
			dto.setCompanyName(null); // Or set a default value or leave it unset
		}
		
		// Assuming you want to set the username of the associated user
		if (employee.getUsers() != null) {
			dto.setUsername(employee.getUsers().getUsername()); // Assuming the Users entity has a getUsername() method
		} else {
			dto.setUsername(null); // Or set a default value or leave it unset
		}
		
		return dto;
	}

	/**
	 * Converts the specified delivery payment type to a delivery payment type data transfer object.
	 * @param deliveryPaymentType the delivery payment type
	 * @return the delivery payment type data transfer object
	 */
	public DeliveryPaymentTypeDTO convertToDeliveryPaymentTypeDTO(DeliveryPaymentType deliveryPaymentType) {
		DeliveryPaymentTypeDTO dto = new DeliveryPaymentTypeDTO();
		dto.setId(deliveryPaymentType.getId());
		dto.setPaymentType(deliveryPaymentType.getPaymentType());
		return dto;
	}

	/**
	 * Converts the specified shipment status to a shipment status data transfer object.
	 * @param shipmentStatus the shipment status
	 * @return the shipment status data transfer object
	 */
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
