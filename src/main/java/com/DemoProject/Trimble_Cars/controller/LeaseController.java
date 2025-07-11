package com.DemoProject.Trimble_Cars.controller;

import com.DemoProject.Trimble_Cars.model.Car;
import com.DemoProject.Trimble_Cars.model.Lease;
import com.DemoProject.Trimble_Cars.model.User;
import com.DemoProject.Trimble_Cars.services.CarService;
import com.DemoProject.Trimble_Cars.services.LeaseService;
import com.DemoProject.Trimble_Cars.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/leases")
public class LeaseController {

    private static final Logger logger = LoggerFactory.getLogger(LeaseController.class);

    @Autowired
    private LeaseService leaseService;
    @Autowired
    private CarService carService;
    @Autowired
    private UserService userService;

    @PostMapping("/start")
    public ResponseEntity<Lease> startLease(@RequestParam Long carId, @RequestParam Long customerId) {
        logger.info("Starting lease for car ID: {} by customer ID: {}", carId, customerId);
        Car car = carService.getCarById(carId).orElseThrow();
        User customer = userService.getUserById(customerId).orElseThrow();
        return ResponseEntity.ok(leaseService.startLease(car, customer));
    }

    @PostMapping("/end/{leaseId}")
    public ResponseEntity<Lease> endLease(@PathVariable Long leaseId) {
        logger.info("Ending lease with ID: {}", leaseId);
        Lease lease = leaseService.getAllLeases().stream()
                .filter(l -> l.getId().equals(leaseId))
                .findFirst()
                .orElseThrow();
        return ResponseEntity.ok(leaseService.endLease(lease));
    }

    @GetMapping("/history/{customerId}")
    public ResponseEntity<List<Lease>> getHistory(@PathVariable Long customerId) {
        logger.info("Fetching lease history for customer ID: {}", customerId);
        User customer = userService.getUserById(customerId).orElseThrow();
        return ResponseEntity.ok(leaseService.getLeaseHistory(customer));
    }
}
