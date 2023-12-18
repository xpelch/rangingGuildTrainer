package main.java.org.libra.rangingGuild.data;

import org.rspeer.game.position.Position;
import org.rspeer.game.position.area.Area;

public enum Areas {
    RANGING_PEN(Area.polygonal(
        new Position(2667, 3418, 0),
        new Position(2671, 3422, 0),
        new Position(2673, 3422, 0),
        new Position(2676, 3419, 0),
        new Position(2676, 3417, 0),
        new Position(2672, 3413, 0))
    ),

    RANGING_GUILD_ENTRANCE(Area.polygonal(
        new Position(2657, 3442, 0),
        new Position(2652, 3437, 0),
        new Position(2654, 3435, 0),
        new Position(2662, 3443, 0),
        new Position(2660, 3445, 0)
    )),

    RANGING_GUILD_AREA(Area.polygonal(
        new Position(2667, 3446, 0),
        new Position(2652, 3430, 0),
        new Position(2652, 3426, 0),
        new Position(2667, 3412, 0),
        new Position(2670, 3412, 0),
        new Position(2685, 3427, 0),
        new Position(2685, 3430, 0),
        new Position(2670, 3445, 0)
    ));

    private final Area area;

    private Areas(Area area) {
        this.area = area;
    }

    public Area getArea() {
        return area;
    }
}
