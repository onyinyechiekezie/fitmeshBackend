-- Create gym_plans table
CREATE TABLE gym_plans (
                           plan_id VARCHAR(255) PRIMARY KEY,
                           name VARCHAR(255) NOT NULL,
                           description TEXT,
                           price DECIMAL(19, 2) NOT NULL,
                           currency VARCHAR(10) NOT NULL DEFAULT 'NGN',
                           billing_frequency VARCHAR(50) NOT NULL,
--                            duration_in_days INT NOT NULL,
                           features TEXT,
                           is_active BOOLEAN NOT NULL DEFAULT TRUE,
                           created_at TIMESTAMP NOT NULL,
                           updated_at TIMESTAMP NOT NULL
);

-- Create indexes
CREATE INDEX idx_gym_plans_active ON gym_plans(is_active);
CREATE INDEX idx_gym_plans_billing_frequency ON gym_plans(billing_frequency);

-- Insert default gym plans
INSERT INTO gym_plans (plan_id, name, description, price, currency, billing_frequency, duration_in_days, features, is_active, created_at, updated_at)
VALUES
    (
        'plan-basic-monthly',
        'Basic Monthly',
        'Access to gym floor and basic equipment',
        5000.00,
        'NGN',
        'MONTHLY',
        30,
        'Gym floor access,Basic equipment,Locker room',
        TRUE,
        NOW(),
        NOW()
    ),
    (
        'plan-premium-monthly',
        'Premium Monthly',
        'Access to all facilities including classes and pool',
        15000.00,
        'NGN',
        'MONTHLY',
        30,
        'Gym floor access,All equipment,Group classes,Swimming pool,Sauna,Personal trainer (2 sessions/month)',
        TRUE,
        NOW(),
        NOW()
    ),
    (
        'plan-basic-quarterly',
        'Basic Quarterly',
        'Access to gym floor and basic equipment - 3 months',
        13500.00,
        'NGN',
        'QUARTERLY',
        90,
        'Gym floor access,Basic equipment,Locker room',
        TRUE,
        NOW(),
        NOW()
    ),
    (
        'plan-premium-quarterly',
        'Premium Quarterly',
        'Access to all facilities - 3 months',
        40500.00,
        'NGN',
        'QUARTERLY',
        90,
        'Gym floor access,All equipment,Group classes,Swimming pool,Sauna,Personal trainer (6 sessions/quarter)',
        TRUE,
        NOW(),
        NOW()
    );