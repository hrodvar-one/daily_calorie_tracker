package com.aleksandr_kobelskiy.daily_calorie_tracker.dto;

import lombok.Data;

import java.util.List;

@Data
public class DailyReportResponse {
    private String date;
    private List<MealInfo> meals;
    private double totalCalories;
    private int dailyNorm;
    private boolean withinNorm;

    @Data
    public static class MealInfo {
        private String name;
        private double portion;
        private double calories;
    }
}
