package main.java.org.libra.rangingGuild.task.minigame;

import org.rspeer.commons.Time;
import org.rspeer.game.adapter.scene.SceneObject;
import org.rspeer.game.component.Dialog;
import org.rspeer.game.script.Task;
import org.rspeer.game.script.TaskDescriptor;

import com.google.inject.Inject;

import main.java.org.libra.rangingGuild.domain.GameStateService;
import main.java.org.libra.rangingGuild.domain.ScriptService;

@TaskDescriptor(name = "Relaunching minigame")
public class RelaunchMinigameTask extends Task {
    private static final String FIRE_AT_OPTION = "Fire-at";
    private final ScriptService scriptService;
    private final GameStateService gameStateService;

    @Inject
    public RelaunchMinigameTask(ScriptService scriptService, GameStateService gameStateService) {
        this.scriptService = scriptService;
        this.gameStateService = gameStateService;
    }

    public boolean execute() {
        if (!scriptService.hasCoins()) {
            gameStateService.setStopping(true);
            return false;
        }

        if (!shouldExecuteRelaunchMinigameTask()) {
            return false;
        }

        SceneObject target = scriptService.fetchTarget();
        if (target != null) {
            target.interact(FIRE_AT_OPTION);
            Time.sleepUntil(Dialog::canContinue, 2000);
        }

        return true;
    }

    private boolean shouldExecuteRelaunchMinigameTask() {
        return gameStateService.isMiniGameOver() && scriptService.isPlayerWithinTargetPenArea();
    }
}
