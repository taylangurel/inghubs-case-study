package com.inghubs.casestudy;

import com.inghubs.casestudy.model.Customer;
import com.inghubs.casestudy.repository.CustomerRepository;
import com.inghubs.casestudy.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerCustomer_shouldRegisterCustomer_whenUsernameIsUnique() {
        Customer customer = new Customer();
        customer.setUsername("testuser");
        customer.setPassword("password123");

        when(customerRepository.findByUsername("testuser")).thenReturn(null);
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");

        String result = customerService.registerCustomer(customer);

        assertEquals("Registration successful", result);
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    void registerCustomer_shouldThrowException_whenUsernameIsTaken() {
        Customer customer = new Customer();
        customer.setUsername("testuser");

        when(customerRepository.findByUsername("testuser")).thenReturn(customer);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            customerService.registerCustomer(customer);
        });

        assertEquals("Username already taken", exception.getMessage());
        verify(customerRepository, never()).save(any(Customer.class));
    }
}

