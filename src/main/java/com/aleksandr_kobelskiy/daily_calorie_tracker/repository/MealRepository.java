package com.aleksandr_kobelskiy.daily_calorie_tracker.repository;

import com.aleksandr_kobelskiy.daily_calorie_tracker.entity.MealEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MealRepository extends JpaRepository<MealEntity, Long> {
}
