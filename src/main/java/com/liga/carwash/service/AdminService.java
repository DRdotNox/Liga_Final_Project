package com.liga.carwash.service;

import com.liga.carwash.model.Box;
import com.liga.carwash.model.DTO.OperatorDiscountDTO;
import com.liga.carwash.model.Operator;
import com.liga.carwash.model.User;


public interface AdminService {
    void setOperator(User user, Box box);

    void changeDiscount(Long id, OperatorDiscountDTO operatorDiscountDTO);

    Operator getOperator(Long id);
}
