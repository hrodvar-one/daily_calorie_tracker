package com.aleksandr_kobelskiy.daily_calorie_tracker.dto;
import jakarta.validation.constraints.*;

import lombok.Data;

@Data
public class CreateUserRequest {

    @NotBlank
    @Size(max = 100)
    private String name;

    @NotBlank
    @Email
    private String email;

    @NotNull
    @Min(10)
    @Max(120)
    private Integer age;

    @NotBlank
    @Pattern(regexp = "MALE|FEMALE")
    private String gender;

    @NotNull
    @DecimalMin("30.0")
    @DecimalMax("300.0")
    private Double weight;

    @NotNull
    @Min(100)
    @Max(250)
    private Integer height;

    @NotBlank
    @Pattern(regexp = "LOSE_WEIGHT|MAINTAIN|GAIN_WEIGHT")
    private String purpose;
}
