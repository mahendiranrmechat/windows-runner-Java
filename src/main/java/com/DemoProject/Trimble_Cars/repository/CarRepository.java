package com.DemoProject.Trimble_Cars.repository;

import com.DemoProject.Trimble_Cars.model.Car;
import com.DemoProject.Trimble_Cars.model.CarStatus;
import com.DemoProject.Trimble_Cars.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findByOwner(User owner);
    List<Car> findByStatus(CarStatus status);
}

