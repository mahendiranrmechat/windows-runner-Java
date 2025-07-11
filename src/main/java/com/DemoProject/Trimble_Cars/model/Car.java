package com.DemoProject.Trimble_Cars.model;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "car")
public class Car implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "MODEL")
    private String model;

    @Column(name = "REGISTRATION_NUMBER")
    private String registrationNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "CAR_STATUS")
    private CarStatus status;

    @ManyToOne
    @JoinColumn(name = "OWNER_ID")
    private User owner;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public CarStatus getStatus() {
        return status;
    }

    public void setStatus(CarStatus status) {
        this.status = status;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
