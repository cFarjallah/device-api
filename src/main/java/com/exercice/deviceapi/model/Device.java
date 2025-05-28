package com.exercice.deviceapi.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * Entity representing a device with name, brand, state, and creation timestamp.
 */
@Entity
public class Device {
    /**
     * Unique identifier for the device.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String brand;
    /**
     * Current state of the device (e.g., IN_USE, AVAILABLE).
     */
    @Enumerated(EnumType.STRING)
    private State state;
    /**
     * Timestamp when the device was created.
     * This field is set automatically on persist and is not updatable.
     */
    @Column(updatable = false)
    private LocalDateTime creationTime;

    protected Device() {

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

    /**
     * Callback method invoked automatically before the entity is persisted.
     * Sets the creationTime field to the current time.
     */
    @PrePersist
    private void onCreate() {
        creationTime = LocalDateTime.now();
    }
}
