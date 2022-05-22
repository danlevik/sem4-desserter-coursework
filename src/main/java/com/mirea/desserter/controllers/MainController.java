package com.mirea.desserter.controllers;

import com.mirea.desserter.models.Basket;
import com.mirea.desserter.models.Product;
import com.mirea.desserter.models.User;
import com.mirea.desserter.services.BasketService;
import com.mirea.desserter.services.ProductService;
import com.mirea.desserter.services.TypeService;
import com.mirea.desserter.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Controller
public class MainController {

    @Autowired
    private ProductService productService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private UserService userService;

    @Autowired
    private BasketService basketService;



    @GetMapping("/")
    public String productsPage(
            @AuthenticationPrincipal User user,
            @RequestParam(name = "typeId", required = false) Integer typeId,
            Model model){

        model.addAttribute("authority", user.getAuthorities().toString());
        model.addAttribute("types", typeService.getAllTypes());
        model.addAttribute("typeId", typeId);

        Iterable<Product> products = productService.getAllProducts();

        if (typeId == null) {
            model.addAttribute("products", products);
        }
        else{
            model.addAttribute("products", productService.getAllProductsByTypeId(typeId));
        }

        return "products";
    }

    @GetMapping("/products/{id}")
    public String productPage(
            @AuthenticationPrincipal User user,
            @PathVariable(value="id") int id,
            Model model){

        Product product = productService.getProductById(id);

        model.addAttribute("authority", user.getAuthorities().toString());

        model.addAttribute("product", product);
        model.addAttribute("productId", id);
        model.addAttribute("product_type", typeService.getTypeById(product.getTypeId()));
        model.addAttribute("types", typeService.getAllTypes());

        return "product";
    }

    @PostMapping("/products/{id}")
    public String addProductToBasket(
            Authentication authentication,
            @PathVariable(value = "id") int productId,
            Model model
            ) {
        String userRole = userService.getUserRole(authentication);

        if (userRole.equals("GUEST")) {
            return "redirect:/products/" + productId;
        }
        else {
            int userId = userService.getUserId(authentication);
            Basket basket = basketService.getPurchaseByUserIdAndProductId(userId, productId);
            if (basket == null){
                Basket newBasket = new Basket();
                newBasket.setUserId(userId);
                newBasket.setProductId(productId);
                newBasket.setProductCount(1);
                basketService.savePurchase(newBasket);
                return "redirect:/products/" + productId;
            }
            else{
                basket.setProductCount(basket.getProductCount() + 1);
                basketService.savePurchase(basket);
                return "redirect:/products/" + productId;
            }
        }
    }

    @GetMapping("/signin")
    public String signIn() {
        return "signin";
    }

    @PostMapping("/signin")
    public String signInCreate(HttpServletRequest request,
                             @RequestParam(name = "username") String username,
                             @RequestParam(name = "password") String password,
                             Model model) {
        if (userService.loadUserByUsername(username) != null) {
            model.addAttribute("status", "user_exists");
            return "signin";
        }
        else {
            userService.create(username,password);
            authWithHttpServletRequest(request, username, password);
            return "redirect:/";
        }
    }

    public void authWithHttpServletRequest(HttpServletRequest request, String username, String password) {
        try {
            request.login(username, password);
        } catch (ServletException e) { }
    }
}
