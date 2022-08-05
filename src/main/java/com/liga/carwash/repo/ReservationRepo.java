package com.liga.carwash.repo;

import com.liga.carwash.model.Box;
import com.liga.carwash.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepo extends JpaRepository<Reservation,Long> {
    List<Reservation> findAllByBox(Box box);
}
