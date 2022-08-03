package com.liga.carwash.repo;

import com.liga.carwash.model.Option;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionRepo extends JpaRepository<Option,Long> {
}
