package com.liga.carwash.repo;

import com.liga.carwash.model.Box;
import com.liga.carwash.model.Reservation;
import com.liga.carwash.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ReservationRepo extends JpaRepository<Reservation,Long>, JpaSpecificationExecutor<Reservation> {
    List<Reservation> findAllByBox(Box box);
    List<Reservation> findAllByUser(User user);
    List<Reservation> findAllByInTime(Boolean inTime);
}
