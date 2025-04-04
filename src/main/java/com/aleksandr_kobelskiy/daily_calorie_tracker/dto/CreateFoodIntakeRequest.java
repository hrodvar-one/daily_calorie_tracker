package com.aleksandr_kobelskiy.daily_calorie_tracker.dto;

import jakarta.validation.Valid;
import lombok.Data;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CreateFoodIntakeRequest {

    @NotNull
    private Long userId;

    @NotNull
    private LocalDateTime intakeTime;

    @NotEmpty
    private List<@Valid MealPortion> meals;

    @Data
    public static class MealPortion {

        @NotNull
        private Long mealId;

        @NotNull
        @DecimalMin("0.1")
        private Double portion;
    }
}
