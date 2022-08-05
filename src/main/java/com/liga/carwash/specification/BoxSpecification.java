//package com.liga.carwash.specification;
//
//import com.liga.carwash.model.Box;
//import org.springframework.data.jpa.domain.Specification;
//
//import javax.persistence.criteria.CriteriaBuilder;
//import javax.persistence.criteria.CriteriaQuery;
//import javax.persistence.criteria.Predicate;
//import javax.persistence.criteria.Root;
//
//public class BoxSpecification implements Specification<Box> {
//    @Override
//    public Predicate toPredicate(Root<Box> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
//        query.orderBy(criteriaBuilder.asc(root.get("coef")));
//        return criteriaBuilder.all(root);
////    }
//
