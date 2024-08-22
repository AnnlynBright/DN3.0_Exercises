package com.example.bookstoreapi.controller;

import com.example.bookstoreapi.entity.Customer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private List<Customer> customers = new ArrayList<>();

    // POST method to create a new customer using JSON request body
    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        customer.setId((long) (customers.size() + 1)); // Simple id assignment logic
        customers.add(customer);
        return new ResponseEntity<>(customer, HttpStatus.CREATED);
    }


    // POST method to process form data for customer registration
    @PostMapping("/register")
    public ResponseEntity<Customer> registerCustomer(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String email,
            @RequestParam String password) {

        Customer customer = new Customer((long) (customers.size() + 1), firstName, lastName, email, password);
        customers.add(customer);
        return new ResponseEntity<>(customer, HttpStatus.CREATED);
    }

}

