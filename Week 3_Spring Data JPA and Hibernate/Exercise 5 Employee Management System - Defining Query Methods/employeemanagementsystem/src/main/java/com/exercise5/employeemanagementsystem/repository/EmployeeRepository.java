package com.exercise5.employeemanagementsystem.repository;

import com.exercise5.employeemanagementsystem.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // Custom Query Methods Using Keywords
    List<Employee> findByLastName(String lastName);

    List<Employee> findByDepartment(String department);

    // Custom Query Methods Using @Query Annotation
    @Query("SELECT e FROM Employee e WHERE e.firstName = :firstName AND e.lastName = :lastName")
    List<Employee> findByFirstAndLastName(@Param("firstName") String firstName, @Param("lastName") String lastName);

    @Query("SELECT e FROM Employee e WHERE e.department = :department")
    List<Employee> findEmployeesByDepartment(@Param("department") String department);
}
