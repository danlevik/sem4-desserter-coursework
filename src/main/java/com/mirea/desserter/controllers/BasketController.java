package com.mirea.desserter.controllers;

import com.mirea.desserter.models.Basket;
import com.mirea.desserter.models.User;
import com.mirea.desserter.repos.IBasketRepo;
import com.mirea.desserter.repos.IProductRepo;
import com.mirea.desserter.repos.ITypeRepo;
import com.mirea.desserter.services.BasketService;
import com.mirea.desserter.services.ProductService;
import com.mirea.desserter.services.TypeService;
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
    private ProductService productService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private UserService userService;

    @Autowired
    private BasketService basketService;


    @GetMapping("/basket")
    public String basket(Model model,
                         @AuthenticationPrincipal User user){

        int userId = user.getId();

        model.addAttribute("authority", user.getAuthorities().toString());
        model.addAttribute("basketPrice", productService.getBasketPrice(basketService.getPurchasesByUserId(userId)));
        model.addAttribute("types", typeService.getAllTypes());

        List<Basket> purchases = basketService.getPurchasesByUserId(userId);

        model.addAttribute("basket", purchases);
        model.addAttribute("productService", productService);

        return "basket";
    }

    @PostMapping("/basketDeletePurchase")
    public String deletePurchase(@RequestParam(value = "delButton") int purchaseId){
        basketService.deletePurchaseById(purchaseId);
        return "redirect:/basket";
    }

    @PostMapping("/basketIncrPurchase")
    public String increasePurchase(@RequestParam(value = "incrButton") int purchaseId){
        Basket purchase = basketService.getPurchaseById(purchaseId);
        purchase.setProductCount(purchase.getProductCount() + 1);
        basketService.savePurchase(purchase);
        return "redirect:/basket";
    }

    @PostMapping("/basketDecrPurchase")
    public String decreasePurchase(@RequestParam(value = "decrButton") int purchaseId){
        Basket purchase = basketService.getPurchaseById(purchaseId);
        purchase.setProductCount(purchase.getProductCount() - 1);
        basketService.savePurchase(purchase);
        if (purchase.getProductCount() <= 0){
            basketService.deletePurchase(purchase);
        }
        return "redirect:/basket";
    }

}
