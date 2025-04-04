package com.aleksandr_kobelskiy.daily_calorie_tracker.dto;
import jakarta.validation.constraints.*;

import lombok.Data;

@Data
public class CreateMealRequest {

    @NotBlank
    @Size(max = 100)
    private String name;

    @NotNull
    @DecimalMin("1.0")
    private Double calories;

    @NotNull
    @DecimalMin("0.0")
    private Double proteins;

    @NotNull
    @DecimalMin("0.0")
    private Double fats;

    @NotNull
    @DecimalMin("0.0")
    private Double carbohydrates;
}
