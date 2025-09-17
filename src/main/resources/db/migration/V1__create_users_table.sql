CREATE TABLE IF NOT EXISTS users
(
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    username  VARCHAR(50) NOT NULL UNIQUE,
    password  VARCHAR(50) NOT NULL,
    email     VARCHAR(50) NOT NULL,
    phone     VARCHAR(20) NULL,
    full_name VARCHAR(50) NULL
);