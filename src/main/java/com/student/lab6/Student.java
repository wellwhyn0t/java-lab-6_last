package com.student.lab6;

import java.time.LocalDate;

/**
 * Model class representing a Student entity.
 * Variant 18: Students with fields - ID, Name, Group, Rating, Date of Birth
 */
public class Student {
    
    private int id;
    private String name;
    private String group;
    private double rating;
    private LocalDate birthDate;
    
    // Default constructor required for JavaFX
    public Student() {
    }
    
    // Full constructor
    public Student(int id, String name, String group, double rating, LocalDate birthDate) {
        this.id = id;
        this.name = name;
        this.group = group;
        this.rating = rating;
        this.birthDate = birthDate;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getGroup() {
        return group;
    }
    
    public void setGroup(String group) {
        this.group = group;
    }
    
    public double getRating() {
        return rating;
    }
    
    public void setRating(double rating) {
        this.rating = rating;
    }
    
    public LocalDate getBirthDate() {
        return birthDate;
    }
    
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
    
    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", group='" + group + '\'' +
                ", rating=" + rating +
                ", birthDate=" + birthDate +
                '}';
    }
}
