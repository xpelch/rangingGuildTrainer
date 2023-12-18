package main.java.org.libra.rangingGuild.task.minigame;

import static main.java.org.libra.rangingGuild.data.InteractionActions.FIRE_AT;
import static main.java.org.libra.rangingGuild.data.InteractionActions.WIELD;

import org.rspeer.commons.Time;
import org.rspeer.commons.logging.Log;
import org.rspeer.game.adapter.component.inventory.Backpack;
import org.rspeer.game.adapter.component.inventory.Equipment;
import org.rspeer.game.adapter.scene.SceneObject;
import org.rspeer.game.component.Dialog;
import org.rspeer.game.component.Item;
import org.rspeer.game.component.tdi.Skill;
import org.rspeer.game.component.tdi.Skills;
import org.rspeer.game.script.Task;
import org.rspeer.game.script.TaskDescriptor;

import com.google.inject.Inject;

import main.java.org.libra.rangingGuild.data.Bow;
import main.java.org.libra.rangingGuild.domain.EventService;
import main.java.org.libra.rangingGuild.domain.GameStateService;
import main.java.org.libra.rangingGuild.domain.ScriptService;

@TaskDescriptor(name = "Firing at target")
public class FiringAtTargetTask extends Task {

    private static final String BRONZE_ARROW = "Bronze arrow";

    private final ScriptService scriptService;
    private final GameStateService gameStateService;
    private final EventService eventService;

    @Inject
    public FiringAtTargetTask(ScriptService scriptService, GameStateService gameStateService, EventService eventService) {
        this.scriptService = scriptService;
        this.gameStateService = gameStateService;
        this.eventService = eventService;
    }

    @Override
    public boolean execute() {
        if (!shouldFireAtTarget()) {
            return false;
        }

        if (!scriptService.isBowEquipped()) {
            return equipBow();
        }

        SceneObject target = scriptService.fetchTarget();
        if (target != null) {
            target.interact(FIRE_AT.getAction());
            Time.sleepUntil(eventService::hasXpDropOccurred, 2000);

            eventService.resetXpDropOccurred();
        }

        return true;
    }

    private boolean shouldFireAtTarget() {
        Item quiverItem = Equipment.equipment().getItemAt(Equipment.Slot.QUIVER);

        return scriptService.isPlayerWithinTargetPenArea()
            && scriptService.isPlayerAtShootingPosition()
            && gameStateService.isMiniGameStarted()
            && !Dialog.canContinue()
            && quiverItem != null
            && quiverItem.getName().equals(BRONZE_ARROW);
    }


    private boolean equipBow() {
        int playerRangedLevel = Skills.getLevel(Skill.RANGED);
        Bow bestBow = Bow.getBestAvailableAccuracyBowForLevel(scriptService.fetchInventoryBows(), playerRangedLevel);

        if (bestBow == null) {
            Log.info("No bow found to equip");
            gameStateService.setStopping(true);
            Log.info("Stopping script");
            return false;
        }

        Item bowToEquip = Backpack.backpack().getItems(bestBow.getName()).first();
        if (bowToEquip != null) {
            bowToEquip.interact(WIELD.getAction());
        }

        return true;
    }
}
