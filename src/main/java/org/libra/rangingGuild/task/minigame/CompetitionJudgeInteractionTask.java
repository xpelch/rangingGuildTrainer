package main.java.org.libra.rangingGuild.task.minigame;

import static main.java.org.libra.rangingGuild.data.InteractionActions.TALK_TO;

import org.rspeer.commons.Time;
import org.rspeer.game.adapter.scene.Npc;
import org.rspeer.game.component.Dialog;
import org.rspeer.game.script.Task;
import org.rspeer.game.script.TaskDescriptor;

import com.google.inject.Inject;

import main.java.org.libra.rangingGuild.domain.GameStateService;
import main.java.org.libra.rangingGuild.domain.PlayerService;

@TaskDescriptor(name = "Interacting with the competition judge")
public class CompetitionJudgeInteractionTask extends Task {
    private final GameStateService gameStateService;
    private final PlayerService playerService;

    @Inject
    public CompetitionJudgeInteractionTask(GameStateService gameStateService, PlayerService playerService) {
        this.gameStateService = gameStateService;
        this.playerService = playerService;
    }

    @Override
    public boolean execute() {
        if (playerService.hasNoMoney()) {
            gameStateService.setStopping(true);
            return false;
        }

        if (!shouldExecuteCompetitionJudgeInteractionTask()) {
            return false;
        }

        Npc judge = playerService.fetchJudgeNpc();
        if (judge != null) {
            judge.interact(TALK_TO.getAction());
            Time.sleepUntil(Dialog::canContinue, 2000);
        }

        return true;
    }

    private boolean shouldExecuteCompetitionJudgeInteractionTask() {
        return gameStateService.isFreshStartMiniGame() && !(Dialog.canContinue() || Dialog.isViewingChatOptions());
    }
}
