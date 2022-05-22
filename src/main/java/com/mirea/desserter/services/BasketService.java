package com.mirea.desserter.services;

import com.mirea.desserter.models.Basket;
import com.mirea.desserter.repos.IBasketRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BasketService {

    private IBasketRepo basketRepo;

    @Autowired
    public BasketService(IBasketRepo basketRepo) {
        this.basketRepo = basketRepo;
    }


    public List<Basket> getPurchasesByUserId(int userId) {
        return basketRepo.findAllByUserId(userId);
    }

    public Basket getPurchaseByUserIdAndProductId(int userId, int productId){
        return basketRepo.findByUserIdAndProductId(userId, productId);
    }

    public Basket getPurchaseById (int id) {
        return basketRepo.findById(id);
    }

    public void savePurchase(Basket purchase) {
        basketRepo.save(purchase);
    }

    public void deletePurchaseById(int id) {
        basketRepo.deleteById(id);
    }

    public void deletePurchase(Basket purchase){
        basketRepo.delete(purchase);
    }

    public void deleteAllByUserId(int userId)
    {
        basketRepo.deleteAllByUserId(userId);
    }
}
