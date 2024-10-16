package com.inghubs.casestudy.service;

import com.inghubs.casestudy.model.Customer;
import com.inghubs.casestudy.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder; // * This is the same bean defined in SecurityConfig to ensure consistency

    public String registerCustomer(Customer customer) {
        // Check if the username already exists
        if (customerRepository.findByUsername(customer.getUsername()) != null) {
            throw new RuntimeException("Username already taken");
        }

        // Encrypt the password before saving
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customer.setAdmin(false); // Newly registered users are not admins by default

        customerRepository.save(customer);
        return "Registration successful";
    }
}
