package com.logistics.logisticsCompany.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.logistics.logisticsCompany.DTO.CustomerDTO;
import com.logistics.logisticsCompany.DTO.EmployeeDTO;
import com.logistics.logisticsCompany.DTO.OfficeDTO;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ShipmentDTO {
    //reduced because we dont need id and shipment date to transfer?right?
    @JsonProperty("senderCustomerPhoneNumber")
    @NotNull(message = "SenderCustomerPhoneNumber cannot be null")
    private String senderCustomerPhoneNumber;

    @JsonProperty("receiverCustomerPhoneNumber")
    @NotNull(message = "ReceiverCustomerPhoneNumber cannot be null")
    private String receiverCustomerPhoneNumber;
    
    @JsonProperty("price")
    @PositiveOrZero(message = "Price cannot be negative")
    private BigDecimal price;
    
    @JsonProperty("weight")
    @NotNull(message = "Weight cannot be null")
    @Positive(message = "Weight must be positive")
    private BigDecimal weight;
    
    @JsonProperty("isPaidDelivery")
    @NotNull(message = "IsPaidDelivery cannot be null")
    private boolean isPaidDelivery;
    
    @JsonProperty("receiverOfficeId")
    @NotNull(message = "ReceiverOfficeId cannot be null")
    private Long receiverOfficeId;
    
    @JsonProperty("senderEmployeeId")
    @NotNull(message = "SenderEmployeeId cannot be null")
    private Long senderEmployeeId;
    


    // No-argument constructor
    public ShipmentDTO() {
    }
    
    // Constructor with necessary fields
    
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
