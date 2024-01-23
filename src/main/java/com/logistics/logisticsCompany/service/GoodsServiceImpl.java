package com.logistics.logisticsCompany.service;

import com.logistics.logisticsCompany.customExceptions.EntityNotFoundException;
import com.logistics.logisticsCompany.entities.goods.Goods;
import com.logistics.logisticsCompany.repository.GoodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {

    private final GoodsRepository goodsRepository;

    @Autowired
    public GoodsServiceImpl(GoodsRepository goodsRepository) {
        this.goodsRepository = goodsRepository;
    }

    @Override
    public void addGoods(Goods goods) {
        goodsRepository.save(goods);
    }

    @Override
    public List<Goods> getAllGoods() {
        return goodsRepository.findAll();
    }

    @Override
    public void updateGoods(long goodsId, Goods updatedGoods) {
        Goods existingGoods = goodsRepository.findById(goodsId)
                .orElseThrow(() -> new EntityNotFoundException("Goods not found with id: " + goodsId));

        // Update logic if needed
        existingGoods.setName(updatedGoods.getName());
        existingGoods.setWeight(updatedGoods.getWeight());
        existingGoods.setOrderHistory(updatedGoods.getOrderHistory());
        existingGoods.setGoodsType(updatedGoods.getGoodsType());

        goodsRepository.save(existingGoods);
    }

    @Override
    public void deleteGoods(long goodsId) {
        if (goodsRepository.existsById(goodsId)) {
            goodsRepository.deleteById(goodsId);
        } else {
            throw new EntityNotFoundException("Goods not found with id: " + goodsId);
        }
    }
}
