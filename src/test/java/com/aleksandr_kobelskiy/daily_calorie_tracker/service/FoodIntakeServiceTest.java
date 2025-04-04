package com.aleksandr_kobelskiy.daily_calorie_tracker.service;

import com.aleksandr_kobelskiy.daily_calorie_tracker.dto.CreateFoodIntakeRequest;
import com.aleksandr_kobelskiy.daily_calorie_tracker.dto.CreateFoodIntakeRequest.MealPortion;
import com.aleksandr_kobelskiy.daily_calorie_tracker.entity.FoodIntakeEntity;
import com.aleksandr_kobelskiy.daily_calorie_tracker.entity.FoodIntakeMealEntity;
import com.aleksandr_kobelskiy.daily_calorie_tracker.entity.MealEntity;
import com.aleksandr_kobelskiy.daily_calorie_tracker.entity.UserEntity;
import com.aleksandr_kobelskiy.daily_calorie_tracker.repository.FoodIntakeRepository;
import com.aleksandr_kobelskiy.daily_calorie_tracker.repository.MealRepository;
import com.aleksandr_kobelskiy.daily_calorie_tracker.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FoodIntakeServiceTest {

    private FoodIntakeService service;
    private FoodIntakeRepository intakeRepo;
    private UserRepository userRepo;
    private MealRepository mealRepo;

    @BeforeEach
    void setup() {
        intakeRepo = mock(FoodIntakeRepository.class);
        userRepo = mock(UserRepository.class);
        mealRepo = mock(MealRepository.class);
        service = new FoodIntakeService(intakeRepo, userRepo, mealRepo);
    }

    @Test
    void createFoodIntake_success() {
        CreateFoodIntakeRequest request = new CreateFoodIntakeRequest();
        request.setUserId(1L);
        request.setIntakeTime(LocalDateTime.now());

        MealPortion mp = new MealPortion();
        mp.setMealId(1L);
        mp.setPortion(1.0);
        request.setMeals(List.of(mp));

        UserEntity user = new UserEntity();
        user.setId(1L);

        MealEntity meal = new MealEntity();
        meal.setId(1L);
        meal.setCalories(100.0);

        when(userRepo.findById(1L)).thenReturn(Optional.of(user));
        when(mealRepo.findById(1L)).thenReturn(Optional.of(meal));
        when(intakeRepo.save(any())).thenAnswer(i -> i.getArguments()[0]);

        FoodIntakeEntity result = service.createFoodIntake(request);
        assertEquals(1, result.getMeals().size());
    }

    @Test
    void createFoodIntake_userNotFound() {
        CreateFoodIntakeRequest request = new CreateFoodIntakeRequest();
        request.setUserId(99L);
        when(userRepo.findById(99L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> service.createFoodIntake(request));
    }

    @Test
    void getDailyReport_success() {
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setDailyCalorieNorm(2000);
        when(userRepo.findById(1L)).thenReturn(Optional.of(user));
        when(intakeRepo.findByUserIdAndIntakeTimeBetween(eq(1L), any(), any())).thenReturn(List.of());

        var report = service.getDailyReport(1L, LocalDate.now());
        assertEquals(0, report.getTotalCalories());
        assertTrue(report.isWithinNorm());
    }

    @Test
    void getDailyReport_userNotFound() {
        when(userRepo.findById(999L)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> service.getDailyReport(999L, LocalDate.now()));
    }

    @Test
    void getFoodHistory_success() {
        UserEntity user = new UserEntity();
        when(userRepo.findById(1L)).thenReturn(Optional.of(user));
        when(intakeRepo.findByUserIdOrderByIntakeTimeDesc(1L)).thenReturn(List.of());

        var history = service.getFoodHistory(1L);
        assertNotNull(history.getDays());
    }

    @Test
    void getFoodHistory_userNotFound() {
        when(userRepo.findById(123L)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> service.getFoodHistory(123L));
    }

    @Test
    void toResponse_success() {
        UserEntity user = new UserEntity();
        user.setId(1L);

        MealEntity meal = new MealEntity();
        meal.setName("Овсянка");
        meal.setCalories(110.0);

        FoodIntakeMealEntity fim = new FoodIntakeMealEntity();
        fim.setMeal(meal);
        fim.setPortion(2.0);

        FoodIntakeEntity entity = new FoodIntakeEntity();
        entity.setId(1L);
        entity.setUser(user);
        entity.setIntakeTime(LocalDateTime.now());
        entity.setMeals(List.of(fim));
        fim.setFoodIntake(entity);

        var response = service.toResponse(entity);
        assertEquals(1, response.getMeals().size());
        assertEquals("Овсянка", response.getMeals().get(0).getMealName());
    }

    @Test
    void toResponse_nullInput_shouldThrow() {
        assertThrows(NullPointerException.class, () -> service.toResponse(null));
    }
}