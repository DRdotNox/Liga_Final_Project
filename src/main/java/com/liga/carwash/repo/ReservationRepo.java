package com.liga.carwash.repo;

import com.liga.carwash.model.Box;
import com.liga.carwash.model.Reservation;
import com.liga.carwash.model.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ReservationRepo extends JpaRepository<Reservation,Long>, JpaSpecificationExecutor<Reservation> {
    List<Reservation> findAllByBox(Box box);
    List<Reservation> findAllByInTime(Boolean inTime);
}
