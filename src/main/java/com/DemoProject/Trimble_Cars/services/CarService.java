package com.DemoProject.Trimble_Cars.services;

import com.DemoProject.Trimble_Cars.model.Car;
import com.DemoProject.Trimble_Cars.model.CarStatus;
import com.DemoProject.Trimble_Cars.model.User;
import com.DemoProject.Trimble_Cars.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    public Car registerCar(Car car) {
        return carRepository.save(car);
    }

    public List<Car> getCarsByOwner(User owner) {
        return carRepository.findByOwner(owner);
    }

    public List<Car> getCarsByStatus(CarStatus status) {
        return carRepository.findByStatus(status);
    }

    public Optional<Car> getCarById(Long id) {
        return carRepository.findById(id);
    }

    public Car updateCarStatus(Long carId, CarStatus status) {
        Car car = carRepository.findById(carId).orElseThrow();
        car.setStatus(status);
        return carRepository.save(car);
    }
}