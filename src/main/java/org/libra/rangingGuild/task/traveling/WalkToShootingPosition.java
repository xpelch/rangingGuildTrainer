package main.java.org.libra.rangingGuild.task.traveling;

import javax.inject.Inject;

import org.rspeer.game.movement.Movement;
import org.rspeer.game.script.Task;
import org.rspeer.game.script.TaskDescriptor;

import main.java.org.libra.rangingGuild.domain.GameStateService;
import main.java.org.libra.rangingGuild.domain.ScriptService;

@TaskDescriptor(name = "Walking to shooting position")
public class WalkToShootingPosition extends Task {
    private final ScriptService scriptService;
    private final GameStateService gameStateService;

    @Inject
    public WalkToShootingPosition(ScriptService scriptService, GameStateService gameStateService) {
        this.scriptService = scriptService;
        this.gameStateService = gameStateService;
    }

    @Override
    public boolean execute() {
        if (!shouldExecuteWalkToShootingPosition()) {
            return false;
        }

        Movement.walkTowards(scriptService.getShootingPosition());
        return false;
    }

    private boolean shouldExecuteWalkToShootingPosition() {
        return gameStateService.isMiniGameStarted()
            && scriptService.isPlayerWithinTargetPenArea()
            && !scriptService.isPlayerAtShootingPosition();
    }
}
