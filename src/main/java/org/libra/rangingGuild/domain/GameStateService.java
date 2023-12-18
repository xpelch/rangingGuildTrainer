package main.java.org.libra.rangingGuild.domain;

import org.rspeer.game.Vars;

import com.google.inject.Singleton;

import main.java.org.libra.rangingGuild.commons.SilentService;

@Singleton
public class GameStateService implements SilentService {
    private static final int TARGET_MINIGAME_VARP = 156;
    private static final int SCORE_VARP = 157;
    private boolean stopping = false;

    public boolean isStopping() {
        return stopping;
    }

    public void setStopping(boolean stopping) {
        this.stopping = stopping;
    }

    public boolean isMiniGameStarted() {
        return Vars.get(TARGET_MINIGAME_VARP) != 0;
    }

    public boolean isMiniGameOver() {
        return Vars.get(SCORE_VARP) != 0 && Vars.get(TARGET_MINIGAME_VARP) == 11;
    }

    public boolean isFreshStartMiniGame() {
        return !isMiniGameStarted() && !isMiniGameOver();
    }
}
