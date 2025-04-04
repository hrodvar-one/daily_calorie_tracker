package com.aleksandr_kobelskiy.daily_calorie_tracker.service;

import com.aleksandr_kobelskiy.daily_calorie_tracker.dto.CreateUserRequest;
import com.aleksandr_kobelskiy.daily_calorie_tracker.entity.UserEntity;
import com.aleksandr_kobelskiy.daily_calorie_tracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserEntity createUser(CreateUserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email уже используется");
        }

        UserEntity user = new UserEntity();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setAge(request.getAge());
        user.setGender(request.getGender());
        user.setWeight(request.getWeight());
        user.setHeight(request.getHeight());
        user.setPurpose(request.getPurpose());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        user.calculateDailyCalorieNorm();

        return userRepository.save(user);
    }
}
