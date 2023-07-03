package ua.com.footballmanagerapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.com.footballmanagerapp.model.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
