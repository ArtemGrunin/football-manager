package ua.com.footballmanagerapp.service.mapper.impl;

import org.springframework.stereotype.Component;
import ua.com.footballmanagerapp.dto.request.TeamRequestDto;
import ua.com.footballmanagerapp.dto.response.TeamResponseDto;
import ua.com.footballmanagerapp.model.Team;
import ua.com.footballmanagerapp.service.mapper.RequestDtoMapper;
import ua.com.footballmanagerapp.service.mapper.ResponseDtoMapper;

@Component
public class TeamMapper implements RequestDtoMapper<TeamRequestDto, Team>,
        ResponseDtoMapper<TeamResponseDto, Team> {

    @Override
    public Team mapToModel(TeamRequestDto dto) {
        Team team = new Team();
        team.setName(dto.getName());
        team.setCity(dto.getCity());
        team.setCountry(dto.getCountry());
        team.setBudget(dto.getBudget());
        team.setTransferCommission(dto.getTransferCommission());
        return team;
    }

    @Override
    public TeamResponseDto mapToDto(Team team) {
        TeamResponseDto responseDto = new TeamResponseDto();
        responseDto.setId(team.getId());
        responseDto.setName(team.getName());
        responseDto.setCity(team.getCity());
        responseDto.setCountry(team.getCountry());
        responseDto.setBudget(team.getBudget());
        responseDto.setTransferCommission(team.getTransferCommission());
        return responseDto;
    }
}
