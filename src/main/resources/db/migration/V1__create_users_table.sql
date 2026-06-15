-- Create users table
CREATE TABLE users (
                       user_id VARCHAR(255) PRIMARY KEY,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       phone_number VARCHAR(20) NOT NULL UNIQUE,
                       password_hash VARCHAR(255) NOT NULL,
                       status VARCHAR(50) NOT NULL,
                       registered_at TIMESTAMP NOT NULL,
                       last_login_at TIMESTAMP,
                       updated_at TIMESTAMP NOT NULL
);

-- Create user_roles table (many-to-many relationship)
CREATE TABLE user_roles (
                            user_id VARCHAR(255) NOT NULL,
                            role VARCHAR(50) NOT NULL,
                            PRIMARY KEY (user_id, role),
                            FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Create indexes for performance
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_phone_number ON users(phone_number);
CREATE INDEX idx_users_status ON users(status);