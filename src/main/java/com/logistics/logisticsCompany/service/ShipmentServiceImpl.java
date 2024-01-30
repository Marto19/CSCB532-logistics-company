
package com.logistics.logisticsCompany.service;

import com.logistics.logisticsCompany.DTO.*;
import com.logistics.logisticsCompany.customExceptions.EntityNotFoundException;
import com.logistics.logisticsCompany.entities.enums.DeliveryPaymentType;
import com.logistics.logisticsCompany.entities.enums.ShipmentStatus;
import com.logistics.logisticsCompany.entities.orders.Shipment;
import com.logistics.logisticsCompany.entities.orders.ShipmentStatusHistory;
import com.logistics.logisticsCompany.entities.users.Customer;
import com.logistics.logisticsCompany.entities.users.Employee;
import com.logistics.logisticsCompany.repository.ShipmentRepository;
import com.logistics.logisticsCompany.repository.ShipmentStatusHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import com.logistics.logisticsCompany.DTO.ShipmentDTO;
import org.springframework.transaction.annotation.Transactional;
import com.logistics.logisticsCompany.repository.DeliveryPaymentTypeRepository;
import com.logistics.logisticsCompany.repository.ShipmentStatusRepository;
import com.logistics.logisticsCompany.entities.offices.Office;
@Service
public class ShipmentServiceImpl implements ShipmentService {

    private final ShipmentRepository shipmentRepository;
    private final EntityDtoMapper entityDtoMapper;
    
    /*
    * trying some things
    * */
    
    private final DeliveryPaymentTypeRepository deliveryPaymentTypeRepository;
    
    private final CustomerService customerService;
    private final EmployeeService employeeService;
    private final ShipmentStatusHistoryRepository shipmentStatusHistoryRepository;
    
    private final ShipmentStatusRepository shipmentStatusRepository;
    
    private final OfficeService officeService;
    @Autowired
    public ShipmentServiceImpl(ShipmentRepository shipmentRepository, EntityDtoMapper entityDtoMapper, DeliveryPaymentTypeRepository deliveryPaymentTypeRepository,
                               CustomerService customerService, EmployeeService employeeService, ShipmentStatusHistoryRepository shipmentStatusHistoryRepository,
                               ShipmentStatusRepository shipmentStatusRepository, OfficeService officeService ){
        this.shipmentRepository = shipmentRepository;
        this.entityDtoMapper = entityDtoMapper;
        this.deliveryPaymentTypeRepository = deliveryPaymentTypeRepository;
        this.customerService = customerService;
        this.employeeService = employeeService;
	    this.shipmentStatusHistoryRepository = shipmentStatusHistoryRepository;
        this.shipmentStatusRepository = shipmentStatusRepository;
        this.officeService = officeService;
    }
    
    /*
    *Shipment fields:
    *isPaid
    *isPaidDelivery
    *price
    *priceDelivery
    *ReceivedDate
    *Weight
    *DeliveryPaymentType
    *SenderCustomer
    *SenderEmployee
    *ReceiverCustomer
    *
    * */
    @Override
    public Shipment createShipment(ShipmentDTO shipmentDto) {
        Shipment shipment = new Shipment();
        
        // Auto Set shipment date to the current date
        shipment.setShipmentDate(LocalDate.now());
        
        // Set weight
        shipment.setWeight(shipmentDto.getWeight());
        //set isPaidDelivery
        shipment.setIsPaidDelivery(shipmentDto.getIsPaidDelivery());
        
        // Calculate priceDelivery based on weight, and set price if entered
        BigDecimal priceDelivery = calculatePriceDelivery(shipmentDto.getWeight());
        shipment.setPriceDelivery(priceDelivery);
        
        BigDecimal price = (shipmentDto.getPrice() == null) ? BigDecimal.ZERO : shipmentDto.getPrice();
        shipment.setPrice(price);
        
        shipment.setTotalPrice(price.add(priceDelivery));
        
        // Determine and set DeliveryPaymentType based on price and isPaidDelivery
        // Determine and set DeliveryPaymentType based on the flags
        DeliveryPaymentType deliveryPaymentType = determineDeliveryPaymentType(shipmentDto);
        shipment.setDeliveryPaymentType(deliveryPaymentType);
        
        // Set sender and receiver customer by phone number
        Customer senderCustomer = customerService.getCustomerByPhoneNumber(shipmentDto.getSenderCustomerPhoneNumber());
        Customer receiverCustomer = customerService.getCustomerByPhoneNumber(shipmentDto.getReceiverCustomerPhoneNumber());
        shipment.setSenderCustomer(senderCustomer);
        shipment.setReceiverCustomer(receiverCustomer);
        
        // Set sender employee and office from the logged-in employee's information
        //this is getting the employee by id from the shipmentDto
        Employee senderEmployee = employeeService.getEmployeeById(shipmentDto.getSenderEmployeeId())
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with ID: " + shipmentDto.getSenderEmployeeId()));
        shipment.setSenderEmployee(senderEmployee);
        shipment.setSenderOffice(senderEmployee.getCurrentOffice());
        
        //fixme - set receiver employee and office from the logged-in employee's information - waiting for marto to implement authentication
        //   Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //    String currentUsername = authentication.getName();
        //    Employee senderEmployee = employeeService.getEmployeeByUsername(currentUsername)
        //            .orElseThrow(() -> new EntityNotFoundException("Employee not found for username: " + currentUsername));
        //    shipment.setSenderEmployee(senderEmployee);
        //    shipment.setSenderOffice(senderEmployee.getCurrentOffice());
        
        // Set receiver office
        if (shipmentDto.getReceiverOfficeId() != null) {
            Office receiverOffice = officeService.getOfficeById(shipmentDto.getReceiverOfficeId())
                    .orElseThrow(() -> new EntityNotFoundException("Office not found with ID: " + shipmentDto.getReceiverOfficeId()));
            shipment.setReceiverOffice(receiverOffice);
        } else {
            // Handle the case where receiverOfficeId is null
        }
        
        
        
        shipmentRepository.save(shipment);
        
        // Create initial shipment status history as 'Registered'
        createInitialShipmentStatusHistory(shipment);
        
        return shipment;
    }
    
    private DeliveryPaymentType determineDeliveryPaymentType(ShipmentDTO shipmentDto) {
        if (shipmentDto.getPrice() != null && shipmentDto.getPrice().compareTo(BigDecimal.ZERO) > 0) {
            // If price is provided and greater than 0, it's "Cash-On-Delivery"
            return deliveryPaymentTypeRepository
                    .findByPaymentType("Cash-On-Delivery")
                    .orElseThrow(() -> new EntityNotFoundException("DeliveryPaymentType 'Cash-On-Delivery' not found"));
        } else if (shipmentDto.getIsPaidDelivery()) {
            // If isPaidDelivery is true, it's "Paid-Delivery"
            return deliveryPaymentTypeRepository
                    .findByPaymentType("Paid-Delivery")
                    .orElseThrow(() -> new EntityNotFoundException("DeliveryPaymentType 'Paid-Delivery' not found"));
        } else {
            // If isPaidDelivery is false, it's "Not-Paid-Delivery"
            return deliveryPaymentTypeRepository
                    .findByPaymentType("Not-Paid-Delivery")
                    .orElseThrow(() -> new EntityNotFoundException("DeliveryPaymentType 'Not-Paid-Delivery' not found"));
        }
    }
    
    // Logic to calculate priceDelivery based on weight
    private BigDecimal calculatePriceDelivery(BigDecimal weight) {
        BigDecimal baseDeliveryPrice = new BigDecimal("5.00"); // Base delivery price
        BigDecimal pricePerKg = new BigDecimal("2.00"); // Price per kilogram
        
        // Check if weight is null
        if (weight == null) {
            return BigDecimal.ZERO;
        }
        
        if (weight.compareTo(new BigDecimal("5.00")) > 0) {
            // If weight is greater than 5 kg, calculate based on the condition
            BigDecimal extraWeight = weight.subtract(new BigDecimal("5.00"));
            baseDeliveryPrice = baseDeliveryPrice.add(extraWeight.multiply(pricePerKg));
        }
        
        return baseDeliveryPrice;
    }
    
    
    @Override
    public void registerSentShipment(Shipment shipment) {
        // Check if the shipment has a predefined ID
        if (shipment.getId() != 0) {
            throw new RuntimeException("Cannot register sent shipment with predefined ID");
        }

        // Set additional details for a sent shipment
        shipment.setShipmentDate(LocalDate.now()); // Set the shipment date to the current date
        shipment.setReceivedDate(null); // Reset received date for sent shipments

        // Implement other logic as needed

        // Save the shipment
        shipmentRepository.save(shipment);
    }

    @Override
    public void registerReceivedShipment(Shipment shipment) {
        // Check if the shipment has a predefined ID
        if (shipment.getId() != 0) {
            throw new RuntimeException("Cannot register received shipment with predefined ID");
        }

        // Set additional details for a received shipment
        shipment.setReceivedDate(LocalDate.now()); // Set the received date to the current date
        // Implement other logic as needed
        // Save the shipment
        shipmentRepository.save(shipment);
    }

//
//    @Override
//    public void createShipment(Shipment shipment) {
//        if (shipment.getId() != 0) {
//            throw new RuntimeException("Cannot create shipment with predefined ID");
//        }
//        shipmentRepository.save(shipment);
//    }

    @Override
    public List<Shipment> getAllShipments() {
        return shipmentRepository.findAll();
    }

    @Override
    public Shipment getShipmentById(long shipmentId) {
        return shipmentRepository.findById(shipmentId)
                .orElseThrow(() -> new EntityNotFoundException("Shipment not found with id: " + shipmentId));
    }

    @Override
    public void updateShipment(long shipmentId, Shipment updatedShipment) {
        Shipment existingShipment = shipmentRepository.findById(shipmentId)
                .orElseThrow(() -> new EntityNotFoundException("Shipment not found with id: " + shipmentId));

        // Add protection to ensure IDs match
        if (existingShipment.getId() != updatedShipment.getId()) {
            throw new RuntimeException("Mismatched IDs in the request");
        }

        // Update logic if needed
        existingShipment.setShipmentDate(updatedShipment.getShipmentDate());
        existingShipment.setWeight(updatedShipment.getWeight());
        existingShipment.setPrice(updatedShipment.getPrice());
        existingShipment.setIsPaid(updatedShipment.getIsPaid());
        existingShipment.setReceivedDate(updatedShipment.getReceivedDate());

        shipmentRepository.save(existingShipment);
    }

    @Override
    public void deleteShipment(long shipmentId) {
        if (shipmentRepository.existsById(shipmentId)) {
            shipmentRepository.deleteById(shipmentId);
        } else {
            throw new EntityNotFoundException("Shipment not found with id: " + shipmentId);
        }
    }
    
    
    //5.d.------------------------
    @Override
    public List<Shipment> getAllSentShipmentsByEmployeeId(Long employeeId) {
        return shipmentRepository.findBySenderEmployeeId(employeeId);
    }
    @Override
    public List<Shipment> getAllReceivedShipmentsByEmployeeId(Long employeeId) {
        return shipmentRepository.findByReceiverEmployeeId(employeeId);
    }
    
    //5.e.------------------------
    public List<ShipmentDTO> getShipmentsSentButNotReceived() {
        String sentStatus = "SENT";
        List<String> notReceivedStatuses = Arrays.asList("RECEIVED", "DELIVERED");
        List<Shipment> shipments = shipmentRepository.findShipmentsSentButNotReceived(sentStatus, notReceivedStatuses);
        return shipments.stream()
                .map(entityDtoMapper::convertToShipmentDTO)
                .collect(Collectors.toList());
    }
    
    //5.f.------------------------
    @Override
    public List<ShipmentDTO> getShipmentsBySenderCustomerId(Long customerId) {
        List<Shipment> shipments = shipmentRepository.findBySenderCustomerId(customerId);
        return shipments.stream()
                .map(entityDtoMapper::convertToShipmentDTO)
                .collect(Collectors.toList());
    }
    
    //5.g.------------------------
    @Override
    public List<ShipmentDTO> getShipmentsByReceiverCustomerId(Long customerId) {
        List<Shipment> shipments = shipmentRepository.findByReceiverCustomerId(customerId);
        return shipments.stream()
                .map(entityDtoMapper::convertToShipmentDTO)
                .collect(Collectors.toList());
    }
    
    
    //logic................................................................

    private void createInitialShipmentStatusHistory(Shipment shipment) {
        ShipmentStatusHistory statusHistory = new ShipmentStatusHistory();
        statusHistory.setShipment(shipment);
        statusHistory.setUpdateDate(LocalDateTime.now());
        
        // Fetch the 'Registered' status from the repository
        ShipmentStatus registeredStatus = shipmentStatusRepository.findByShipmentStatus("REGISTERED")
                .orElseThrow(() -> new EntityNotFoundException("ShipmentStatus 'Registered' not found"));
        
        // Set the fetched status in the status history
        statusHistory.setShipmentStatus(registeredStatus);
        
        shipmentStatusHistoryRepository.save(statusHistory);
    }
    
}
