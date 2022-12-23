package main.day22;

import main.utils.Point;

public record PointAndDir(Point p, Point d) {
    public PointAndDir step(){
        return new PointAndDir(p.add(d), d);
    }
}
