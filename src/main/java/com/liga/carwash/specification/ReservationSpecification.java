package com.liga.carwash.specification;

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
public class ReservationSpecification implements Specification<Reservation> {
    private SearchCriteria searchCriteria;

    @Override
    public Predicate toPredicate(Root<Reservation> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        List<Predicate> predicates = new ArrayList<>();

        if(searchCriteria.getBox()!=null) predicates.add(builder.equal(root.get("box"), searchCriteria.getBox()));
        if(searchCriteria.getInTime()!=null) predicates.add(builder.equal(root.get("inTime"), searchCriteria.getInTime()));
        if(searchCriteria.getDate()!=null)predicates.add(builder.equal(root.get("date"), searchCriteria.getDate()));
        if(searchCriteria.getTimeStart()!=null){
            predicates.add(builder.greaterThanOrEqualTo(root.get("timeStart"), searchCriteria.getTimeStart()));
            predicates.add(builder.greaterThanOrEqualTo(root.get("timeEnd"), searchCriteria.getTimeStart()));
        }
        if(searchCriteria.getTimeEnd()!=null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("timeStart"), searchCriteria.getTimeEnd()));
            predicates.add(builder.lessThanOrEqualTo(root.get("timeEnd"), searchCriteria.getTimeEnd()));
        }

        return builder.and(predicates.toArray(new Predicate[0]));
    }
}
