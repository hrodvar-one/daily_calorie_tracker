package com.aleksandr_kobelskiy.daily_calorie_tracker.rest;

import com.aleksandr_kobelskiy.daily_calorie_tracker.dto.CreateFoodIntakeRequest;
import com.aleksandr_kobelskiy.daily_calorie_tracker.dto.FoodIntakeResponse;
import com.aleksandr_kobelskiy.daily_calorie_tracker.entity.FoodIntakeEntity;
import com.aleksandr_kobelskiy.daily_calorie_tracker.service.FoodIntakeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/food-intakes")
@RequiredArgsConstructor
public class FoodIntakeRestController {

    private final FoodIntakeService foodIntakeService;

    @PostMapping
    public ResponseEntity<FoodIntakeResponse> createIntake(@RequestBody @Valid CreateFoodIntakeRequest request) {
        FoodIntakeEntity intake = foodIntakeService.createFoodIntake(request);
        FoodIntakeResponse response = foodIntakeService.toResponse(intake);
        return ResponseEntity.ok(response);
    }
}
