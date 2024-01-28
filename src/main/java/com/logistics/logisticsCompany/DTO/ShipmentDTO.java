package com.logistics.logisticsCompany.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.logistics.logisticsCompany.DTO.CustomerDTO;
import com.logistics.logisticsCompany.DTO.EmployeeDTO;
import com.logistics.logisticsCompany.DTO.OfficeDTO;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ShipmentDTO {
    @JsonProperty("id")
    private long id;
    
    @JsonProperty("shipmentDate")
    private LocalDate shipmentDate;
    
    @JsonProperty("weight")
    private BigDecimal weight;
    
    @JsonProperty("price")
    private BigDecimal price;
    
    @JsonProperty("isPaidDelivery")
    private boolean isPaidDelivery;
    
    @JsonProperty("priceDelivery")
    private BigDecimal priceDelivery;
    
    @JsonProperty("isPaid")
    private boolean isPaid;
    
    @JsonProperty("receivedDate")
    private LocalDate receivedDate;
    
    @JsonProperty("senderOffice")
    private OfficeDTO senderOffice;
    
    @JsonProperty("senderCustomer")
    private CustomerDTO senderCustomer;
    
    @JsonProperty("senderEmployee")
    private EmployeeDTO senderEmployee;
    
    @JsonProperty("receiverOffice")
    private OfficeDTO receiverOffice;
    
    @JsonProperty("receiverCustomer")
    private CustomerDTO receiverCustomer;
    
    @JsonProperty("receiverEmployee")
    private EmployeeDTO receiverEmployee;
    
    
    // Constructors, getters, and setters

    // Ensure you have a no-argument constructor
    public ShipmentDTO() {
    }
    
    public ShipmentDTO(long id, LocalDate shipmentDate, BigDecimal weight, BigDecimal price, boolean isPaidDelivery, BigDecimal priceDelivery, boolean isPaid, LocalDate receivedDate, OfficeDTO senderOffice, CustomerDTO senderCustomer, EmployeeDTO senderEmployee, OfficeDTO receiverOffice, CustomerDTO receiverCustomer, EmployeeDTO receiverEmployee) {
        this.id = id;
        this.shipmentDate = shipmentDate;
        this.weight = weight;
        this.price = price;
        this.isPaidDelivery = isPaidDelivery;
        this.priceDelivery = priceDelivery;
        this.isPaid = isPaid;
        this.receivedDate = receivedDate;
        this.senderOffice = senderOffice;
        this.senderCustomer = senderCustomer;
        this.senderEmployee = senderEmployee;
        this.receiverOffice = receiverOffice;
        this.receiverCustomer = receiverCustomer;
        this.receiverEmployee = receiverEmployee;
    }

    // Getters and setters
    
    public void setId(long id) {
        this.id = id;
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
    
    public void setPriceDelivery(BigDecimal priceDelivery) {
        this.priceDelivery = priceDelivery;
    }
    
    public void setIsPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }
    
    public void setReceivedDate(LocalDate receivedDate) {
        this.receivedDate = receivedDate;
    }
    
    public void setSenderOffice(OfficeDTO senderOffice) {
        this.senderOffice = senderOffice;
    }
    
    public void setSenderCustomer(CustomerDTO senderCustomer) {
        this.senderCustomer = senderCustomer;
    }
    
    public void setSenderEmployee(EmployeeDTO senderEmployee) {
        this.senderEmployee = senderEmployee;
    }
    
    public void setReceiverOffice(OfficeDTO receiverOffice) {
        this.receiverOffice = receiverOffice;
    }
    
    public void setReceiverCustomer(CustomerDTO receiverCustomer) {
        this.receiverCustomer = receiverCustomer;
    }
    
    public void setReceiverEmployee(EmployeeDTO receiverEmployee) {
        this.receiverEmployee = receiverEmployee;
    }
    
    public long getId() {
        return id;
    }
    
    public LocalDate getShipmentDate() {
        return shipmentDate;
    }
    
    public BigDecimal getWeight() {
        return weight;
    }
    
    public BigDecimal getPrice() {
        return price;
    }
    
    public boolean isPaidDelivery() {
        return isPaidDelivery;
    }
    
    public BigDecimal getPriceDelivery() {
        return priceDelivery;
    }
    
    public boolean isPaid() {
        return isPaid;
    }
    
    public LocalDate getReceivedDate() {
        return receivedDate;
    }
    
    public OfficeDTO getSenderOffice() {
        return senderOffice;
    }
    
    public CustomerDTO getSenderCustomer() {
        return senderCustomer;
    }
    
    public EmployeeDTO getSenderEmployee() {
        return senderEmployee;
    }
    
    public OfficeDTO getReceiverOffice() {
        return receiverOffice;
    }
    
    public CustomerDTO getReceiverCustomer() {
        return receiverCustomer;
    }
    
    public EmployeeDTO getReceiverEmployee() {
        return receiverEmployee;
    }
}
