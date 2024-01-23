package com.logistics.logisticsCompany.repository;

import com.logistics.logisticsCompany.entities.goods.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodsRepository extends JpaRepository<Goods, Long> {
}
