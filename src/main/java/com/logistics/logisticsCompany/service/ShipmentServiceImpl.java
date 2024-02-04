
package com.logistics.logisticsCompany.service;

import com.logistics.logisticsCompany.DTO.*;
import com.logistics.logisticsCompany.customExceptions.EntityNotFoundException;
import com.logistics.logisticsCompany.entities.enums.DeliveryPaymentType;
import com.logistics.logisticsCompany.entities.orders.Shipment;
import com.logistics.logisticsCompany.entities.users.Customer;
import com.logistics.logisticsCompany.entities.users.Employee;
import com.logistics.logisticsCompany.repository.ShipmentRepository;
import com.logistics.logisticsCompany.repository.ShipmentStatusHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import com.logistics.logisticsCompany.DTO.ShipmentDTO;
import org.springframework.transaction.annotation.Transactional;
import com.logistics.logisticsCompany.repository.DeliveryPaymentTypeRepository;
import com.logistics.logisticsCompany.repository.ShipmentStatusRepository;
import com.logistics.logisticsCompany.entities.offices.Office;
import java.util.Optional;
import java.util.Optional;

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
    
    private final IncomeHistoryService incomeHistoryService;
    private final ShipmentStatusHistoryService shipmentStatusHistoryService;
    
    @Autowired
    public ShipmentServiceImpl(ShipmentRepository shipmentRepository, EntityDtoMapper entityDtoMapper, DeliveryPaymentTypeRepository deliveryPaymentTypeRepository,
                               CustomerService customerService, EmployeeService employeeService, ShipmentStatusHistoryRepository shipmentStatusHistoryRepository,
                               ShipmentStatusRepository shipmentStatusRepository, OfficeService officeService, IncomeHistoryService incomeHistoryService, ShipmentStatusHistoryService shipmentStatusHistoryService){
        this.shipmentRepository = shipmentRepository;
        this.entityDtoMapper = entityDtoMapper;
        this.deliveryPaymentTypeRepository = deliveryPaymentTypeRepository;
        this.customerService = customerService;
        this.employeeService = employeeService;
	    this.shipmentStatusHistoryRepository = shipmentStatusHistoryRepository;
        this.shipmentStatusRepository = shipmentStatusRepository;
        this.officeService = officeService;
        this.incomeHistoryService = incomeHistoryService;
        this.shipmentStatusHistoryService = shipmentStatusHistoryService;
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
    * --------------CREATING SHIPMENT LOGIC --------------*/
    @Override
    @Transactional
    public Shipment createShipment(ShipmentDTO shipmentDto) {
        Shipment shipment = new Shipment();
        
        // Auto Set shipment date to the current date
        shipment.setShipmentDate(LocalDate.now());
        
        // Directly set properties from DTO
        // Set weight
        shipment.setWeight(shipmentDto.getWeight());
        //set isPaidDelivery
        shipment.setIsPaidDelivery(shipmentDto.getIsPaidDelivery());
        
        // Calculate priceDelivery based on weight, and set price if entered
        BigDecimal priceDelivery = calculatePriceDelivery(shipmentDto.getWeight());
        shipment.setPriceDelivery(priceDelivery);
        
        // if there is any price to get from receiver, if it is null, set it to 0
        BigDecimal price = (shipmentDto.getPrice() == null) ? BigDecimal.ZERO : shipmentDto.getPrice();
        shipment.setPrice(price);
        
        // Calculate totalPrice for receiver based on isDeliveryPaid flag
        BigDecimal totalPrice = shipmentDto.getIsPaidDelivery() ? price : price.add(priceDelivery);
        shipment.setTotalPrice(totalPrice);
        
        // Determine and set DeliveryPaymentType based on price and isPaidDelivery
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
        }
        
        
        //Save shipment entity
        shipmentRepository.save(shipment);
        
        // if deliveryispaid = true, add to the record of daily income
        if (shipment.getIsPaidDelivery()) {
            incomeHistoryService.recordDailyIncome(shipment.getPriceDelivery(), shipment.getSenderEmployee().getLogisticsCompany().getId());
        }
        
        // Create initial shipment status history as 'Registered'
        shipmentStatusHistoryService.recordShipmentStatusChange(shipment.getId(), "REGISTERED");
        
        return shipment;
    }
    
    //three variants of "DeliveryPaymentType" - "Cash-On-Delivery", "Paid-Delivery", "Not-Paid-Delivery".
    //based on logic whether price>0 and isPaidDelivery=true or false we determine the type
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
    
    // logic to calculate priceDelivery based on weight (if weight is null, return 0)
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
    @Transactional
    public void markShipmentAsDelivered(Long shipmentId, Long employeeId) {
        Shipment shipment = shipmentRepository.findById(shipmentId)
                .orElseThrow(() -> new EntityNotFoundException("Shipment not found"));
        
        // Set received date and receiver employee
        shipment.setReceivedDate(LocalDate.now());
        
      /*  Employee receiverEmployee = employeeService.getCurrentlyLoggedInEmployee(); //!!!!!!!!!!!!!!!!!!!!!!!!!!FIXME Implement this method!!!!!!!!!!!!!!!!!!!!!!!!!!!
        shipment.setReceiverEmployee(receiverEmployee);*/
        Employee receiverEmployee = employeeService.getEmployeeById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with ID: " + employeeId));//todo fix upper method
        shipment.setReceiverEmployee(receiverEmployee);
        
        //Update customer balance if cash on delivery
        processPaymentToCustomerBalance(shipmentId);
        
        // Update isPaidDelivery TO TRUE, because shipment is supposed to be paid when delivered
        if(!shipment.getIsPaidDelivery()) {
            shipment.setIsPaidDelivery(true);
            incomeHistoryService.recordDailyIncome(shipment.getPriceDelivery(), shipment.getSenderEmployee().getLogisticsCompany().getId());
        }
        ///////////////////////////////
        ///Save the updated shipment///
        ///////////////////////////////
        shipmentRepository.save(shipment);
        
        
        // Update shipment status history
        shipmentStatusHistoryService.recordShipmentStatusChange(shipment.getId(), "DELIVERED");
        
        
    }
    public void processPaymentToCustomerBalance(Long shipmentId) {
        Shipment shipment = shipmentRepository.findById(shipmentId)
                .orElseThrow(() -> new EntityNotFoundException("Shipment not found"));
        
        if ("Cash-On-Delivery".equals(shipment.getDeliveryPaymentType().getPaymentType())) {
            Customer senderCustomer = shipment.getSenderCustomer();
            BigDecimal shipmentPrice = shipment.getPrice();
            
            // Assuming the price is not null and the customer exists
            if (senderCustomer != null && shipmentPrice != null) {
                BigDecimal newBalance = senderCustomer.getBalance().add(shipmentPrice);
                senderCustomer.setBalance(newBalance);
                customerService.updateCustomer(senderCustomer);
            }
        }
        
        // Other shipment processing logic...
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
    public List<ShipmentDTO> getAllSentShipmentsByEmployeeId(Long employeeId) {
        List<Shipment> shipments = shipmentRepository.findBySenderEmployeeId(employeeId);
        return shipments.stream()
                .map(entityDtoMapper::convertToShipmentDTO)
                .collect(Collectors.toList());
    }

    
    //5.e.------------------------
    @Override
    public List<ShipmentDTO> getShipmentsSentButNotReceived() {
        // If there are more statuses in the future, you might want to handle them here
        List<Shipment> shipments = shipmentRepository.findByStatusNot("DELIVERED");
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
    
    // Alternatively, if using phone number
    @Override
    public List<ShipmentDTO> getShipmentsBySenderCustomerPhone(String phone) {
        List<Shipment> shipments = shipmentRepository.findBySenderCustomerPhone(phone);
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


    
}
