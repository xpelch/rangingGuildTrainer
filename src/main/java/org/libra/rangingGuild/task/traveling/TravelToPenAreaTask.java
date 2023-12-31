package main.java.org.libra.rangingGuild.task.traveling;

import org.rspeer.game.movement.Movement;
import org.rspeer.game.scene.Players;
import org.rspeer.game.script.Task;
import org.rspeer.game.script.TaskDescriptor;

import com.google.inject.Inject;

import main.java.org.libra.rangingGuild.data.Areas;
import main.java.org.libra.rangingGuild.domain.PlayerService;

@TaskDescriptor(name = "Traveling to pen area")
public class TravelToPenAreaTask extends Task {
    private static final int PLAYER_IDLE_ID = 808;
    private final PlayerService playerService;

    @Inject
    public TravelToPenAreaTask(PlayerService playerService) {
        this.playerService = playerService;
    }

    @Override
    public boolean execute() {
        if (!shouldExecuteTravelToPenAreaTask()) {
            return false;
        }

        Movement.walkTowards(Areas.RANGING_PEN.getArea().getCenter());
        sleepUntil(() -> Players.self().getStance().getId() == PLAYER_IDLE_ID, 3);
        return false;
    }

    private boolean shouldExecuteTravelToPenAreaTask() {

        return playerService.isPlayerWithinRangingGuildArea() && !playerService.isPlayerWithinTargetPenArea();
    }
}
