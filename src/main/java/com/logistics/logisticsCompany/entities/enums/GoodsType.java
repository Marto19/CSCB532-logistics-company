package com.logistics.logisticsCompany.entities.enums;

import com.logistics.logisticsCompany.entities.orders.Shipment;
import jakarta.persistence.*;

import java.util.Set;

/**
 * The GoodsType class is used to represent a goods type entity.
 * It contains the id and type name of the goods type.
 * Its supposed to be an enum but I made it an entity to be able to add more goods types in the future.
 */
@Entity
@Table(name = "goods_type")
public class GoodsType {
    /**
     * The id of the goods type.
     * It is a unique identifier for the goods type.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * The type name of the goods type.
     */
    @Column(name = "type_name")     //TODO: add validation annotations
    private String typeName;

//    @OneToOne(mappedBy = "goodsType")
//    private Goods goods;

    /**
     * The set of shipments of the goods type.
     */
    @OneToMany(mappedBy = "goodsType")
    private Set<Shipment> shipment;

    /**
     * Default constructor for the {@code GoodsType} class.
     * @param typeName
     */
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
