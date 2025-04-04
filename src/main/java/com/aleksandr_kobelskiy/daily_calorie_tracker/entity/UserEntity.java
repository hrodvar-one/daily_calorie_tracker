package com.aleksandr_kobelskiy.daily_calorie_tracker.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Setter
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(unique = true, nullable = false, length = 100)
    private String email;

    @Column(nullable = false)
    private Integer age;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
    private Double weight;

    @Column(nullable = false)
    private Integer height;

    @Column(nullable = false)
    private String purpose;

    @Column(name = "daily_calorie_norm")
    private Integer dailyCalorieNorm;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public void calculateDailyCalorieNorm() {
        double bmr;
        if (gender.equalsIgnoreCase("MALE")) {
            bmr = 88.36 + (13.4 * weight) + (4.8 * height) - (5.7 * age);
        } else {
            bmr = 447.6 + (9.2 * weight) + (3.1 * height) - (4.3 * age);
        }

        switch (purpose.toUpperCase()) {
            case "LOSE_WEIGHT" -> bmr *= 0.85;
            case "GAIN_WEIGHT" -> bmr *= 1.15;
            default -> {}
        }

        this.dailyCalorieNorm = (int) bmr;
    }
}
