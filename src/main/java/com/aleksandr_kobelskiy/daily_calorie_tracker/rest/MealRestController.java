package com.aleksandr_kobelskiy.daily_calorie_tracker.rest;

import com.aleksandr_kobelskiy.daily_calorie_tracker.dto.CreateMealRequest;
import com.aleksandr_kobelskiy.daily_calorie_tracker.entity.MealEntity;
import com.aleksandr_kobelskiy.daily_calorie_tracker.service.MealService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/meals")
@RequiredArgsConstructor
public class MealRestController {

    private final MealService mealService;

    @PostMapping
    public ResponseEntity<MealEntity> createMeal(@RequestBody @Valid CreateMealRequest request) {
        MealEntity meal = mealService.createMeal(request);
        return ResponseEntity.ok(meal);
    }
}
