package com.liga.carwash.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "operators")
public class Operator {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "disc_min")
    private int discountMin;

    @Column(name = "disc_max")
    private int discountMax;

    @ManyToOne
    @JoinColumn(name = "box_id", referencedColumnName = "id")
    @NotNull
    private Box box;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @NotNull
    private User user;
}
