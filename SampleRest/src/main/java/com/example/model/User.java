package com.example.model;


import lombok.Data;

@Data  // Lombok generates getters/setters automatically
public class User {
    private String name;
    private int age;
}
