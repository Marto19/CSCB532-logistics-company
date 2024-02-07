
package com.logistics.logisticsCompany.service;

import com.logistics.logisticsCompany.DTO.*;
import com.logistics.logisticsCompany.auth.AuthenticationService;
import com.logistics.logisticsCompany.config.JwtService;
import com.logistics.logisticsCompany.customExceptions.EntityNotFoundException;
import com.logistics.logisticsCompany.entities.enums.DeliveryPaymentType;
import com.logistics.logisticsCompany.entities.orders.Shipment;
import com.logistics.logisticsCompany.entities.users.Customer;
import com.logistics.logisticsCompany.entities.users.Employee;
import com.logistics.logisticsCompany.repository.ShipmentRepository;
import com.logistics.logisticsCompany.repository.ShipmentStatusHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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


/**
 * Service for managing shipments.
 */
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
    
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    /**
     * Constructs a new ShipmentServiceImpl with the given parameters.
     *
     * @param shipmentRepository The repository for shipments.
     * @param entityDtoMapper The mapper for converting entities to DTOs and vice versa.
     * @param deliveryPaymentTypeRepository The repository for delivery payment types.
     * @param customerService The service for managing customers.
     * @param employeeService The service for managing employees.
     * @param shipmentStatusHistoryRepository The repository for shipment status histories.
     * @param shipmentStatusRepository The repository for shipment statuses.
     * @param officeService The service for managing offices.
     * @param incomeHistoryService The service for managing income histories.
     * @param shipmentStatusHistoryService The service for managing shipment status histories.
     */
    @Autowired
    public ShipmentServiceImpl(ShipmentRepository shipmentRepository, EntityDtoMapper entityDtoMapper, DeliveryPaymentTypeRepository deliveryPaymentTypeRepository,
                               CustomerService customerService, EmployeeService employeeService, ShipmentStatusHistoryRepository shipmentStatusHistoryRepository,
                               ShipmentStatusRepository shipmentStatusRepository, OfficeService officeService, IncomeHistoryService incomeHistoryService,
                               ShipmentStatusHistoryService shipmentStatusHistoryService, JwtService jwtService, AuthenticationService authenticationService){
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
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }
    
    /*
    *Shipment fields:
    *isPaidx
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

    /**
     * Creates a new shipment based on the provided DTO.
     *
     * The shipment date is set to the current date.
     * The priceDelivery is calculated based on the weight.
     * The totalPrice is calculated based on the price and isPaidDelivery.
     * The deliveryPaymentType is determined based on the price and isPaidDelivery.
     * The sender and receiver customers are set based on the phone numbers.
     * The sender employee and office are set based on the logged-in employee's information.
     * The receiver office is set based on the provided office ID.
     * The shipment is saved to the repository.
     * If the delivery is paid, the daily income is recorded.
     * The initial shipment status history is created as 'REGISTERED'.
     *
     * @param shipmentDto The DTO containing the shipment details.
     * @return The created shipment.
     * @throws EntityNotFoundException if the sender or receiver customer is not found.
     * @throws EntityNotFoundException if the sender employee is not found.
     * @throws EntityNotFoundException if the receiver office is not found.
     *
     */
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
        
        
        
        //Get senderEmployeeId from authenticationService
        Long currentSenderEmployeeUserId = authenticationService.getCurrentUserId();
        Employee currentSenderEmployee = employeeService.getEmployeeByUserId(currentSenderEmployeeUserId);
        System.out.println("Current employee id: " + currentSenderEmployee.getId());//todo remove (temporary debug checks)
        
        shipment.setSenderEmployee(currentSenderEmployee);
        shipment.setSenderOffice(officeService.getOfficeByEmployeeId((currentSenderEmployee.getId())));
        
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

    /**
     * Determines the delivery payment type based on the provided shipment details.
     *
     * If the price is provided and greater than 0, it's "Cash-On-Delivery".
     * If isPaidDelivery is true, it's "Paid-Delivery".
     * If isPaidDelivery is false, it's "Not-Paid-Delivery".
     *
     * @param shipmentDto The DTO containing the shipment details.
     * @return The determined delivery payment type.
     * @throws EntityNotFoundException if the delivery payment type is not found.
     * @throws IllegalArgumentException if the shipment price is negative.
     *
     */
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

    /**
     * Calculates the delivery price based on the provided weight.
     *
     * The base delivery price is $5.00.
     * If the weight is greater than 5 kg, the price is increased by $2.00 per kg.
     *
     * @param weight The weight of the shipment.
     *               If null, the price is 0.
     *               If greater than 5 kg, the price is increased by $2.00 per kg.
     *               If less than or equal to 5 kg, the price is $5.00.
     * @return The calculated delivery price.
     *
     */
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


    /**
     * Marks the shipment as delivered.
     *
     * The received date is set to the current date.
     * The receiver employee is set based on the provided employee ID.
     * If the delivery is cash-on-delivery, the customer balance is updated.
     * If the delivery is not paid, the isPaidDelivery is set to true and the daily income is recorded.
     * The updated shipment is saved to the repository.
     * The shipment status history is updated to 'DELIVERED'.
     *
     * @param shipmentId The ID of the shipment to mark as delivered.
     * @param employeeId The ID of the employee marking the shipment as delivered.
     *                   If null, the currently logged-in employee is used.
     *                   If the employee is not found, an exception is thrown.
     *                   If the shipment is not found, an exception is thrown.
     *                   If the shipment is already delivered, an exception is thrown.
     *                   If the shipment is not paid and the price is null, an exception is thrown.
     */
    @Override
    @Transactional
    public void markShipmentAsDelivered(Long shipmentId) {
        Shipment shipment = shipmentRepository.findById(shipmentId)
                .orElseThrow(() -> new EntityNotFoundException("Shipment not found"));
        
        // Set received date and receiver employee
        shipment.setReceivedDate(LocalDate.now());
        
      /*  Employee receiverEmployee = employeeService.getCurrentlyLoggedInEmployee(); //!!!!!!!!!!!!!!!!!!!!!!!!!!FIXME Implement this method!!!!!!!!!!!!!!!!!!!!!!!!!!!
        shipment.setReceiverEmployee(receiverEmployee);*/
        
        //Get senderEmployeeId from authenticationService
        Long currentReceiverEmployeeUserId = authenticationService.getCurrentUserId();
        Employee currentReceiverEmployee = employeeService.getEmployeeByUserId(currentReceiverEmployeeUserId);
        System.out.println("Current employee id: " + currentReceiverEmployee.getId());//todo remove (temporary debug checks)
        
        shipment.setReceiverEmployee(currentReceiverEmployee);
        
        
        //Update customer balance if cash on delivery (add to balance)
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

    /**
     * Registers a sent shipment.
     *
     * The shipment date is set to the current date.
     * The received date is reset to null.
     * The updated shipment is saved to the repository.
     *
     * @param shipment The shipment to register as sent.
     *                 If the shipment has a predefined ID, an exception is thrown.
     */
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

    /**
     * Registers a received shipment.
     *
     * The received date is set to the current date.
     * The updated shipment is saved to the repository.
     *
     * @param shipment The shipment to register as received.
     *                 If the shipment has a predefined ID, an exception is thrown.
     */
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

    /**
     * Retrieves all shipments from the repository.
     *
     * @return A list of all shipments.
     */
    @Override
    public List<Shipment> getAllShipments() {
        return shipmentRepository.findAll();
    }

    /**
     * Retrieves a shipment by its ID.
     *
     * @param shipmentId The ID of the shipment to retrieve.
     * @return The retrieved shipment.
     * @throws EntityNotFoundException if the shipment is not found.
     */
    @Override
    public Shipment getShipmentById(long shipmentId) {
        return shipmentRepository.findById(shipmentId)
                .orElseThrow(() -> new EntityNotFoundException("Shipment with the provided id doesn't exist"));
    }

    /**
     * Updates an existing shipment.
     *
     * @param shipmentId The ID of the shipment to update.
     * @param updatedShipment The updated shipment.
     * @throws EntityNotFoundException if the shipment is not found.
     */
    @Override
    public void updateShipment(long shipmentId, Shipment updatedShipment) {
        if(!shipmentRepository.existsById(shipmentId)){
            throw new EntityNotFoundException("Shipment with the provided id doesn't exist");
        }
        updatedShipment.setId(shipmentId);
        shipmentRepository.save(updatedShipment);
    }

    /**
     * Deletes an existing shipment.
     *
     * @param shipmentId The ID of the shipment to delete.
     * @throws EntityNotFoundException if the shipment is not found.
     */
    @Override
    public void deleteShipment(long shipmentId) {
        if (!shipmentRepository.existsById(shipmentId)) {
            throw new EntityNotFoundException("Shipment with the provided id doesn't exist");
        }
        shipmentRepository.deleteById(shipmentId);
    }
    

    //5.d.------------------------
    /**
     * Retrieves all shipments sent by a specific employee.
     *
     * @param employeeId The ID of the employee.
     * @return A list of all shipments sent by the employee.
     */
    @Override
    public List<ShipmentDTO> getAllSentShipmentsByEmployeeId(Long employeeId) {
        List<Shipment> shipments = shipmentRepository.findBySenderEmployeeId(employeeId);
        return shipments.stream()
                .map(entityDtoMapper::convertToShipmentDTO)
                .collect(Collectors.toList());
    }

    
    //5.e.------------------------

    /**
     * Retrieves all shipments that are sent but not received.
     *
     * @return A list of all shipments that are sent but not received.
     */
    @Override
    public List<ShipmentDTO> getShipmentsSentButNotReceived() {
        // If there are more statuses in the future, you might want to handle them here
        List<Shipment> shipments = shipmentRepository.findByStatusNot("DELIVERED");
        return shipments.stream()
                .map(entityDtoMapper::convertToShipmentDTO)
                .collect(Collectors.toList());
    }
    
    //5.f.------------------------

    /**
     * Retrieves all shipments that are sent but not received by a specific employee.
     *
     * @param customerId The ID of the customer.
     * @return A list of all shipments that are sent but not received by the employee.
     */
    @Override
    public List<ShipmentDTO> getShipmentsBySenderCustomerId(Long customerId) {
        List<Shipment> shipments = shipmentRepository.findBySenderCustomerId(customerId);
        return shipments.stream()
                .map(entityDtoMapper::convertToShipmentDTO)
                .collect(Collectors.toList());
    }
    
    // Alternatively, if using phone number

    /**
     * Retrieves a list of shipments based on the sender's phone number.
     *
     * @param phone The phone number of the sender.
     * @return A list of ShipmentDTOs.
     */
    @Override
    public List<ShipmentDTO> getShipmentsBySenderCustomerPhone(String phone) {
        List<Shipment> shipments = shipmentRepository.findBySenderCustomerPhone(phone);
        return shipments.stream()
                .map(entityDtoMapper::convertToShipmentDTO)
                .collect(Collectors.toList());
    }

    //5.g.------------------------
    /**
     * Retrieves a list of shipments based on the receiver's customer ID.
     *
     * @param customerId The ID of the receiver customer.
     * @return A list of ShipmentDTOs.
     */
    @Override
    public List<ShipmentDTO> getShipmentsByReceiverCustomerId(Long customerId) {
        List<Shipment> shipments = shipmentRepository.findByReceiverCustomerId(customerId);
        return shipments.stream()
                .map(entityDtoMapper::convertToShipmentDTO)
                .collect(Collectors.toList());
    }
    
    
    //logic................................................................


    
}
