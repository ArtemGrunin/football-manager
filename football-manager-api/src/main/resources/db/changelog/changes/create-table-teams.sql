CREATE TABLE IF NOT EXISTS teams (
                       id BIGINT PRIMARY KEY AUTO_INCREMENT,
                       name VARCHAR(255) NOT NULL,
                       city VARCHAR(255) NOT NULL,
                       country VARCHAR(255) NOT NULL,
                       budget DECIMAL(15, 2) NOT NULL,
                       transfer_commission DECIMAL(15, 2) NOT NULL
);
