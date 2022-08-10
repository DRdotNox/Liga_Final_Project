package com.liga.carwash.repo;

import com.liga.carwash.model.Operator;
import com.liga.carwash.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperatorRepo extends JpaRepository<Operator, Long> {
    Operator findByUser(User user);
}
