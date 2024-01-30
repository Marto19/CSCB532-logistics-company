
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
        
        // Fetch delivery payment type
        DeliveryPaymentType deliveryPaymentType = deliveryPaymentTypeRepository.findById(shipmentDto.getDeliveryPaymentTypeId())
                .orElseThrow(() -> new EntityNotFoundException("DeliveryPaymentType not found"));
        shipment.setDeliveryPaymentType(deliveryPaymentType);
        
        // Set prices and paid statuses based on delivery payment type
        adjustPricesBasedOnDeliveryPaymentType(shipment, deliveryPaymentType);
        
        // Set sender and receiver customer by phone number
        Customer senderCustomer = customerService.getCustomerByPhoneNumber(shipmentDto.getSenderCustomerPhoneNumber());
        Customer receiverCustomer = customerService.getCustomerByPhoneNumber(shipmentDto.getReceiverCustomerPhoneNumber());
        shipment.setSenderCustomer(senderCustomer);
        shipment.setReceiverCustomer(receiverCustomer);
        
        // Set sender employee and office from the logged-in employee's information
        /*Employee senderEmployee = employeeService.getEmployeeById(shipmentDto.getSenderEmployeeId())
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with ID: " + shipmentDto.getSenderEmployeeId()));
        shipment.setSenderEmployee(senderEmployee);
        shipment.setSenderOffice(senderEmployee.getCurrentOffice());*/
        
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
    
    private void adjustPricesBasedOnDeliveryPaymentType(Shipment shipment, DeliveryPaymentType deliveryPaymentType) {
        if (deliveryPaymentType == null || deliveryPaymentType.getPaymentType() == null) {
            throw new IllegalArgumentException("Invalid or missing delivery payment type");

        }
        String paymentType = deliveryPaymentType.getPaymentType();
        switch (paymentType) {
            case "Cash-On-Delivery":
                shipment.setIsPaid(false);
                shipment.setIsPaidDelivery(false);
                shipment.setPrice(BigDecimal.ZERO);
                shipment.setPriceDelivery(calculatePriceDelivery(shipment.getWeight()));
                break;
            
            case "Paid-Delivery":
                shipment.setIsPaid(false);
                shipment.setIsPaidDelivery(true);
                shipment.setPrice(BigDecimal.ZERO);
                shipment.setPriceDelivery(BigDecimal.ZERO);
                break;
            
            case "Not-Paid-Delivery":
                shipment.setIsPaid(false);
                shipment.setIsPaidDelivery(false);
                shipment.setPrice(BigDecimal.ZERO);
                shipment.setPriceDelivery(calculatePriceDelivery(shipment.getWeight()));
                break;
            
            default:
                throw new IllegalArgumentException("Unknown delivery payment type: " + deliveryPaymentType.getPaymentType());
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
        existingShipment.setIsPaid(updatedShipment.isPaid());
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
