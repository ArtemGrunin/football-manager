package ua.com.footballmanagerapp.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.footballmanagerapp.model.Player;
import ua.com.footballmanagerapp.model.Team;
import ua.com.footballmanagerapp.repository.PlayerRepository;
import ua.com.footballmanagerapp.repository.TeamRepository;
import ua.com.footballmanagerapp.service.TeamService;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;

    @Override
    public Team add(Team team) {
        return teamRepository.save(team);
    }

    @Override
    public Team get(Long id) {
        return teamRepository.findById(id)
                .orElseThrow(()
                        -> new RuntimeException("Team with id " + id + " was not found"));
    }

    @Override
    public List<Team> findAll(PageRequest pageRequest) {
        return teamRepository.findAll(pageRequest).toList();
    }

    @Transactional(readOnly = false)
    @Override
    public void delete(Long id) {
        playerRepository.setTeamToNullByTeamId(id);
        teamRepository.deleteById(id);
    }

    @Override
    public Team update(Team team) {
        if (teamRepository.existsById(team.getId())) {
            return teamRepository.save(team);
        } else {
            throw new RuntimeException("Team with id " + team.getId() + " does not exist");
        }
    }

    @Override
    public void createTeams(List<Team> teams) {
        teamRepository.saveAll(teams);
    }

    @Override
    public List<Player> getPlayersByTeamId(Long teamId) {
        return playerRepository.findAllByTeamId(teamId);
    }
}
