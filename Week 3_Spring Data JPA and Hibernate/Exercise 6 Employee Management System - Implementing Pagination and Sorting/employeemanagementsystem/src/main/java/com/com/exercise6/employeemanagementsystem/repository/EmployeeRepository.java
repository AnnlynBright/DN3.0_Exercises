package com.com.exercise6.employeemanagementsystem.repository;


import com.com.exercise6.employeemanagementsystem.entity.Employee;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Long> {
    // Custom query methods can be defined here
}