package com.DemoProject.Trimble_Cars.services;

import com.DemoProject.Trimble_Cars.model.Car;
import com.DemoProject.Trimble_Cars.model.CarStatus;
import com.DemoProject.Trimble_Cars.model.Lease;
import com.DemoProject.Trimble_Cars.model.User;
import com.DemoProject.Trimble_Cars.repository.LeaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LeaseService {

    @Autowired
    private LeaseRepository leaseRepository;
    @Autowired
    private CarService carService;

    public List<Lease> getAllLeases() {
        return leaseRepository.findAll();
    }


    public Lease startLease(Car car, User customer) {
        if (leaseRepository.findByCustomer(customer).size() >= 2) {
            throw new RuntimeException("Maximum 2 leases allowed");
        }
        carService.updateCarStatus(car.getId(), CarStatus.ON_LEASE);

        Lease lease = new Lease();
        lease.setCar(car);
        lease.setCustomer(customer);
        lease.setStartDate(LocalDateTime.now());
        return leaseRepository.save(lease);
    }

    public Lease endLease(Lease lease) {
        lease.setEndDate(LocalDateTime.now());
        carService.updateCarStatus(lease.getCar().getId(), CarStatus.IDLE);
        return leaseRepository.save(lease);
    }

    public List<Lease> getLeaseHistory(User customer) {
        return leaseRepository.findByCustomer(customer);
    }
}
