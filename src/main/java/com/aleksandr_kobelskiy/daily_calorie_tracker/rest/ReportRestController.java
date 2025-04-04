package com.aleksandr_kobelskiy.daily_calorie_tracker.rest;

import com.aleksandr_kobelskiy.daily_calorie_tracker.dto.DailyReportResponse;
import com.aleksandr_kobelskiy.daily_calorie_tracker.dto.FoodHistoryResponse;
import com.aleksandr_kobelskiy.daily_calorie_tracker.service.FoodIntakeService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportRestController {

    private final FoodIntakeService foodIntakeService;

    @GetMapping("/daily")
    public ResponseEntity<DailyReportResponse> getDailyReport(
            @RequestParam Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(foodIntakeService.getDailyReport(userId, date));
    }

    @GetMapping("/history")
    public ResponseEntity<FoodHistoryResponse> getHistory(@RequestParam Long userId) {
        return ResponseEntity.ok(foodIntakeService.getFoodHistory(userId));
    }
}
