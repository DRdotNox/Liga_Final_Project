package com.liga.carwash.repo;

import com.liga.carwash.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Long> {
}
