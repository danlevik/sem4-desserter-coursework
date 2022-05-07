package com.mirea.desserter.controllers;

import com.mirea.desserter.models.Product;
import com.mirea.desserter.repos.IProductRepo;
import com.mirea.desserter.repos.ITypeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    @Autowired
    private IProductRepo productRepo;

    @Autowired
    private ITypeRepo typeRepo;


    @GetMapping("/products")
    public String productsPage(
            @RequestParam(name = "typeId", required = false) Integer typeId,
            Model model){

        model.addAttribute("types", typeRepo.findAll());
        model.addAttribute("typeId", typeId);

        if (typeId == null) {
            model.addAttribute("products", productRepo.findAll());
        }
        else{
            model.addAttribute("products", productRepo.findAllByTypeId(typeId));
        }

        return "products";
    }

    @GetMapping("/products/{id}")
    public String productPage(
            @PathVariable(value="id") int id,
            Model model){

        Product product = productRepo.findById(id);

        model.addAttribute("product", product);
        model.addAttribute("types", typeRepo.findAll());

        return "product";
    }
}
