package ua.com.footballmanagerapp.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ua.com.footballmanagerapp.model.Player;
import ua.com.footballmanagerapp.repository.PlayerRepository;
import ua.com.footballmanagerapp.service.PlayerService;

@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;

    @Override
    public Player add(Player player) {
        return playerRepository.save(player);
    }

    @Override
    public Player get(Long id) {
        return playerRepository.findById(id)
                .orElseThrow(()
                        -> new RuntimeException("Player with id " + id + " was not found"));
    }

    @Override
    public List<Player> findAll(PageRequest pageRequest) {
        return playerRepository.findAll(pageRequest).toList();
    }

    @Override
    public void delete(Long id) {
        playerRepository.deleteById(id);
    }

    @Override
    public Player update(Player player) {
        if (playerRepository.existsById(player.getId())) {
            return playerRepository.save(player);
        } else {
            throw new RuntimeException(
                    "Player with id "
                    + player.getId()
                    + " does not exist");
        }
    }

    @Override
    public void createPlayers(List<Player> players) {
        playerRepository.saveAll(players);
    }
}
