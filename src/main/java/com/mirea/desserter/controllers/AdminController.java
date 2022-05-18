package com.mirea.desserter.controllers;

import com.mirea.desserter.models.Product;
import com.mirea.desserter.repos.IBasketRepo;
import com.mirea.desserter.repos.IProductRepo;
import com.mirea.desserter.repos.ITypeRepo;
import com.mirea.desserter.services.BasketService;
import com.mirea.desserter.services.ProductService;
import com.mirea.desserter.services.TypeService;
import com.mirea.desserter.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ProductService productService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private UserService userService;

    @Autowired
    private BasketService basketService;


    @GetMapping
    public String addProductForm(Model model){
        model.addAttribute("products", productService.getAllProducts());

        return "admin";
    }

    @PostMapping("/create")
    public String createProduct(
            @RequestParam(name = "typeId") int typeId,
            @RequestParam(name = "name") String name,
            @RequestParam(name = "price") int price,
            @RequestParam(name = "description") String description,
            @RequestParam(name = "coverLink") String coverLink){

        Product newProduct = new Product();
        newProduct.setTypeId(typeId);
        newProduct.setName(name);
        newProduct.setPrice(price);
        newProduct.setDescription(description);
        newProduct.setCoverLink(coverLink);
        productService.saveProduct(newProduct);

        return "redirect:/admin";
    }

    @PostMapping("/delete")
    private String deleteProduct(
            @RequestParam(name = "id") int id) {
        productService.deleteProductById(id);
        return "redirect:/admin";
    }
}
