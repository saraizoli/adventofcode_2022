package main.day15;

import main.utils.Point;

public record SensorBeacon(Point s, Point b, int d) {

    public SensorBeacon(Point s, Point b) {
        this(s, b, s.dist1(b));
    }
}
