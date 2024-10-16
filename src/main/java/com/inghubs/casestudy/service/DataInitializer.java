package com.inghubs.casestudy.service;

import com.inghubs.casestudy.model.Customer;
import com.inghubs.casestudy.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * This will create an admin user and a customer user when the application starts.
 */

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Create admin user
        Customer admin = new Customer();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setAdmin(true);
        customerRepository.save(admin);

        // Create customer user
        Customer customer = new Customer();
        customer.setUsername("customer");
        customer.setPassword(passwordEncoder.encode("customer123"));
        customer.setAdmin(false);
        customerRepository.save(customer);
    }
}

