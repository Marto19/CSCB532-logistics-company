package com.logistics.logisticsCompany.controller;


import com.logistics.logisticsCompany.customExceptions.EntityNotFoundException;
import com.logistics.logisticsCompany.entities.goods.Goods;
import com.logistics.logisticsCompany.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/goods")
public class GoodsController {
    private final GoodsService goodsService;

    @Autowired
    public GoodsController(GoodsService goodsService) {
        this.goodsService = goodsService;
    }

    @PostMapping
    public ResponseEntity<String> addGoods(@RequestBody Goods goods) {
        try {
            goodsService.addGoods(goods);
            return ResponseEntity.status(HttpStatus.CREATED).body("Goods added successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping
    public List<Goods> getAllGoods() {
        return goodsService.getAllGoods();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateGoods(@PathVariable(value = "id") long goodsId,
                                              @RequestBody Goods updatedGoods) {
        try {
            goodsService.updateGoods(goodsId, updatedGoods);
            return ResponseEntity.ok("Goods updated successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGoods(@PathVariable(value = "id") long goodsId) {
        try {
            goodsService.deleteGoods(goodsId);
            return ResponseEntity.ok("Goods deleted successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
