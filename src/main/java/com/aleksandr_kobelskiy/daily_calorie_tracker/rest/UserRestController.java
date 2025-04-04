package com.aleksandr_kobelskiy.daily_calorie_tracker.rest;

import com.aleksandr_kobelskiy.daily_calorie_tracker.dto.CreateUserRequest;
import com.aleksandr_kobelskiy.daily_calorie_tracker.entity.UserEntity;
import com.aleksandr_kobelskiy.daily_calorie_tracker.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserEntity> createUser(@RequestBody @Valid CreateUserRequest request) {
        UserEntity user = userService.createUser(request);
        return ResponseEntity.ok(user);
    }
}
