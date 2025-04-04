package com.aleksandr_kobelskiy.daily_calorie_tracker.service;

import com.aleksandr_kobelskiy.daily_calorie_tracker.dto.CreateMealRequest;
import com.aleksandr_kobelskiy.daily_calorie_tracker.entity.MealEntity;
import com.aleksandr_kobelskiy.daily_calorie_tracker.repository.MealRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MealServiceTest {

    private MealService mealService;
    private MealRepository mealRepository;

    @BeforeEach
    void setUp() {
        mealRepository = mock(MealRepository.class);
        mealService = new MealService(mealRepository);
    }

    @Test
    void createMeal_success() {
        CreateMealRequest request = new CreateMealRequest();
        request.setName("Овсянка");
        request.setCalories(110.0);
        request.setProteins(3.5);
        request.setFats(1.2);
        request.setCarbohydrates(21.0);

        MealEntity savedMeal = new MealEntity();
        savedMeal.setId(1L);
        savedMeal.setName(request.getName());
        savedMeal.setCalories(request.getCalories());
        savedMeal.setProteins(request.getProteins());
        savedMeal.setFats(request.getFats());
        savedMeal.setCarbohydrates(request.getCarbohydrates());
        savedMeal.setCreatedAt(LocalDateTime.now());
        savedMeal.setUpdatedAt(LocalDateTime.now());

        when(mealRepository.save(any(MealEntity.class))).thenReturn(savedMeal);

        MealEntity result = mealService.createMeal(request);

        assertNotNull(result);
        assertEquals("Овсянка", result.getName());
        assertEquals(110.0, result.getCalories());
        verify(mealRepository, times(1)).save(any(MealEntity.class));
    }

    @Test
    void createMeal_saveFails_shouldThrowException() {
        CreateMealRequest request = new CreateMealRequest();
        request.setName("Брокколи");
        request.setCalories(50.0);
        request.setProteins(4.0);
        request.setFats(0.5);
        request.setCarbohydrates(10.0);

        when(mealRepository.save(any(MealEntity.class)))
                .thenThrow(new RuntimeException("DB error"));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            mealService.createMeal(request);
        });

        assertEquals("DB error", ex.getMessage());
        verify(mealRepository, times(1)).save(any(MealEntity.class));
    }
}