CREATE TABLE players (
                         id BIGINT PRIMARY KEY AUTO_INCREMENT,
                         first_name VARCHAR(255) NOT NULL,
                         last_name VARCHAR(255) NOT NULL,
                         career_start_date DATE NOT NULL,
                         experience_months INT NOT NULL,
                         date_of_birth DATE NOT NULL,
                         team_id BIGINT,
                         FOREIGN KEY (team_id) REFERENCES teams(id)
);
