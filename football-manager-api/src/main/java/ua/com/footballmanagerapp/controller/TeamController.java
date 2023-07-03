package ua.com.footballmanagerapp.controller;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.com.footballmanagerapp.dto.request.TeamRequestDto;
import ua.com.footballmanagerapp.dto.response.PlayerResponseDto;
import ua.com.footballmanagerapp.dto.response.TeamResponseDto;
import ua.com.footballmanagerapp.model.Player;
import ua.com.footballmanagerapp.model.Team;
import ua.com.footballmanagerapp.service.TeamService;
import ua.com.footballmanagerapp.service.mapper.RequestDtoMapper;
import ua.com.footballmanagerapp.service.mapper.ResponseDtoMapper;

@RestController
@RequestMapping("/teams")
@AllArgsConstructor
public class TeamController {
    private final TeamService teamService;
    private final RequestDtoMapper<TeamRequestDto, Team> teamRequestMapper;
    private final ResponseDtoMapper<TeamResponseDto, Team> teamResponseMapper;
    private final ResponseDtoMapper<PlayerResponseDto, Player> playerResponseMapper;

    @PostMapping
    public TeamResponseDto create(@Valid @RequestBody TeamRequestDto dto) {
        return teamResponseMapper
                .mapToDto(teamService.add(teamRequestMapper.mapToModel(dto)));
    }

    @GetMapping("/{id}")
    public TeamResponseDto get(@PathVariable Long id) {
        return teamResponseMapper.mapToDto(teamService.get(id));
    }

    @PutMapping("/{id}")
    public TeamResponseDto update(@PathVariable Long id,
                                  @Valid @RequestBody TeamRequestDto dto) {
        Team team = teamRequestMapper.mapToModel(dto);
        team.setId(id);
        return teamResponseMapper.mapToDto(teamService.add(team));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        teamService.delete(id);
    }

    @GetMapping
    public List<TeamResponseDto> getAll(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return teamService.findAll(pageRequest).stream()
                .map(teamResponseMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/init")
    public ResponseEntity<String> initTeamData(
            @RequestBody List<TeamRequestDto> teamDtoList) {
        List<Team> teams = new ArrayList<>();
        for (TeamRequestDto dto : teamDtoList) {
            Team team = teamRequestMapper.mapToModel(dto);
            teams.add(team);
        }
        teamService.createTeams(teams);
        return ResponseEntity.ok("Data initialized successfully for teams");
    }

    @GetMapping("/{teamId}/players")
    public List<PlayerResponseDto> getPlayersByTeamId(@PathVariable Long teamId) {
        return teamService.getPlayersByTeamId(teamId).stream()
                .map(playerResponseMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
