package com.aleksandr_kobelskiy.daily_calorie_tracker.service;

import com.aleksandr_kobelskiy.daily_calorie_tracker.dto.CreateUserRequest;
import com.aleksandr_kobelskiy.daily_calorie_tracker.entity.UserEntity;
import com.aleksandr_kobelskiy.daily_calorie_tracker.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userService = new UserService(userRepository);
    }

    @Test
    void createUser_success() {
        CreateUserRequest request = new CreateUserRequest();
        request.setName("Иван");
        request.setEmail("ivan@example.com");
        request.setAge(30);
        request.setGender("MALE");
        request.setWeight(75.0);
        request.setHeight(180);
        request.setPurpose("MAINTAIN");

        when(userRepository.existsByEmail("ivan@example.com")).thenReturn(false);
        when(userRepository.save(any(UserEntity.class))).thenAnswer(i -> {
            UserEntity u = (UserEntity) i.getArguments()[0];
            u.setId(1L);
            return u;
        });

        UserEntity result = userService.createUser(request);

        assertNotNull(result);
        assertEquals("ivan@example.com", result.getEmail());
        assertTrue(result.getDailyCalorieNorm() > 0);
        verify(userRepository).save(any(UserEntity.class));
    }

    @Test
    void createUser_emailAlreadyExists_shouldThrow() {
        CreateUserRequest request = new CreateUserRequest();
        request.setEmail("ivan@example.com");

        when(userRepository.existsByEmail("ivan@example.com")).thenReturn(true);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userService.createUser(request)
        );

        assertEquals("Email уже используется", exception.getMessage());
        verify(userRepository, never()).save(any());
    }
}