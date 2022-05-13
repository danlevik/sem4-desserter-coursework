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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @PostMapping("/basketDeletePurchase")
    public String deletePurchase(@RequestParam(value = "delButton") int purchaseId){
        basketRepo.deleteById(purchaseId);
        return "redirect:/basket";
    }

    @PostMapping("/basketIncrPurchase")
    public String increasePurchase(@RequestParam(value = "incrButton") int purchaseId){
        Basket purchase = basketRepo.findById(purchaseId);
        purchase.setProductCount(purchase.getProductCount() + 1);
        basketRepo.save(purchase);
        return "redirect:/basket";
    }

    @PostMapping("/basketDecrPurchase")
    public String decreasePurchase(@RequestParam(value = "decrButton") int purchaseId){
        Basket purchase = basketRepo.findById(purchaseId);
        purchase.setProductCount(purchase.getProductCount() - 1);
        basketRepo.save(purchase);
        if (purchase.getProductCount() <= 0){
            basketRepo.delete(purchase);
        }
        return "redirect:/basket";
    }

}
