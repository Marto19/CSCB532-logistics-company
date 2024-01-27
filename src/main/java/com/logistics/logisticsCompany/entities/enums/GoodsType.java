package com.logistics.logisticsCompany.entities.enums;

import com.logistics.logisticsCompany.entities.orders.Shipment;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "goods_type")
public class GoodsType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "type_name")     //TODO: add validation annotations
    private String typeName;

//    @OneToOne(mappedBy = "goodsType")
//    private Goods goods;

    @OneToMany(mappedBy = "goodsType")
    private Set<Shipment> shipment;

    public GoodsType(String typeName) {
        this.typeName = typeName;
    }

    public GoodsType() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return "GoodsType{" +
                "id=" + id +
                ", typeName='" + typeName + '\'' +
                '}';
    }
}
