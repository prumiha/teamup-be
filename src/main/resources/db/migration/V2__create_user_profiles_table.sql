CREATE TABLE IF NOT EXISTS user_profiles (
    id         BIGINT       NOT NULL PRIMARY KEY,
    avatar_url VARCHAR(255) NULL,
    bio        VARCHAR(500) NULL,
    CONSTRAINT fk_user_profiles_user
    FOREIGN KEY (id) REFERENCES users(id)
    ON DELETE CASCADE
);
