package com.mirea.desserter.controllers;

import com.mirea.desserter.models.Basket;
import com.mirea.desserter.models.User;
import com.mirea.desserter.repos.IBasketRepo;
import com.mirea.desserter.repos.IProductRepo;
import com.mirea.desserter.repos.ITypeRepo;
import com.mirea.desserter.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class BasketController {

    @Autowired
    private IProductRepo productRepo;

    @Autowired
    private ITypeRepo typeRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private IBasketRepo basketRepo;


    private int getBasketPrice(List<Basket> purchases) {
        int total = 0;
        for (Basket basket: purchases){
            total += productRepo.findById(basket.getProductId()).getPrice() * basket.getProductCount();
        }
        return total;
    }

    @GetMapping("/basket")
    public String basket(Model model,
                         @AuthenticationPrincipal User user){

        int userId = user.getId();
        model.addAttribute("basketPrice", getBasketPrice(basketRepo.findAllByUserId(userId)));
        model.addAttribute("types", typeRepo.findAll());

        List<Basket> purchases = basketRepo.findAllByUserId(userId);

        model.addAttribute("basket", purchases);
        model.addAttribute("productRepo", productRepo);

        return "basket";
    }


}
