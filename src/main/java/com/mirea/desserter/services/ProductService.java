package com.mirea.desserter.services;


import com.mirea.desserter.models.Product;
import com.mirea.desserter.repos.IProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private IProductRepo productRepo;

    @Autowired
    public ProductService(IProductRepo productRepo){
        this.productRepo = productRepo;
    }

    public List<Product> getAllProducts(){
        return productRepo.findAll();
    }

    public List<Product> getAllProductsByTypeId(int typeId) {
        return productRepo.findAllByTypeId(typeId);
    }

    public Product getProductById(int id){
        return productRepo.findById(id);
    }

    public void saveProduct(Product product){
        productRepo.save(product);
    }

    public void deleteProductById(int id){
        productRepo.deleteById(id);
    }
}
