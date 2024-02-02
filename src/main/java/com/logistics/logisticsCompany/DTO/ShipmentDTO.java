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
    
    @JsonProperty("totalPrice")
    private BigDecimal totalPrice;
    // No-argument constructor
    public ShipmentDTO() {
    }
    
    // Constructor with necessary fields
    
    public ShipmentDTO(BigDecimal weight, BigDecimal price, boolean isPaid, BigDecimal priceDelivery,
                       boolean isPaidDelivery, String senderCustomerPhoneNumber, String receiverCustomerPhoneNumber,
                       Long senderEmployeeId, Long receiverEmployeeId, Long deliveryPaymentTypeId, Long receiverOfficeId, BigDecimal totalPrice) {
        this.weight = weight;
        this.price = price;
        this.isPaid = isPaid;
        this.priceDelivery = priceDelivery;
        this.isPaidDelivery = isPaidDelivery;
        this.senderCustomerPhoneNumber = senderCustomerPhoneNumber;
        this.receiverCustomerPhoneNumber = receiverCustomerPhoneNumber;
        this.senderEmployeeId = senderEmployeeId;
        this.receiverEmployeeId = receiverEmployeeId;
        this.deliveryPaymentTypeId = deliveryPaymentTypeId;
        this.receiverOfficeId = receiverOfficeId;
        this.totalPrice = totalPrice;
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
    
    public void setSenderCustomerPhoneNumber(String senderCustomerPhoneNumber) {
        this.senderCustomerPhoneNumber = senderCustomerPhoneNumber;
    }
    
    public void setReceiverCustomerPhoneNumber(String receiverCustomerPhoneNumber) {
        this.receiverCustomerPhoneNumber = receiverCustomerPhoneNumber;
    }
    
    public void setSenderEmployeeId(Long senderEmployeeId) {
        this.senderEmployeeId = senderEmployeeId;
    }
    
    public void setReceiverEmployeeId(Long receiverEmployeeId) {
        this.receiverEmployeeId = receiverEmployeeId;
    }
    
    public void setDeliveryPaymentTypeId(Long deliveryPaymentTypeId) {
        this.deliveryPaymentTypeId = deliveryPaymentTypeId;
    }
    
    public void setReceiverOfficeId(Long receiverOfficeId) {
        this.receiverOfficeId = receiverOfficeId;
    }
    
    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
    
    public BigDecimal getWeight() {
        return weight;
    }
    
    public BigDecimal getPrice() {
        return price;
    }
    
    public boolean getIsPaid() {
        return isPaid;
    }
    
    public BigDecimal getPriceDelivery() {
        return priceDelivery;
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
    
    public Long getReceiverEmployeeId() {
        return receiverEmployeeId;
    }
    
    public Long getDeliveryPaymentTypeId() {
        return deliveryPaymentTypeId;
    }
    
    public Long getReceiverOfficeId() {
        return receiverOfficeId;
    }
    
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }
}
