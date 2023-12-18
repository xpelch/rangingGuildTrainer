package main.java.org.libra.rangingGuild.task.traveling;

import org.rspeer.game.movement.Movement;
import org.rspeer.game.script.Task;
import org.rspeer.game.script.TaskDescriptor;

import com.google.inject.Inject;

import main.java.org.libra.rangingGuild.data.Areas;
import main.java.org.libra.rangingGuild.domain.ScriptService;

@TaskDescriptor(name = "Traveling to pen area")
public class TravelToPenAreaTask extends Task {
    private final ScriptService scriptService;

    @Inject
    public TravelToPenAreaTask(ScriptService scriptService) {
        this.scriptService = scriptService;
    }

    @Override
    public boolean execute() {
        if (!shouldExecuteTravelToPenAreaTask()) {
            return false;
        }

        Movement.walkTo(Areas.RANGING_PEN.getArea().getCenter());

        return false;
    }

    private boolean shouldExecuteTravelToPenAreaTask() {

        return scriptService.isPlayerWithinRangingGuildArea() && !scriptService.isPlayerWithinTargetPenArea();
    }
}
