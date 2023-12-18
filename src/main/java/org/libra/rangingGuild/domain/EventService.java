package main.java.org.libra.rangingGuild.domain;

import org.rspeer.event.Subscribe;
import org.rspeer.game.component.tdi.Skill;
import org.rspeer.game.event.SkillEvent;
import org.rspeer.game.event.TickEvent;

import main.java.org.libra.rangingGuild.commons.SilentService;

public class EventService implements SilentService {
    private int tick = 0;
    private boolean xpDropOccurred = false;

    @Subscribe
    public void onTick(TickEvent event) {
        tick++;
    }

    public int ticksSince(int tick) {
        return this.tick - tick;
    }

    @Subscribe
    public void onSkillEvent(SkillEvent event) {
        if (event.getSource() == Skill.RANGED) {
            xpDropOccurred = true;
        }
    }

    public boolean hasXpDropOccurred() {
        return xpDropOccurred;
    }

    public void resetXpDropOccurred() {
        xpDropOccurred = false;
    }
}
