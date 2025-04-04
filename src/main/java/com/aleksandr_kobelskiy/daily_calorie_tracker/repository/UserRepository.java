package com.aleksandr_kobelskiy.daily_calorie_tracker.repository;

import com.aleksandr_kobelskiy.daily_calorie_tracker.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByEmail(String email);
}
