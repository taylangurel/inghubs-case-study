package com.inghubs.casestudy.controller;

import com.inghubs.casestudy.model.Customer;
import com.inghubs.casestudy.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class CustomerRegistrationController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/register")
    public String registerCustomer(@RequestBody Customer customer) {
        return customerService.registerCustomer(customer);
    }
}

