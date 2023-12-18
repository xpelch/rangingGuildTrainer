package main.java.org.libra.rangingGuild.data;

public enum InteractionActions {
    FIRE_AT("Fire-at"),
    OPEN("Open"),
    CLOSE("Close"),
    TALK_TO("Talk-to"),
    WIELD("Wield");

    private final String action;

    InteractionActions(String action) {
        this.action = action;
    }

    public String getAction() {
        return this.action;
    }
}
