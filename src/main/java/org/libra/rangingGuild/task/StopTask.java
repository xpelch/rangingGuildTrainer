package main.java.org.libra.rangingGuild.task;

import org.rspeer.game.script.Task;
import org.rspeer.game.script.TaskDescriptor;

import com.google.inject.Inject;

import main.java.org.libra.rangingGuild.domain.GameStateService;

@TaskDescriptor(
    name = "Stopping",
    stoppable = true,
    priority = Integer.MAX_VALUE
)
public class StopTask extends Task {

    private final GameStateService gameStateService;

    @Inject
    public StopTask(GameStateService gameStateService) {
        this.gameStateService = gameStateService;
    }

    @Override
    public boolean execute() {
        return gameStateService.isStopping();
    }
}
