-- Create memberships table
CREATE TABLE memberships (
                             membership_id VARCHAR(255) PRIMARY KEY,
                             user_id VARCHAR(255) NOT NULL,
                             plan_id VARCHAR(255) NOT NULL,
                             status VARCHAR(50) NOT NULL,
                             start_date TIMESTAMP,
                             end_date TIMESTAMP,
                             last_payment_date TIMESTAMP,
                             created_at TIMESTAMP NOT NULL,
                             updated_at TIMESTAMP NOT NULL,
                             FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
                             FOREIGN KEY (plan_id) REFERENCES gym_plans(plan_id)
);

-- Create indexes
CREATE INDEX idx_memberships_user_id ON memberships(user_id);
CREATE INDEX idx_memberships_plan_id ON memberships(plan_id);
CREATE INDEX idx_memberships_status ON memberships(status);
CREATE INDEX idx_memberships_end_date ON memberships(end_date);

-- Add membership_id to payments table (link payment to membership)
-- ALTER TABLE payments ADD COLUMN membership_id VARCHAR(255);
-- ALTER TABLE payments ADD CONSTRAINT fk_payments_membership
--     FOREIGN KEY (membership_id) REFERENCES memberships(membership_id) ON DELETE SET NULL;
--
-- CREATE INDEX idx_payments_membership_id ON payments(membership_id);