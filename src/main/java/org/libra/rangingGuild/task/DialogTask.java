package main.java.org.libra.rangingGuild.task;

import org.rspeer.game.component.Dialog;
import org.rspeer.game.script.Task;
import org.rspeer.game.script.TaskDescriptor;

import com.google.inject.Inject;

import main.java.org.libra.rangingGuild.domain.GameStateService;
import main.java.org.libra.rangingGuild.domain.ScriptService;

@TaskDescriptor(name = "Going through dialog")
public class DialogTask extends Task {
    private static final int FIRST_OPTION_INDEX = 0;
    private final ScriptService scriptService;
    private final GameStateService gameStateService;

    @Inject
    public DialogTask(ScriptService scriptService, GameStateService gameStateService) {
        this.scriptService = scriptService;
        this.gameStateService = gameStateService;
    }

    @Override
    public boolean execute() {
        if (!shouldExecuteDialogTask()) {
            return false;
        }

        if (Dialog.canContinue()) {
            Dialog.processContinue();
        } else if (Dialog.isViewingChatOptions()) {
            Dialog.process(FIRST_OPTION_INDEX);
        }

        return true;
    }

    private boolean shouldExecuteDialogTask() {
        return scriptService.isPlayerWithinTargetPenArea()
            && !gameStateService.isMiniGameStarted()
            && Dialog.canContinue() || Dialog.isViewingChatOptions();
    }
}
