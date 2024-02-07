package com.logistics.logisticsCompany.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.logistics.logisticsCompany.DTO.CustomerDTO;
import com.logistics.logisticsCompany.DTO.EmployeeDTO;
import com.logistics.logisticsCompany.DTO.OfficeDTO;
import com.logistics.logisticsCompany.validation.ValidPhoneNumber;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * The ShipmentDTO class is used to represent a shipment data transfer object.
 * It contains the sender customer phone number, receiver customer phone number, price, weight, is paid delivery, receiver office id, and sender employee id.
 */
public class ShipmentDTO {
    //reduced because we dont need id and shipment date to transfer?right?
    /**
     * The sender customer phone number.
     * It is a unique identifier for the sender customer.
     */
    @JsonProperty("senderCustomerPhoneNumber")
    @NotNull(message = "SenderCustomerPhoneNumber cannot be null")
    @ValidPhoneNumber(message = "Sender's phone number must be valid form.")
    private String senderCustomerPhoneNumber;

    /**
     * The receiver customer phone number.
     * It is a unique identifier for the receiver customer.
     */
    @JsonProperty("receiverCustomerPhoneNumber")
    @NotNull(message = "ReceiverCustomerPhoneNumber cannot be null")
    @ValidPhoneNumber(message = "Reciever's phone number must be valid form.")
    private String receiverCustomerPhoneNumber;

    /**
     * The price of the shipment.
     */
    @JsonProperty("price")
    @PositiveOrZero(message = "Price cannot be negative")
    private BigDecimal price;

    /**
     * The weight of the shipment.
     */
    @JsonProperty("weight")
    @NotNull(message = "Weight cannot be null")
    @Positive(message = "Weight must be positive")
    private BigDecimal weight;

    /**
     * The is paid delivery of the shipment.
     */
    @JsonProperty("isPaidDelivery")
    @NotNull(message = "IsPaidDelivery cannot be null")
    private boolean isPaidDelivery;

    /**
     * The receiver office id of the shipment.
     */
    @JsonProperty("receiverOfficeId")
    @NotNull(message = "ReceiverOfficeId cannot be null")
    private Long receiverOfficeId;

    /**
     * The sender employee id of the shipment.
     */
    @JsonProperty("senderEmployeeId")
    //todo remove if shipment creation is fixed
    //@NotNull(message = "SenderEmployeeId cannot be null")
    private Long senderEmployeeId;
    
    
    @JsonProperty("shipmentDate")
    private LocalDate shipmentDate;


    /**
     * Default constructor for the {@code ShipmentDTO} class.
     * It is used to create an empty shipment data transfer object.
     * This constructor is used by the Jackson library to create an empty shipment data transfer object during deserialization.
     * It should not be used explicitly.
     *
     */
    // No-argument constructor
    public ShipmentDTO() {
    }
    
    // Constructor with necessary fields

    /**
     * Constructs a ShipmentDTO with the specified weight, price, is paid delivery, price delivery, is paid delivery, sender customer phone number, receiver customer phone number, sender employee id, receiver employee id, delivery payment type id, receiver office id, and total price.
     * @param weight the weight
     * @param price the price
     * @param isPaidDelivery the is paid delivery
     * @param priceDelivery the price delivery
     * @param isPaidDelivery the is paid delivery
     * @param senderCustomerPhoneNumber the sender customer phone number
     * @param receiverCustomerPhoneNumber the receiver customer phone number
     * @param senderEmployeeId the sender employee id
     * @param receiverEmployeeId the receiver employee id
     * @param deliveryPaymentTypeId the delivery payment type id
     * @param receiverOfficeId the receiver office id
     * @param totalPrice the total price
     */
    public ShipmentDTO(BigDecimal weight, BigDecimal price, boolean isPaid, BigDecimal priceDelivery,
                       boolean isPaidDelivery, String senderCustomerPhoneNumber, String receiverCustomerPhoneNumber,
                       Long senderEmployeeId, Long receiverEmployeeId, Long deliveryPaymentTypeId, Long receiverOfficeId, BigDecimal totalPrice) {
        this.weight = weight;
        this.price = price;
        this.isPaidDelivery = isPaidDelivery;
        this.senderCustomerPhoneNumber = senderCustomerPhoneNumber;
        this.receiverCustomerPhoneNumber = receiverCustomerPhoneNumber;
        this.senderEmployeeId = senderEmployeeId;
        this.receiverOfficeId = receiverOfficeId;
    }
    
    
    public LocalDate getShipmentDate() {
        return shipmentDate;
    }
    
    public void setShipmentDate(LocalDate shipmentDate) {
        this.shipmentDate = shipmentDate;
    }
    
    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }
    
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    
    
    
    public void setIsPaidDelivery(boolean isPaidDelivery) {
        this.isPaidDelivery = isPaidDelivery;
    }
    
    public void setSenderCustomerPhoneNumber(String senderCustomerPhoneNumber) {
        this.senderCustomerPhoneNumber = senderCustomerPhoneNumber;
    }
    
    public void setReceiverCustomerPhoneNumber(String receiverCustomerPhoneNumber) {
        this.receiverCustomerPhoneNumber = receiverCustomerPhoneNumber;
    }
    
    public void setSenderEmployeeId(Long senderEmployeeId) {
        this.senderEmployeeId = senderEmployeeId;
    }
    
    
    
    public void setReceiverOfficeId(Long receiverOfficeId) {
        this.receiverOfficeId = receiverOfficeId;
    }
    
    public BigDecimal getWeight() {
        return weight;
    }
    
    public BigDecimal getPrice() {
        return price;
    }
    
    public boolean getIsPaidDelivery() {
        return isPaidDelivery;
    }
    
    public String getSenderCustomerPhoneNumber() {
        return senderCustomerPhoneNumber;
    }
    
    public String getReceiverCustomerPhoneNumber() {
        return receiverCustomerPhoneNumber;
    }
    
    public Long getSenderEmployeeId() {
        return senderEmployeeId;
    }
    
    
    public Long getReceiverOfficeId() {
        return receiverOfficeId;
    }
    

}
