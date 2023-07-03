package ua.com.footballmanagerapp.service;

import java.util.List;
import org.springframework.data.domain.PageRequest;
import ua.com.footballmanagerapp.model.Player;
import ua.com.footballmanagerapp.model.Team;

public interface TeamService {
    Team add(Team team);

    Team get(Long id);

    List<Team> findAll(PageRequest pageRequest);

    void delete(Long id);

    Team update(Team team);

    void createTeams(List<Team> teams);

    List<Player> getPlayersByTeamId(Long teamId);
}
