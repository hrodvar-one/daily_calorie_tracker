package com.aleksandr_kobelskiy.daily_calorie_tracker.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class FoodIntakeResponse {

    private Long id;
    private Long userId;
    private LocalDateTime intakeTime;
    private List<MealEntry> meals;

    @Data
    public static class MealEntry {
        private String mealName;
        private Double portion;
        private Double caloriesPerPortion;
        private Double totalCalories;
    }
}
