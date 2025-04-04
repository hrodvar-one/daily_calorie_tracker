package com.aleksandr_kobelskiy.daily_calorie_tracker.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "food_intakes")
@Getter
@Setter
public class FoodIntakeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "intake_time", nullable = false)
    private LocalDateTime intakeTime;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "foodIntake", cascade = CascadeType.ALL)
    private List<FoodIntakeMealEntity> meals;
}
