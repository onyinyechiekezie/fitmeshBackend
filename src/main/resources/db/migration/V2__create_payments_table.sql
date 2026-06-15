-- Create payments table
CREATE TABLE payments (
                          payment_id VARCHAR(255) PRIMARY KEY,
                          payment_reference VARCHAR(50) NOT NULL UNIQUE,
                          amount DECIMAL(19, 2) NOT NULL,
                          currency VARCHAR(10) NOT NULL,
                          payment_type VARCHAR(50) NOT NULL,
                          payment_status VARCHAR(50) NOT NULL,
                          user_id VARCHAR(255) NOT NULL,
                          description TEXT,
                          provider_reference VARCHAR(255),
                          created_at TIMESTAMP NOT NULL,
                          updated_at TIMESTAMP NOT NULL,
                          FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Create indexes for performance
CREATE INDEX idx_payments_user_id ON payments(user_id);
CREATE INDEX idx_payments_reference ON payments(payment_reference);
CREATE INDEX idx_payments_status ON payments(payment_status);
CREATE INDEX idx_payments_provider_reference ON payments(provider_reference);
CREATE INDEX idx_payments_created_at ON payments(created_at);
```