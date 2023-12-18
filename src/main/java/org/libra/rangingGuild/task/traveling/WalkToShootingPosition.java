package main.java.org.libra.rangingGuild.task.traveling;

import javax.inject.Inject;

import org.rspeer.game.movement.Movement;
import org.rspeer.game.script.Task;
import org.rspeer.game.script.TaskDescriptor;

import main.java.org.libra.rangingGuild.domain.GameStateService;
import main.java.org.libra.rangingGuild.domain.PlayerService;

@TaskDescriptor(name = "Walking to shooting position")
public class WalkToShootingPosition extends Task {
    private final PlayerService playerService;
    private final GameStateService gameStateService;

    @Inject
    public WalkToShootingPosition(PlayerService playerService, GameStateService gameStateService) {
        this.playerService = playerService;
        this.gameStateService = gameStateService;
    }

    @Override
    public boolean execute() {
        if (!shouldExecuteWalkToShootingPosition()) {
            return false;
        }

        Movement.walkTowards(playerService.getShootingPosition());
        return false;
    }

    private boolean shouldExecuteWalkToShootingPosition() {
        return gameStateService.isMiniGameStarted()
            && playerService.isPlayerWithinTargetPenArea()
            && !playerService.isPlayerAtShootingPosition();
    }
}
