package com.exercice.deviceapi.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
@Entity
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String brand;
    @Enumerated(EnumType.STRING)
    private State state;
    @Column(updatable = false)
    private LocalDateTime creationTime;

    protected Device(){

    }
    public Device(String name, String brand, State state) {
        this.name = name;
        this.brand = brand;
        this.state = state;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    @PrePersist
    private void onCreate() {
        creationTime = LocalDateTime.now();
    }
}
