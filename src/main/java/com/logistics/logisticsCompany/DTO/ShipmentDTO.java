package com.logistics.logisticsCompany.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.logistics.logisticsCompany.DTO.CustomerDTO;
import com.logistics.logisticsCompany.DTO.EmployeeDTO;
import com.logistics.logisticsCompany.DTO.OfficeDTO;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ShipmentDTO {
    //reduced because we dont need id and shipment date to transfer?right?
    @JsonProperty("weight")
    private BigDecimal weight;
    
    @JsonProperty("price")
    private BigDecimal price;
    
    @JsonProperty("isPaid")
    private boolean isPaid;
    
    @JsonProperty("priceDelivery")
    private BigDecimal priceDelivery;
    
    @JsonProperty("isPaidDelivery")
    private boolean isPaidDelivery;
    
    @JsonProperty("senderCustomerPhoneNumber")
    private String senderCustomerPhoneNumber;
    
    @JsonProperty("receiverCustomerPhoneNumber")
    private String receiverCustomerPhoneNumber;
    
    @JsonProperty("senderEmployeeId")
    private Long senderEmployeeId;
    
    @JsonProperty("receiverEmployeeId")
    private Long receiverEmployeeId;
    
    @JsonProperty("deliveryPaymentTypeId")
    private Long deliveryPaymentTypeId;
    
    @JsonProperty("receiverOfficeId")
    private Long receiverOfficeId;
    // No-argument constructor
    public ShipmentDTO() {
    }
    
    // Constructor with necessary fields
    public ShipmentDTO(BigDecimal weight, String senderCustomerPhoneNumber, String receiverCustomerPhoneNumber, Long senderEmployeeId, Long deliveryPaymentTypeId, Long receiverOfficeId) {
        this.weight = weight;
        this.senderCustomerPhoneNumber = senderCustomerPhoneNumber;
        this.receiverCustomerPhoneNumber = receiverCustomerPhoneNumber;
        this.senderEmployeeId = senderEmployeeId;
        this.deliveryPaymentTypeId = deliveryPaymentTypeId;
        this.receiverOfficeId = receiverOfficeId;
    }
    
    public void setReceiverOfficeId(Long receiverOfficeId) {
        this.receiverOfficeId = receiverOfficeId;
    }
    
    public Long getReceiverOfficeId() {
        return receiverOfficeId;
    }
    
    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }
    
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    
    public void setIsPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }
    
    public void setPriceDelivery(BigDecimal priceDelivery) {
        this.priceDelivery = priceDelivery;
    }
    
    public void setIsPaidDelivery(boolean isPaidDelivery) {
        this.isPaidDelivery = isPaidDelivery;
    }
    
    public void setReceiverEmployeeId(Long receiverEmployeeId) {
        this.receiverEmployeeId = receiverEmployeeId;
    }
    
    public BigDecimal getPrice() {
        return price;
    }
    
    public boolean isPaid() {
        return isPaid;
    }
    
    public BigDecimal getPriceDelivery() {
        return priceDelivery;
    }
    
    public boolean isPaidDelivery() {
        return isPaidDelivery;
    }
    
    public Long getReceiverEmployeeId() {
        return receiverEmployeeId;
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
    
    public void setDeliveryPaymentTypeId(Long deliveryPaymentTypeId) {
        this.deliveryPaymentTypeId = deliveryPaymentTypeId;
    }
    
    public BigDecimal getWeight() {
        return weight;
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
    
    public Long getDeliveryPaymentTypeId() {
        return deliveryPaymentTypeId;
    }
}
