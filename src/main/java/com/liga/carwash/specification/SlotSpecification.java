package com.liga.carwash.specification;

import com.liga.carwash.model.Slot;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SlotSpecification implements Specification<Slot> {

    private final String STATUS_FIELD = "status";
    private SearchCriteria searchCriteria;

    @Nullable
    @Override
    public Predicate toPredicate(Root<Slot> root, CriteriaQuery<?> query,
                                 CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();

       // predicates.add(builder.equal(root.get("box"), searchCriteria.getBox()));

        predicates.add(builder.equal(root.get("status"), searchCriteria.getStatus()));

        predicates.add(builder.equal(root.get("date"), searchCriteria.getDate()));

        if (searchCriteria.getTimeStart() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("timeStart"), searchCriteria.getTimeStart()));
        }
//        if (searchCriteria.getTimeEnd() != null) {
//            predicates.add(builder.lessThanOrEqualTo(root.get("timeStart"), searchCriteria.getTimeEnd()));
//        }
        query.orderBy(builder.asc(root.get("timeStart")));
        predicates.forEach(predicate -> System.out.println(predicate.toString()));
        return builder.and(predicates.toArray(new Predicate[0]));
    }

}