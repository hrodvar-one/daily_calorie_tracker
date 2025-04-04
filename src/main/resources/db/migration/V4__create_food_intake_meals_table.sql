CREATE TABLE food_intake_meals (
    id BIGSERIAL PRIMARY KEY,
    food_intake_id BIGINT NOT NULL,
    meal_id BIGINT NOT NULL,
    portion DECIMAL(10,2) NOT NULL CHECK (portion > 0),
    CONSTRAINT fk_food_intake_meal_food_intake FOREIGN KEY (food_intake_id) REFERENCES food_intakes(id) ON DELETE CASCADE,
    CONSTRAINT fk_food_intake_meal_meal FOREIGN KEY (meal_id) REFERENCES meals(id) ON DELETE CASCADE
);