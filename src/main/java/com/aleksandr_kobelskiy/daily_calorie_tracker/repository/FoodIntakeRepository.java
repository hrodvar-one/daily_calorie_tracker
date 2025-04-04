package com.aleksandr_kobelskiy.daily_calorie_tracker.repository;

import com.aleksandr_kobelskiy.daily_calorie_tracker.entity.FoodIntakeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FoodIntakeRepository extends JpaRepository<FoodIntakeEntity, Long> {
    List<FoodIntakeEntity> findByUserIdAndIntakeTimeBetween(Long userId, LocalDateTime start, LocalDateTime end);
    List<FoodIntakeEntity> findByUserIdOrderByIntakeTimeDesc(Long userId);
}
