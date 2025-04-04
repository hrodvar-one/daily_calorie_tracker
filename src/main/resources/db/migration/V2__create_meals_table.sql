CREATE TABLE meals (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    calories DECIMAL(10,2) NOT NULL CHECK (calories > 0),
    proteins DECIMAL(10,2) NOT NULL CHECK (proteins >= 0),
    fats DECIMAL(10,2) NOT NULL CHECK (fats >= 0),
    carbohydrates DECIMAL(10,2) NOT NULL CHECK (carbohydrates >= 0),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);