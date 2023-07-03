package ua.com.footballmanagerapp.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.com.footballmanagerapp.model.Player;
import ua.com.footballmanagerapp.model.Team;
import ua.com.footballmanagerapp.service.PlayerService;
import ua.com.footballmanagerapp.service.TeamService;
import ua.com.footballmanagerapp.service.TransferService;

@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {
    private static final BigDecimal HUNDRED = BigDecimal.valueOf(100);
    private static final BigDecimal EXPERIENCE_MULTIPLIER = BigDecimal.valueOf(100_000);
    private final PlayerService playerService;
    private final TeamService teamService;

    @Override
    public void transferPlayer(Long playerId, Long teamId) {
        Player player = playerService.get(playerId);
        Team newTeam = teamService.get(teamId);

        if (player == null) {
            throw new RuntimeException("Player with id " + playerId + " not found");
        }
        if (newTeam == null) {
            throw new RuntimeException("Team with id " + teamId + " not found");
        }
        if (player.getTeam() == null) {
            throw new RuntimeException("Player does not belong to any team");
        }
        if (player.getTeam().equals(newTeam)) {
            throw new RuntimeException("Player is already in the team " + newTeam.getName());
        }
        BigDecimal transferPrice = calculateTransferPrice(player, newTeam);
        if (newTeam.getBudget().compareTo(transferPrice) < 0) {
            throw new RuntimeException("Team " + newTeam.getName() + " cannot afford this player");
        }
        performTransfer(player, newTeam, transferPrice);
    }

    private BigDecimal calculateTransferPrice(Player player, Team newTeam) {
        BigDecimal experienceMonths = BigDecimal.valueOf(player.getExperienceMonths());
        BigDecimal playerAge = BigDecimal.valueOf(player.getAge());
        BigDecimal basePrice = experienceMonths
                .multiply(EXPERIENCE_MULTIPLIER)
                .divide(playerAge, RoundingMode.HALF_UP);
        BigDecimal commissionRate = newTeam
                .getTransferCommission()
                .divide(HUNDRED, RoundingMode.HALF_UP);
        BigDecimal commission = basePrice.multiply(commissionRate);
        return basePrice.add(commission);
    }

    private void performTransfer(Player player, Team newTeam, BigDecimal transferPrice) {
        Team oldTeam = player.getTeam();
        newTeam.setBudget(newTeam.getBudget().subtract(transferPrice));
        teamService.add(newTeam);
        oldTeam.setBudget(oldTeam.getBudget().add(transferPrice));
        teamService.add(oldTeam);
        player.setTeam(newTeam);
        playerService.add(player);
    }
}
