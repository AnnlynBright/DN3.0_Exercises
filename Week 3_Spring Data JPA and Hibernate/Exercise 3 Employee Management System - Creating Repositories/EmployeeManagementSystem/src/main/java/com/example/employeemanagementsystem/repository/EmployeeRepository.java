package com.example.employeemanagementsystem.repository;

import com.example.employeemanagementsystem.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // Derived query method to find employees by name
    List<Employee> findByName(String name);

    // Derived query method to find employees by department id
    List<Employee> findByDepartmentId(Long departmentId);
}
