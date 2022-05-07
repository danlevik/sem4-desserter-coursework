package com.mirea.desserter.repos;

import com.mirea.desserter.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IProductRepo extends JpaRepository<Product, Integer> {

    List<Product> findAllByTypeId(int typeId);

    Product findById(int id);

    Long deleteById(int id);

}
