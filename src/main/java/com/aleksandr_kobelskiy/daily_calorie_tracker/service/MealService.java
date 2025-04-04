package com.aleksandr_kobelskiy.daily_calorie_tracker.service;

import com.aleksandr_kobelskiy.daily_calorie_tracker.dto.CreateMealRequest;
import com.aleksandr_kobelskiy.daily_calorie_tracker.entity.MealEntity;
import com.aleksandr_kobelskiy.daily_calorie_tracker.repository.MealRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MealService {

    private final MealRepository mealRepository;

    public MealEntity createMeal(CreateMealRequest request) {
        MealEntity meal = new MealEntity();
        meal.setName(request.getName());
        meal.setCalories(request.getCalories());
        meal.setProteins(request.getProteins());
        meal.setFats(request.getFats());
        meal.setCarbohydrates(request.getCarbohydrates());
        meal.setCreatedAt(LocalDateTime.now());
        meal.setUpdatedAt(LocalDateTime.now());
        return mealRepository.save(meal);
    }
}
