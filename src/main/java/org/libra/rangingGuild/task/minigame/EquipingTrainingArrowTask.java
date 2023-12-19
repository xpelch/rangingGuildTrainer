package main.java.org.libra.rangingGuild.task.minigame;

import static main.java.org.libra.rangingGuild.data.InteractionActions.WIELD;

import org.rspeer.commons.Time;
import org.rspeer.game.adapter.component.inventory.Backpack;
import org.rspeer.game.adapter.component.inventory.Equipment;
import org.rspeer.game.component.Item;
import org.rspeer.game.component.tdi.Tab;
import org.rspeer.game.component.tdi.Tabs;
import org.rspeer.game.script.Task;
import org.rspeer.game.script.TaskDescriptor;

import com.google.inject.Inject;

import main.java.org.libra.rangingGuild.domain.GameStateService;
import main.java.org.libra.rangingGuild.domain.PlayerService;

@TaskDescriptor(name = "Equiping training arrow")
public class EquipingTrainingArrowTask extends Task {
    private static final String TRAINING_ARROW = "Bronze arrow";
    private final PlayerService playerService;
    private final GameStateService gameStateService;

    @Inject
    public EquipingTrainingArrowTask(PlayerService playerService, GameStateService gameStateService) {
        this.playerService = playerService;
        this.gameStateService = gameStateService;
    }

    public boolean execute() {
        if (!shouldExecuteTask()) {
            return false;
        }

        if (!Tabs.isOpen(Tab.INVENTORY)) {
            Tabs.open(Tab.INVENTORY);
            sleepUntil(() -> Tabs.isOpen(Tab.INVENTORY), 3);
        }

        Backpack backpack = Backpack.backpack();
        backpack.getItems(TRAINING_ARROW).first().interact(WIELD.getAction());
        sleepUntil(() -> Equipment.equipment().getItemAt(Equipment.Slot.QUIVER) != null, 3);

        return true;
    }

    private boolean shouldExecuteTask() {
        Item trainingArrows = Backpack.backpack().getItems(TRAINING_ARROW).first();
        Item quiverItem = Equipment.equipment().getItemAt(Equipment.Slot.QUIVER);

        return playerService.isPlayerWithinTargetPenArea()
            && gameStateService.isMiniGameStarted()
            && trainingArrows != null
            && quiverItem == null;
    }
}
