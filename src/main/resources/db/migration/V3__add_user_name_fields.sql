-- Add firstname and lastname columns to users table
ALTER TABLE users ADD COLUMN firstname VARCHAR(100);
ALTER TABLE users ADD COLUMN lastname VARCHAR(100);