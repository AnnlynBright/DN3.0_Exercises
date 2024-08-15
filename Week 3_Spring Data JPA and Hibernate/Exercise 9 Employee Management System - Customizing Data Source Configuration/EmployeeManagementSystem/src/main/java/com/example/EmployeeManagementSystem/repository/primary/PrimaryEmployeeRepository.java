package com.example.EmployeeManagementSystem.repository.primary;

import com.example.EmployeeManagementSystem.entity.secondary.SecondaryEmployee;
import com.example.employeemanagementsystem.entity.secondary.SecondaryEmployee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecondaryEmployeeRepository extends JpaRepository<SecondaryEmployee, Long> {
}