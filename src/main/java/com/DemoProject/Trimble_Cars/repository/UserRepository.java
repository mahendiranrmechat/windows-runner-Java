package com.DemoProject.Trimble_Cars.repository;

import com.DemoProject.Trimble_Cars.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}