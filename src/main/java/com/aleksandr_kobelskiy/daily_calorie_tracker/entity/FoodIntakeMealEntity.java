package com.aleksandr_kobelskiy.daily_calorie_tracker.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "food_intake_meals")
@Getter
@Setter
public class FoodIntakeMealEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "food_intake_id")
    private FoodIntakeEntity foodIntake;

    @ManyToOne(optional = false)
    @JoinColumn(name = "meal_id")
    private MealEntity meal;

    @Column(nullable = false)
    private Double portion;
}
