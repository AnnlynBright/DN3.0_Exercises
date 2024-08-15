package com.example.EmployeeManagementSystem.entity.secondary;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;



@Entity
public class SecondaryEmployee {

    @Id
    private Long id;
    private String name;

    // Getters and setters
}
