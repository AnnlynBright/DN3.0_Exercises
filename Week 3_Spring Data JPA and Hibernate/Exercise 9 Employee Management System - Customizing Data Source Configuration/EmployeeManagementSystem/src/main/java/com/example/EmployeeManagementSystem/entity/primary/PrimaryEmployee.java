package com.example.EmployeeManagementSystem.entity.primary;
import jakarta.persistence.Entity;
import org.springframework.data.annotation.Id;


@Entity
public class PrimaryEmployee {

    @Id
    private Long id;
    private String name;

    // Getters and setters
}