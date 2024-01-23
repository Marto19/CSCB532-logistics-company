package com.logistics.logisticsCompany.service;

import com.logistics.logisticsCompany.entities.goods.Goods;
import java.util.List;

public interface GoodsService {

    void addGoods(Goods goods);

    List<Goods> getAllGoods();

    void updateGoods(long goodsId, Goods updatedGoods);

    void deleteGoods(long goodsId);
}
