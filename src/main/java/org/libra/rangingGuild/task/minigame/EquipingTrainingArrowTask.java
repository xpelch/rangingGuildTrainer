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
import main.java.org.libra.rangingGuild.domain.ScriptService;

@TaskDescriptor(name = "Equiping training arrow")
public class EquipingTrainingArrowTask extends Task {
    private static final String TRAINING_ARROW = "Bronze arrow";
    private final ScriptService scriptService;
    private final GameStateService gameStateService;

    @Inject
    public EquipingTrainingArrowTask(ScriptService scriptService, GameStateService gameStateService) {
        this.scriptService = scriptService;
        this.gameStateService = gameStateService;
    }

    public boolean execute() {
        if (!shouldExecuteTask()) {
            return false;
        }

        if (!Tabs.isOpen(Tab.INVENTORY)) {
            Tabs.open(Tab.INVENTORY);
            Time.sleepUntil(() -> Tabs.isOpen(Tab.INVENTORY), 2000);
        }

        Backpack backpack = Backpack.backpack();
        backpack.getItems(TRAINING_ARROW).first().interact(WIELD.getAction());
        Time.sleepUntil(() -> Equipment.equipment().getItemAt(Equipment.Slot.QUIVER) != null, 2000);

        return true;
    }

    private boolean shouldExecuteTask() {
        Item trainingArrows = Backpack.backpack().getItems(TRAINING_ARROW).first();
        Item quiverItem = Equipment.equipment().getItemAt(Equipment.Slot.QUIVER);

        return scriptService.isPlayerWithinTargetPenArea()
            && gameStateService.isMiniGameStarted()
            && trainingArrows != null
            && quiverItem == null;
    }
}
