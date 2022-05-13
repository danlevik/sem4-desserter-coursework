package com.mirea.desserter.controllers;

import com.mirea.desserter.models.Basket;
import com.mirea.desserter.models.Product;
import com.mirea.desserter.models.Type;
import com.mirea.desserter.models.User;
import com.mirea.desserter.repos.IBasketRepo;
import com.mirea.desserter.repos.IProductRepo;
import com.mirea.desserter.repos.ITypeRepo;
import com.mirea.desserter.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    private IProductRepo productRepo;

    @Autowired
    private ITypeRepo typeRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private IBasketRepo basketRepo;


    private int getUserId(Authentication authentication) {
        if (authentication == null)
            return 0;
        else
            return ((User)userService.loadUserByUsername(authentication.getName())).getId();
    }

    private String getUserRole(Authentication authentication) {
        if (authentication == null)
            return "GUEST";
        else
            return ((User)userService.loadUserByUsername(authentication.getName())).getRole();
    }

    @GetMapping("/products")
    public String productsPage(
            @RequestParam(name = "typeId", required = false) Integer typeId,
            Model model){

        model.addAttribute("types", typeRepo.findAll());
        model.addAttribute("typeId", typeId);

        Iterable<Product> products = productRepo.findAll();

        if (typeId == null) {
            model.addAttribute("products", products);
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
        model.addAttribute("productId", id);
        model.addAttribute("product_type", typeRepo.findById(product.getTypeId()));
        model.addAttribute("types", typeRepo.findAll());

        return "product";
    }

    @PostMapping("/products/{id}")
    public String addProductToBasket(
            Authentication authentication,
            @PathVariable(value = "id") int productId,
            Model model
            ) {
        String userRole = getUserRole(authentication);

        if (userRole.equals("GUEST")) {
            return "redirect:/products/" + productId;
        }
        else {
            int userId = getUserId(authentication);
            Basket basket = basketRepo.findByUserIdAndProductId(userId, productId);
            if (basket == null){
                Basket newBasket = new Basket();
                newBasket.setUserId(userId);
                newBasket.setProductId(productId);
                newBasket.setProductCount(1);
                basketRepo.save(newBasket);
                return "redirect:/products/" + productId;
            }
            else{
                basket.setProductCount(basket.getProductCount() + 1);
                basketRepo.save(basket);
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

    /**
     * Метод для проверки существования пользователя
     * @param request Объект содержащий запрос, поступивший от пользователя
     * @param username Имя пользователя
     * @param password Пароль пользователя
     */
    public void authWithHttpServletRequest(HttpServletRequest request, String username, String password) {
        try {
            request.login(username, password);
        } catch (ServletException e) { }
    }
}
