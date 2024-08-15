package com.example.employeemanagementsystem.repository;

import com.example.employeemanagementsystem.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    // Derived query method to find a department by name
    Optional<Department> findByName(String name);
}
