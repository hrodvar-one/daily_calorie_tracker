package com.aleksandr_kobelskiy.daily_calorie_tracker.dto;

import lombok.Data;

import java.util.List;

@Data
public class FoodHistoryResponse {
    private List<DayRecord> days;

    @Data
    public static class DayRecord {
        private String date;
        private List<MealEntry> meals;
    }

    @Data
    public static class MealEntry {
        private String mealName;
        private double portion;
        private double calories;
        private String time;
    }
}
