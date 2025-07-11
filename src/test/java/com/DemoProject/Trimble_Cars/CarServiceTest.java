package com.DemoProject.Trimble_Cars;

import com.DemoProject.Trimble_Cars.model.Car;
import com.DemoProject.Trimble_Cars.model.CarStatus;
import com.DemoProject.Trimble_Cars.model.User;
import com.DemoProject.Trimble_Cars.repository.CarRepository;
import com.DemoProject.Trimble_Cars.services.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CarServiceTest {

    @InjectMocks
    private CarService carService;

    @Mock
    private CarRepository carRepository;

    private Car car;
    private User owner;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        owner = new User();
        owner.setId(1L);
        owner.setUsername("Jagathish");
        owner.setPassword("jags@123");

        car = new Car();
        car.setId(100L);
        car.setModel("Hyundai i20");
        car.setRegistrationNumber("TN01AB1234");
        car.setStatus(CarStatus.ON_SERVICE);
        car.setOwner(owner);
    }

    @Test
    void testRegisterCar() {
        when(carRepository.save(car)).thenReturn(car);
        Car result = carService.registerCar(car);
        assertThat(result).isEqualTo(car);
        verify(carRepository, times(1)).save(car);
    }

    @Test
    void testGetCarsByOwner() {
        when(carRepository.findByOwner(owner)).thenReturn(List.of(car));
        List<Car> result = carService.getCarsByOwner(owner);
        assertThat(result).hasSize(1).contains(car);
        verify(carRepository).findByOwner(owner);
    }

    @Test
    void testGetCarsByStatus() {
        when(carRepository.findByStatus(CarStatus.IDLE)).thenReturn(List.of(car));
        List<Car> result = carService.getCarsByStatus(CarStatus.IDLE);
        assertThat(result).hasSize(1).contains(car);
        verify(carRepository).findByStatus(CarStatus.IDLE);
    }

    @Test
    void testGetCarById() {
        when(carRepository.findById(100L)).thenReturn(Optional.of(car));
        Optional<Car> result = carService.getCarById(100L);
        assertThat(result).isPresent().contains(car);
        verify(carRepository).findById(100L);
    }

    @Test
    void testUpdateCarStatus() {
        Car car = new Car();
        car.setId(1L);
        car.setStatus(CarStatus.IDLE);

        when(carRepository.findById(1L)).thenReturn(Optional.of(car));
        when(carRepository.save(any(Car.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Car updatedCar = carService.updateCarStatus(1L, CarStatus.ON_LEASE);

        assertEquals(CarStatus.ON_LEASE, updatedCar.getStatus());
        verify(carRepository, times(1)).save(car);
    }

}
