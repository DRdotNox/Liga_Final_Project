package com.liga.carwash.repo;

import com.liga.carwash.model.Box;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoxRepo extends JpaRepository<Box,Long> {
}
