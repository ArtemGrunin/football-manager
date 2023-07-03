package ua.com.footballmanagerapp.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.com.footballmanagerapp.model.Player;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    List<Player> findAllByTeamId(Long teamId);

    @Modifying
    @Query("update Player p set p.team = null where p.team.id = :teamId")
    void setTeamToNullByTeamId(@Param("teamId") Long teamId);
}
