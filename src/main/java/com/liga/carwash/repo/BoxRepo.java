package com.liga.carwash.repo;

import com.liga.carwash.model.Box;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BoxRepo extends JpaRepository<Box, Long> {
}
