package com.example.EmployeeManagementSystem.dto;
ublic class EmployeeDTO {
    private Long id;
    private String firstName;
    private String lastName;

    public EmployeeDTO(Long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // Getters and Setters
    // ...
}