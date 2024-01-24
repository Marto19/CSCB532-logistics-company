package com.logistics.logisticsCompany.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ShipmentDTO {
    private long id;
    private LocalDate shipmentDate;
    private BigDecimal weight;
    private BigDecimal price;
    private boolean pricePaid;
    private LocalDate receivedDate;
    private String senderOfficeName;
    private String senderCustomerName;
    private String senderEmployeeName;
    private String receiverOfficeName;
    private String receiverCustomerName;
    private String receiverEmployeeName;
    private List<Long> orderHistoryIds;

    // Constructors, getters, and setters...

    public ShipmentDTO() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getShipmentDate() {
        return shipmentDate;
    }

    public void setShipmentDate(LocalDate shipmentDate) {
        this.shipmentDate = shipmentDate;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public boolean isPricePaid() {
        return pricePaid;
    }

    public void setPricePaid(boolean pricePaid) {
        this.pricePaid = pricePaid;
    }

    public LocalDate getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(LocalDate receivedDate) {
        this.receivedDate = receivedDate;
    }

    public String getSenderOfficeName() {
        return senderOfficeName;
    }

    public void setSenderOfficeName(String senderOfficeName) {
        this.senderOfficeName = senderOfficeName;
    }

    public String getSenderCustomerName() {
        return senderCustomerName;
    }

    public void setSenderCustomerName(String senderCustomerName) {
        this.senderCustomerName = senderCustomerName;
    }

    public String getSenderEmployeeName() {
        return senderEmployeeName;
    }

    public void setSenderEmployeeName(String senderEmployeeName) {
        this.senderEmployeeName = senderEmployeeName;
    }

    public String getReceiverOfficeName() {
        return receiverOfficeName;
    }

    public void setReceiverOfficeName(String receiverOfficeName) {
        this.receiverOfficeName = receiverOfficeName;
    }

    public String getReceiverCustomerName() {
        return receiverCustomerName;
    }

    public void setReceiverCustomerName(String receiverCustomerName) {
        this.receiverCustomerName = receiverCustomerName;
    }

    public String getReceiverEmployeeName() {
        return receiverEmployeeName;
    }

    public void setReceiverEmployeeName(String receiverEmployeeName) {
        this.receiverEmployeeName = receiverEmployeeName;
    }

    public List<Long> getOrderHistoryIds() {
        return orderHistoryIds;
    }

    public void setOrderHistoryIds(List<Long> orderHistoryIds) {
        this.orderHistoryIds = orderHistoryIds;
    }

    @Override
    public String toString() {
        return "ShipmentDTO{" +
                "id=" + id +
                ", shipmentDate=" + shipmentDate +
                ", weight=" + weight +
                ", price=" + price +
                ", pricePaid=" + pricePaid +
                ", receivedDate=" + receivedDate +
                ", senderOfficeName='" + senderOfficeName + '\'' +
                ", senderCustomerName='" + senderCustomerName + '\'' +
                ", senderEmployeeName='" + senderEmployeeName + '\'' +
                ", receiverOfficeName='" + receiverOfficeName + '\'' +
                ", receiverCustomerName='" + receiverCustomerName + '\'' +
                ", receiverEmployeeName='" + receiverEmployeeName + '\'' +
                ", orderHistoryIds=" + orderHistoryIds +
                '}';
    }
}