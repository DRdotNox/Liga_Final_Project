package com.liga.carwash.specification;

import com.liga.carwash.enums.ReservationStatus;
import com.liga.carwash.model.Reservation;
import lombok.*;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class IncomeSpecification implements Specification<Reservation> {

    private IncomeCriteria incomeCriteria;

    @Override
    public Predicate toPredicate(Root<Reservation> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(builder.equal(root.get("status"), ReservationStatus.FINISHED));

        if(incomeCriteria.getBox()!=null) predicates.add(builder.equal(root.get("box"), incomeCriteria.getBox()));
        if(incomeCriteria.getDateFrom()!=null)predicates.add(builder.greaterThanOrEqualTo(root.get("date"), incomeCriteria.getDateFrom()));
        if(incomeCriteria.getDateTo()!=null)predicates.add(builder.lessThanOrEqualTo(root.get("date"), incomeCriteria.getDateTo()));

        if(incomeCriteria.getTimeStart()!=null) predicates.add(builder.greaterThanOrEqualTo(root.get("timeStart"), incomeCriteria.getTimeStart()));
        if(incomeCriteria.getTimeEnd()!=null)    predicates.add(builder.lessThanOrEqualTo(root.get("timeEnd"), incomeCriteria.getTimeEnd()));


        return builder.and(predicates.toArray(new Predicate[0]));
    }
}
