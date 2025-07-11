package com.DemoProject.Trimble_Cars.repository;

import com.DemoProject.Trimble_Cars.model.Lease;
import com.DemoProject.Trimble_Cars.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaseRepository extends JpaRepository<Lease, Long> {
    List<Lease> findByCustomer(User customer);
}