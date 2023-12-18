package main.java.org.libra.rangingGuild.domain;

import java.util.List;

import org.rspeer.game.adapter.component.inventory.Backpack;
import org.rspeer.game.adapter.component.inventory.Equipment;
import org.rspeer.game.adapter.scene.Npc;
import org.rspeer.game.adapter.scene.SceneObject;
import org.rspeer.game.component.Item;
import org.rspeer.game.position.Position;
import org.rspeer.game.scene.Npcs;
import org.rspeer.game.scene.Players;
import org.rspeer.game.scene.SceneObjects;

import com.google.inject.Singleton;

import main.java.org.libra.rangingGuild.commons.SilentService;
import main.java.org.libra.rangingGuild.data.Areas;

@Singleton
public class ScriptService implements SilentService {
    private static final Position TARGET_POSITION = new Position(2679, 3426, 0);
    private static final Position SHOOTING_POSITION = new Position(2673, 3420, 0);
    private static final String BOW = "bow";
    private static final String CROSSBOW = "crossbow";
    private static final String TARGET_NAME = "Target";
    private static final String JUDGE_NAME = "Competition Judge";
    private static final String COINS = "Coins";
    private static final int MINIMUM_COINS = 10000;

    public Position getShootingPosition() {
        return SHOOTING_POSITION;
    }

    public SceneObject fetchTarget() {
        return SceneObjects.query()
            .nameContains(TARGET_NAME)
            .on(TARGET_POSITION)
            .results()
            .nearest();
    }

    public SceneObject fetchGuildDoor() {
        return SceneObjects.query()
            .nameContains("Guild door")
            .results()
            .nearest();
    }

    public Npc fetchJudgeNpc() {
        return Npcs.query().nameContains(JUDGE_NAME).results().nearest();
    }

    public boolean isPlayerWithinTargetPenArea() {
        return Areas.RANGING_PEN.getArea().contains(Players.self());
    }

    public boolean isPlayerWithinRangingGuildArea() {
        return Areas.RANGING_GUILD_AREA.getArea().contains(Players.self());
    }

    public boolean isPlayerWithinRangingGuildEntranceArea() {
        return Areas.RANGING_GUILD_ENTRANCE.getArea().contains(Players.self());
    }

    public boolean hasCoins() {
        return Backpack.backpack().getItems(COINS).first().getStackSize() >= MINIMUM_COINS;
    }

    public boolean isBowEquipped() {
        Item bowWeapon = Equipment.equipment().getItemAt(Equipment.Slot.MAINHAND);
        return bowWeapon != null && bowWeapon.getName().contains(BOW) && !bowWeapon.getName().contains(CROSSBOW);
    }

    public List<Item> fetchInventoryBows() {
        return Backpack.backpack().getItems(bow -> bow.getName().contains(BOW)
            && !bow.getName().contains(CROSSBOW)).results;
    }

    public boolean isPlayerAtShootingPosition() {
        return Players.self().getPosition().equals(SHOOTING_POSITION);
    }
}
