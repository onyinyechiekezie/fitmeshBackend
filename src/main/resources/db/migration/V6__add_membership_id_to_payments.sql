-- Add membership_id to payments table (link payment to membership)
ALTER TABLE payments ADD COLUMN membership_id VARCHAR(255);

ALTER TABLE payments
    ADD CONSTRAINT fk_payments_membership
        FOREIGN KEY (membership_id) REFERENCES memberships(membership_id) ON DELETE SET NULL;

CREATE INDEX idx_payments_membership_id ON payments(membership_id);
