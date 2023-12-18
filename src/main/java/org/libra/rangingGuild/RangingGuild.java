package main.java.org.libra.rangingGuild;

import java.util.function.Supplier;

import org.rspeer.commons.ArrayUtils;
import org.rspeer.commons.IntPair;
import org.rspeer.commons.StopWatch;
import org.rspeer.event.Subscribe;
import org.rspeer.game.adapter.component.inventory.Equipment;
import org.rspeer.game.component.InventoryType;
import org.rspeer.game.component.tdi.Skill;
import org.rspeer.game.event.ChatMessageEvent;
import org.rspeer.game.event.InventoryEvent;
import org.rspeer.game.script.Task;
import org.rspeer.game.script.TaskScript;
import org.rspeer.game.script.meta.ScriptMeta;
import org.rspeer.game.script.meta.paint.PaintBinding;
import org.rspeer.game.script.meta.paint.PaintScheme;
import org.rspeer.game.service.inventory.InventoryCache;
import org.rspeer.game.service.stockmarket.StockMarketService;

import main.java.org.libra.rangingGuild.domain.EventService;
import main.java.org.libra.rangingGuild.domain.GameStateService;
import main.java.org.libra.rangingGuild.domain.PlayerService;
import main.java.org.libra.rangingGuild.task.DialogTask;
import main.java.org.libra.rangingGuild.task.StopTask;
import main.java.org.libra.rangingGuild.task.minigame.CompetitionJudgeInteractionTask;
import main.java.org.libra.rangingGuild.task.minigame.EquipingTrainingArrowTask;
import main.java.org.libra.rangingGuild.task.minigame.FiringAtTargetTask;
import main.java.org.libra.rangingGuild.task.minigame.RelaunchMinigameTask;
import main.java.org.libra.rangingGuild.task.traveling.TravelToPenAreaTask;
import main.java.org.libra.rangingGuild.task.traveling.TravelToRangingGuildTask;
import main.java.org.libra.rangingGuild.task.traveling.WalkToShootingPosition;

@org.rspeer.event.ScriptService({
    InventoryCache.class,
    StockMarketService.class,
    PlayerService.class,
    EventService.class,
    GameStateService.class,
})
@ScriptMeta(
    name = "Ranging Guild trainer",
    regions = -3,
    paint = PaintScheme.class,
    desc = "Train range in the ranging guild",
    developer = "Lib"
)
public class RangingGuild extends TaskScript {
    private static final int TICKET_ID = 1464;

    @PaintBinding("Runtime")
    private final StopWatch runtime = StopWatch.start();

    @PaintBinding("XP")
    private final Skill skill = Skill.RANGED;

    @PaintBinding("Last task")
    private final Supplier<String> task = () -> manager.getLastTaskName();

    @PaintBinding(value = "Arrows fired", rate = true)
    private int arrowFired = 0;

    @PaintBinding(value = "Tickets earned", rate = true)
    private int ticketEarned = 0;

    @Override
    public Class<? extends Task>[] tasks() {
        return ArrayUtils.getTypeSafeArray(
            StopTask.class,
            CompetitionJudgeInteractionTask.class,
            DialogTask.class,
            RelaunchMinigameTask.class,
            EquipingTrainingArrowTask.class,
            FiringAtTargetTask.class,
            TravelToPenAreaTask.class,
            TravelToRangingGuildTask.class,
            WalkToShootingPosition.class
        );
    }

    @Override
    public void initialize() {
        InventoryCache inventoryCache = injector.getInstance(InventoryCache.class);
        inventoryCache.submit(InventoryType.BACKPACK, 28);
        inventoryCache.submit(InventoryType.BANK, 1220);
        inventoryCache.submit(InventoryType.EQUIPMENT, Equipment.Slot.values().length);
    }

    @Subscribe
    public void notify(ChatMessageEvent event) {
        String message = event.getContents();
        if (message.contains("You carefully aim at the target...")) {
            arrowFired++;
        }
    }

    @Subscribe
    public void notify(InventoryEvent event) {
        boolean isNotBackpackEvent = event.getKey() != InventoryType.BACKPACK.getKey();
        if (isNotBackpackEvent) {
            return;
        }

        IntPair currentItem = event.getCurrent();
        IntPair nextItem = event.getNext();
        boolean itemsExist = currentItem != null && nextItem != null;
        if (itemsExist && currentItem.getLeft() == TICKET_ID && nextItem.getLeft() == TICKET_ID) {
            ticketEarned += nextItem.getRight() - currentItem.getRight();
        }
    }
}
