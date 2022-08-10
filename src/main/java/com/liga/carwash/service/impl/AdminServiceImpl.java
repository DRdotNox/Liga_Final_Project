package com.liga.carwash.service.impl;

import com.liga.carwash.enums.RoleType;
import com.liga.carwash.model.Box;
import com.liga.carwash.model.DTO.OperatorDiscountDTO;
import com.liga.carwash.model.Operator;
import com.liga.carwash.model.User;
import com.liga.carwash.repo.OperatorRepo;
import com.liga.carwash.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final OperatorRepo operatorRepo;

    @Override
    @Transactional
    public void setOperator(User user, Box box) {

        Operator operator = operatorRepo.findByUser(user);
        if (operator != null) {
            operator.setBox(box);
        } else {
            user.setRole(RoleType.ROLE_OPERATOR);
            operator = Operator.builder()
                    .box(box)
                    .user(user)
                    .build();
        }
        operatorRepo.save(operator);
    }

    @Override
    @Transactional
    public void changeDiscount(Long id, OperatorDiscountDTO operatorDiscountDTO) {
        Operator operator = getOperator(id);
        if (operatorDiscountDTO.getDiscountMin() != null) operator.setDiscountMin(operatorDiscountDTO.getDiscountMin());
        if (operatorDiscountDTO.getDiscountMax() != null) operator.setDiscountMax(operatorDiscountDTO.getDiscountMax());
        operatorRepo.save(operator);
    }

    @Override
    @Transactional
    public Operator getOperator(Long id) {
        return operatorRepo.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
