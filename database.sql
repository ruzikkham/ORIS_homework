CREATE TABLE IF NOT EXISTS driver (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    age INTEGER NOT NULL,
    fines_num INTEGER NOT NULL,
    exp_years INTEGER NOT NULL,
    cars_num INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS settings (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    value INTEGER NOT NULL
);

INSERT INTO settings (name, value)
VALUES ('user_count', 3)
ON CONFLICT (name) DO NOTHING;
