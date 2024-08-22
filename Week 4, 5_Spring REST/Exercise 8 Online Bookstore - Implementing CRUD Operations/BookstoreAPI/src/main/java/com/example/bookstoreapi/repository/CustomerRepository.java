package com.example.bookstoreapi.repository;

import com.example.bookstoreapi.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
