package com.mirea.desserter.repos;

import com.mirea.desserter.models.Basket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IBasketRepo extends JpaRepository<Basket, Integer> {

    Basket findByUserIdAndProductId(int userId, int productId);

    List<Basket> findAllByUserId(int userId);

    Basket findById(int id);

    Long deleteById(int id);

    @Transactional
    Long deleteAllByUserId(int userId);
}
