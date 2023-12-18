package main.java.org.libra.rangingGuild.data;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import org.rspeer.game.component.Item;

public enum Bow {
    SHORTBOW("Shortbow", 1, 8),
    LONGBOW("Longbow", 1, 8),
    OAK_SHORTBOW("Oak shortbow", 5, 14),
    OAK_LONGBOW("Oak longbow", 5, 14),
    WILLOW_SHORTBOW("Willow shortbow", 20, 20),
    WILLOW_COMP_BOW("Willow comp bow", 20, 22),
    WILLOW_LONGBOW("Willow longbow", 20, 20),
    MAPLE_SHORTBOW("Maple shortbow", 30, 29),
    MAPLE_LONGBOW("Maple longbow", 30, 29),
    OGRE_BOW("Ogre bow", 30, 38),
    COMP_OGRE_BOW("Comp ogre bow", 30, 38),
    YEW_SHORTBOW("Yew shortbow", 40, 47),
    YEW_LONGBOW("Yew longbow", 40, 47),
    YEW_COMP_BOW("Yew comp bow", 40, 49),
    MAGIC_SHORTBOW("Magic shortbow", 50, 75), // Assuming imbued
    MAGIC_LONGBOW("Magic longbow", 50, 69),
    MAGIC_COMP_BOW("Magic comp Bow", 50, 71),
    SEERCULL("Seercull", 50, 69),
    CRAWS_BOW("Craw's bow", 60, 75),
    DARK_BOW("Dark bow", 60, 95);

    private final String name;
    private final int requiredLevel;
    private final int accuracy;

    Bow(String name, int requiredLevel, int accuracy) {
        this.name = name;
        this.requiredLevel = requiredLevel;
        this.accuracy = accuracy;
    }

    public String getName() {
        return name;
    }

    public int getRequiredLevel() {
        return requiredLevel;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public static Bow findByName(String name) {
        String normalized = name.toLowerCase();
        for (Bow bow : Bow.values()) {
            if (bow.getName().toLowerCase().equals(normalized)) {
                return bow;
            }
        }
        return null;
    }

    public static Bow getBestAvailableAccuracyBowForLevel(List<Item> availableBows, int playerLevel) {
        return availableBows.stream()
            .map(Item::getName)
            .map(Bow::findByName)
            .filter(Objects::nonNull)
            .filter(bow -> playerLevel >= bow.getRequiredLevel())
            .max(Comparator.comparingInt(Bow::getAccuracy))
            .orElse(null);
    }

    public static Bow getBestBowForLevel(int level) {
        Bow bestBow = null;
        for (Bow bow : Bow.values()) {
            if (level >= bow.getRequiredLevel()) {
                if (bestBow == null || bow.getAccuracy() > bestBow.getAccuracy()) {
                    bestBow = bow;
                }
            }
        }
        return bestBow;
    }
}
