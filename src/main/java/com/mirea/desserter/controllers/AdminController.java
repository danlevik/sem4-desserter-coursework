package com.mirea.desserter.controllers;

import com.mirea.desserter.repos.IBasketRepo;
import com.mirea.desserter.repos.IProductRepo;
import com.mirea.desserter.repos.ITypeRepo;
import com.mirea.desserter.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private IProductRepo productRepo;

    @Autowired
    private ITypeRepo typeRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private IBasketRepo basketRepo;

    
}
