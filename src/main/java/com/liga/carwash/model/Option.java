package com.liga.carwash.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "options")
public class Option {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private int price;
    @Column(name = "time")
    private int time;

    @Column(name = "discount")
    private Integer discount;
    public static Integer getFullPriceWithDiscount(Option option) {
        if (option.getDiscount() == 0) return option.getPrice();
        else return option.getPrice() - option.getPrice() * option.getDiscount() / 100;
    }

}
