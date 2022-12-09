package main.utils;

import java.util.Map;

public record Point(int x, int y) {
    public static Point O = new Point(0, 0);
    public static Point U = new Point(0, 1);
    public static Point D = new Point(0, -1);
    public static Point L = new Point(-1, 0);
    public static Point R = new Point(1, 0);

    public static Map<String, Point> DIRS = Map.of("U", U, "D", D, "L", L, "R", R);

    public Point add(Point o) {
        return new Point(x + o.x, y + o.y);
    }

    public Point mult(int c) {
        return new Point(c * x, c * y);
    }

    public Point neg() {
        return new Point(-x, -y);
    }

    public int dist0(Point o) {
        return Math.max(Math.abs(x - o.x), Math.abs(y - o.y));
    }

    public int dist1(Point o) {
        return Math.abs(x - o.x) + Math.abs(y - o.y);
    }

    public int dist2(Point o) {
        return (x - o.x) * (x - o.x) + (y - o.y) * (y - o.y);
    }
}
