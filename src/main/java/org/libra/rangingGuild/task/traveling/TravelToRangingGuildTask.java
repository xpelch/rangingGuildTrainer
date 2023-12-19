package main.java.org.libra.rangingGuild.task.traveling;

import static main.java.org.libra.rangingGuild.data.InteractionActions.OPEN;

import org.rspeer.commons.Time;
import org.rspeer.game.adapter.scene.SceneObject;
import org.rspeer.game.movement.Movement;
import org.rspeer.game.scene.Players;
import org.rspeer.game.script.Task;
import org.rspeer.game.script.TaskDescriptor;

import com.google.inject.Inject;

import main.java.org.libra.rangingGuild.data.Areas;
import main.java.org.libra.rangingGuild.domain.PlayerService;

@TaskDescriptor(name = "Traveling to ranging guild")
public class TravelToRangingGuildTask extends Task {
    private static final int PLAYER_IDLE_ID = 808;
    private final PlayerService playerService;

    @Inject
    public TravelToRangingGuildTask(PlayerService playerService) {
        this.playerService = playerService;
    }

    @Override
    public boolean execute() {
        if (!shouldExecuteTravelToRangingGuildTask()) {
            return false;
        }

        if (!playerService.isPlayerWithinRangingGuildEntranceArea()) {
            Movement.walkTo(Areas.RANGING_GUILD_ENTRANCE.getArea().getRandomTile());
            sleepUntil(() -> Players.self().getStance().getId() == PLAYER_IDLE_ID, 3);
        }

        if (playerService.isPlayerWithinRangingGuildEntranceArea()) {
            SceneObject door = playerService.fetchGuildDoor();
            if (door != null) {
                door.interact(OPEN.getAction());
                sleepUntil(playerService::isPlayerWithinRangingGuildArea, 3);
            }
        }
        return true;
    }

    private boolean shouldExecuteTravelToRangingGuildTask() {
        return !playerService.isPlayerWithinRangingGuildArea() && playerService.isBowEquipped();
    }
}
