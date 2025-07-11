package com.DemoProject.Trimble_Cars.controller;

import com.DemoProject.Trimble_Cars.model.Car;
import com.DemoProject.Trimble_Cars.model.CarStatus;
import com.DemoProject.Trimble_Cars.model.User;
import com.DemoProject.Trimble_Cars.services.CarService;
import com.DemoProject.Trimble_Cars.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarController {

    private static final Logger logger = LoggerFactory.getLogger(CarController.class);

    @Autowired
    private CarService carService;
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Car> registerCar(@RequestBody Car car) {
        logger.info("Registering car: {}", car.getRegistrationNumber());
        return ResponseEntity.ok(carService.registerCar(car));
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<Car>> getByOwner(@PathVariable Long ownerId) {
        logger.info("Fetching cars for owner ID: {}", ownerId);
        User owner = userService.getUserById(ownerId).orElseThrow();
        return ResponseEntity.ok(carService.getCarsByOwner(owner));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Car>> getByStatus(@PathVariable CarStatus status) {
        logger.info("Fetching cars by status: {}", status);
        return ResponseEntity.ok(carService.getCarsByStatus(status));
    }

    @PutMapping("/{carId}/status")
    public ResponseEntity<Car> updateStatus(@PathVariable Long carId, @RequestParam CarStatus status) {
        logger.info("Updating car ID: {} to status: {}", carId, status);
        return ResponseEntity.ok(carService.updateCarStatus(carId, status));
    }
}