package com.logistics.logisticsCompany.entities.enums;

import com.logistics.logisticsCompany.entities.orders.ShipmentStatusHistory;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "goods_status")   //this table is an enum, it will contain: REGISTERED,SENT, SHIPPED, DELIVERED
public class GoodsStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "status")
    private String goodsStatus;

//    @OneToOne
//    private Goods goods;
    @OneToMany
    private Set<ShipmentStatusHistory> orderHistorySet;


    public GoodsStatus(String goodsStatus) {
        this.goodsStatus = goodsStatus;
    }

    public GoodsStatus(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGoodsStatus() {
        return goodsStatus;
    }

    public void setGoodsStatus(String goodsStatus) {
        this.goodsStatus = goodsStatus;
    }

    @Override
    public String toString() {
        return "GoodsStatus{" +
                "id=" + id +
                ", goodsStatus='" + goodsStatus + '\'' +
                '}';
    }
}
