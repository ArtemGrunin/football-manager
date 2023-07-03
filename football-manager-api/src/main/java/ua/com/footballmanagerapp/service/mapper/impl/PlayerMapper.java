package ua.com.footballmanagerapp.service.mapper.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.com.footballmanagerapp.dto.request.PlayerRequestDto;
import ua.com.footballmanagerapp.dto.response.PlayerResponseDto;
import ua.com.footballmanagerapp.model.Player;
import ua.com.footballmanagerapp.service.TeamService;
import ua.com.footballmanagerapp.service.mapper.RequestDtoMapper;
import ua.com.footballmanagerapp.service.mapper.ResponseDtoMapper;

@RequiredArgsConstructor
@Component
public class PlayerMapper implements RequestDtoMapper<PlayerRequestDto, Player>,
        ResponseDtoMapper<PlayerResponseDto, Player> {

    private final TeamService teamService;

    @Override
    public Player mapToModel(PlayerRequestDto dto) {
        Player player = new Player();
        player.setFirstName(dto.getFirstName());
        player.setLastName(dto.getLastName());
        player.setCareerStartDate(dto.getCareerStartDate());
        player.setExperienceMonths(dto.getExperienceMonths());
        player.setAge(dto.getAge());
        player.setTeam(teamService.get(dto.getTeamId()));
        return player;
    }

    @Override
    public PlayerResponseDto mapToDto(Player player) {
        Long teamId = null;
        if (player.getTeam() != null) {
            teamId = player.getTeam().getId();
        }
        return new PlayerResponseDto(
                player.getId(),
                player.getFirstName(),
                player.getLastName(),
                player.getCareerStartDate(),
                player.getExperienceMonths(),
                player.getAge(),
                teamId
        );
    }
}
