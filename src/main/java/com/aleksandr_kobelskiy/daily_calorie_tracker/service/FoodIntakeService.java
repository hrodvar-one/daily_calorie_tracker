package com.aleksandr_kobelskiy.daily_calorie_tracker.service;

import com.aleksandr_kobelskiy.daily_calorie_tracker.dto.CreateFoodIntakeRequest;
import com.aleksandr_kobelskiy.daily_calorie_tracker.dto.DailyReportResponse;
import com.aleksandr_kobelskiy.daily_calorie_tracker.dto.FoodHistoryResponse;
import com.aleksandr_kobelskiy.daily_calorie_tracker.dto.FoodIntakeResponse;
import com.aleksandr_kobelskiy.daily_calorie_tracker.entity.FoodIntakeEntity;
import com.aleksandr_kobelskiy.daily_calorie_tracker.entity.FoodIntakeMealEntity;
import com.aleksandr_kobelskiy.daily_calorie_tracker.entity.MealEntity;
import com.aleksandr_kobelskiy.daily_calorie_tracker.entity.UserEntity;
import com.aleksandr_kobelskiy.daily_calorie_tracker.repository.FoodIntakeRepository;
import com.aleksandr_kobelskiy.daily_calorie_tracker.repository.MealRepository;
import com.aleksandr_kobelskiy.daily_calorie_tracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FoodIntakeService {

    private final FoodIntakeRepository foodIntakeRepository;
    private final UserRepository userRepository;
    private final MealRepository mealRepository;

    public FoodIntakeEntity createFoodIntake(CreateFoodIntakeRequest request) {
        UserEntity user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        FoodIntakeEntity intake = new FoodIntakeEntity();
        intake.setUser(user);
        intake.setIntakeTime(request.getIntakeTime());
        intake.setCreatedAt(LocalDateTime.now());
        intake.setUpdatedAt(LocalDateTime.now());

        List<FoodIntakeMealEntity> mealEntities = request.getMeals().stream().map(mp -> {
            MealEntity meal = mealRepository.findById(mp.getMealId())
                    .orElseThrow(() -> new IllegalArgumentException("Meal not found"));

            FoodIntakeMealEntity fim = new FoodIntakeMealEntity();
            fim.setMeal(meal);
            fim.setPortion(mp.getPortion());
            fim.setFoodIntake(intake);
            return fim;
        }).toList();

        intake.setMeals(mealEntities);
        return foodIntakeRepository.save(intake);
    }

    public DailyReportResponse getDailyReport(Long userId, LocalDate date) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.atTime(LocalTime.MAX);

        List<FoodIntakeEntity> intakes = foodIntakeRepository.findByUserIdAndIntakeTimeBetween(userId, start, end);

        double totalCalories = 0;
        List<DailyReportResponse.MealInfo> mealInfos = new ArrayList<>();

        for (FoodIntakeEntity intake : intakes) {
            for (FoodIntakeMealEntity fim : intake.getMeals()) {
                double calories = fim.getMeal().getCalories() * fim.getPortion();
                totalCalories += calories;

                DailyReportResponse.MealInfo info = new DailyReportResponse.MealInfo();
                info.setName(fim.getMeal().getName());
                info.setPortion(fim.getPortion());
                info.setCalories(calories);

                mealInfos.add(info);
            }
        }

        DailyReportResponse report = new DailyReportResponse();
        report.setDate(date.toString());
        report.setMeals(mealInfos);
        report.setTotalCalories(totalCalories);
        report.setDailyNorm(user.getDailyCalorieNorm());
        report.setWithinNorm(totalCalories <= user.getDailyCalorieNorm());

        return report;
    }

    public FoodHistoryResponse getFoodHistory(Long userId) {
        userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));

        List<FoodIntakeEntity> intakes = foodIntakeRepository.findByUserIdOrderByIntakeTimeDesc(userId);

        Map<String, List<FoodHistoryResponse.MealEntry>> grouped = new LinkedHashMap<>();

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        for (FoodIntakeEntity intake : intakes) {
            String date = intake.getIntakeTime().format(dateFormatter);

            for (FoodIntakeMealEntity fim : intake.getMeals()) {
                FoodHistoryResponse.MealEntry entry = new FoodHistoryResponse.MealEntry();
                entry.setMealName(fim.getMeal().getName());
                entry.setPortion(fim.getPortion());
                entry.setCalories(fim.getMeal().getCalories() * fim.getPortion());
                entry.setTime(intake.getIntakeTime().format(timeFormatter));

                grouped.computeIfAbsent(date, d -> new ArrayList<>()).add(entry);
            }
        }

        List<FoodHistoryResponse.DayRecord> dayRecords = grouped.entrySet().stream()
                .map(e -> {
                    FoodHistoryResponse.DayRecord day = new FoodHistoryResponse.DayRecord();
                    day.setDate(e.getKey());
                    day.setMeals(e.getValue());
                    return day;
                })
                .toList();

        FoodHistoryResponse response = new FoodHistoryResponse();
        response.setDays(dayRecords);
        return response;
    }

    public FoodIntakeResponse toResponse(FoodIntakeEntity entity) {
        FoodIntakeResponse response = new FoodIntakeResponse();
        response.setId(entity.getId());
        response.setUserId(entity.getUser().getId());
        response.setIntakeTime(entity.getIntakeTime());

        List<FoodIntakeResponse.MealEntry> meals = entity.getMeals().stream().map(fim -> {
            FoodIntakeResponse.MealEntry entry = new FoodIntakeResponse.MealEntry();
            entry.setMealName(fim.getMeal().getName());
            entry.setPortion(fim.getPortion());
            entry.setCaloriesPerPortion(fim.getMeal().getCalories());
            entry.setTotalCalories(fim.getMeal().getCalories() * fim.getPortion());
            return entry;
        }).toList();

        response.setMeals(meals);
        return response;
    }
}
