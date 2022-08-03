package com.liga.carwash.repo;
import com.liga.carwash.model.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SlotsRepo extends JpaRepository<Slot,Long>, JpaSpecificationExecutor<Slot> {
    List<Slot> findSlotsByDate(LocalDate date);
}
