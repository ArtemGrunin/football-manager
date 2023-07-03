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
import ua.com.footballmanagerapp.dto.request.PlayerRequestDto;
import ua.com.footballmanagerapp.dto.request.TransferRequestDto;
import ua.com.footballmanagerapp.dto.response.PlayerResponseDto;
import ua.com.footballmanagerapp.model.Player;
import ua.com.footballmanagerapp.service.PlayerService;
import ua.com.footballmanagerapp.service.TransferService;
import ua.com.footballmanagerapp.service.mapper.RequestDtoMapper;
import ua.com.footballmanagerapp.service.mapper.ResponseDtoMapper;

@RestController
@RequestMapping("/players")
@AllArgsConstructor
public class PlayerController {
    private final PlayerService playerService;
    private final TransferService transferService;
    private final RequestDtoMapper<PlayerRequestDto, Player> playerRequestMapper;
    private final ResponseDtoMapper<PlayerResponseDto, Player> playerResponseMapper;

    @PostMapping
    public PlayerResponseDto add(@Valid @RequestBody PlayerRequestDto dto) {
        return playerResponseMapper
                .mapToDto(playerService.add(playerRequestMapper.mapToModel(dto)));
    }

    @GetMapping("/{id}")
    public PlayerResponseDto get(@PathVariable Long id) {
        Player player = playerService.get(id);
        return playerResponseMapper.mapToDto(player);
    }

    @PutMapping("/{id}")
    public PlayerResponseDto update(@PathVariable Long id,
                                    @Valid @RequestBody PlayerRequestDto dto) {
        Player player = playerRequestMapper.mapToModel(dto);
        player.setId(id);
        return playerResponseMapper.mapToDto(playerService.add(player));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        playerService.delete(id);
    }

    @GetMapping
    public List<PlayerResponseDto> getAll(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return playerService.findAll(pageRequest).stream()
                .map(playerResponseMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/{playerId}/transfer")
    public ResponseEntity<Void> transferPlayer(
            @PathVariable Long playerId,
            @RequestBody TransferRequestDto transferRequestDto) {
        transferService.transferPlayer(playerId, transferRequestDto.getTeamId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/init")
    public ResponseEntity<String> initPlayerData(
            @RequestBody List<PlayerRequestDto> playerDtoList) {
        List<Player> players = new ArrayList<>();
        for (PlayerRequestDto dto : playerDtoList) {
            Player player = playerRequestMapper.mapToModel(dto);
            players.add(player);
        }
        playerService.createPlayers(players);
        return ResponseEntity.ok("Data initialized successfully for players");
    }
}
