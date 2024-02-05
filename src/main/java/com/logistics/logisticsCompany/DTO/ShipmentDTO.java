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

    @JsonProperty("pricePaid")
    private boolean pricePaid;

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

    public ShipmentDTO(long id, LocalDate shipmentDate, BigDecimal weight, BigDecimal price, boolean pricePaid, LocalDate receivedDate, OfficeDTO senderOffice, CustomerDTO senderCustomer, EmployeeDTO senderEmployee, OfficeDTO receiverOffice, CustomerDTO receiverCustomer, EmployeeDTO receiverEmployee) {
        this.id = id;
        this.shipmentDate = shipmentDate;
        this.weight = weight;
        this.price = price;
        this.pricePaid = pricePaid;
        this.receivedDate = receivedDate;
        this.senderOffice = senderOffice;
        this.senderCustomer = senderCustomer;
        this.senderEmployee = senderEmployee;
        this.receiverOffice = receiverOffice;
        this.receiverCustomer = receiverCustomer;
        this.receiverEmployee = receiverEmployee;
    }

    // Getters and setters
    // ...
}
