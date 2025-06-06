CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    age INTEGER NOT NULL,
    gender VARCHAR(10) NOT NULL CHECK (gender IN ('MALE', 'FEMALE')),
    weight DECIMAL(5,2) NOT NULL,
    height INTEGER NOT NULL,
    purpose VARCHAR(20) NOT NULL CHECK (purpose IN ('LOSE_WEIGHT', 'MAINTAIN', 'GAIN_WEIGHT')),
    daily_calorie_norm INTEGER,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);