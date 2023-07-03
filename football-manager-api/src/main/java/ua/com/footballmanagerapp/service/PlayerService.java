package ua.com.footballmanagerapp.service;

import java.util.List;
import org.springframework.data.domain.PageRequest;
import ua.com.footballmanagerapp.model.Player;

public interface PlayerService {
    Player add(Player player);

    Player get(Long id);

    List<Player> findAll(PageRequest pageRequest);

    void delete(Long id);

    Player update(Player player);

    void createPlayers(List<Player> players);
}
