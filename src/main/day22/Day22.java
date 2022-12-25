package main.day22;

import main.utils.Day;
import main.utils.Point;
import main.utils.PointAndDir;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.IntStream;

public class Day22 extends Day<Integer> {

    private final char[][] space;
    private final int[][] lineEdges;
    private final int[][] colEdges;
    private final String[] instr;
    private final int max;

    private final Point start;
    private static final Map<Point, Integer> DIR_SCORE = Map.of(
            Point.R, 0,
            Point.U, 1, //y axis reverted
            Point.L, 2,
            Point.D, 3
    );


    //When using Points, y axis is upside down
    public Day22() {
        List<String> text = getReader().readAsStringList(22);
//        List<String> text = getReader().readAsStringList("day22_sample.txt");

        int linebreak = text.indexOf("");
        instr = text.get(linebreak + 1).replaceAll("R", " R ").replaceAll("L", " L ").split(" ");
        List<String> spaceL = text.subList(0, linebreak);
        max = spaceL.stream().mapToInt(String::length).max().orElse(0);
        spaceL.add(0, " ");
        spaceL.add(" ");
//        awful performance but don't care that much
        lineEdges = spaceL.stream()
                .map(s -> s.replaceAll("#", "."))
                .map(s -> new int[]{s.indexOf(".") + 1, s.lastIndexOf(".") + 1})
                .toArray(int[][]::new);
        colEdges = IntStream.range(0, max + 1)
                .mapToObj(c -> {
                    int[] goodLines = IntStream.range(0, lineEdges.length).filter(l -> lineEdges[l][0] <= c && lineEdges[l][1] >= c).toArray();
                    return new int[]{goodLines[0], goodLines[goodLines.length - 1]};
                }).toArray(int[][]::new);
        space = spaceL.stream().map(s -> " " + s).map(s -> String.format("%1$-" + (max + 2) + "s", s)).map(String::toCharArray).toArray(char[][]::new);
        start = new Point(lineEdges[1][0], 1);
    }

    @Override
    public Integer getSolution1() {
        Function<PointAndDir, PointAndDir> move = curr -> move(curr, this::loopAround);
        return solve(move);
    }

    @Override
    public Integer getSolution2() {
        Function<PointAndDir, PointAndDir> move = curr -> move(curr, this::cubeAround);
        return solve(move);
    }

    private int solve(Function<PointAndDir, PointAndDir> move) {
        PointAndDir curr = new PointAndDir(start, Point.R);
        int s = 0;
        while (s < instr.length) {
            int steps = Integer.parseInt(instr[s++]);
            for (int i = 0; i < steps; i++) {
//                System.out.println("In step " + s + " before small step " + i + " state is " + curr);
                PointAndDir next = move.apply(curr);
                if (next == curr) {
                    break;
                } else {
                    curr = next;
                }
            }
//            System.out.println("After step " + s + " state is " + curr);
            if (s == instr.length) {
                return score(curr);
            } else {
                String rotDir = instr[s++];
                curr = rotate(curr, rotDir);
            }
        }
        throw new RuntimeException("Should not reach here");
    }

    private PointAndDir move(PointAndDir curr, Function<PointAndDir, PointAndDir> loopAround) {
        PointAndDir next = curr.step();
        char c = lookup(next.p());
        if (c == ' ') { //need to loop around before checking the values
            next = loopAround.apply(curr);
        }
        c = lookup(next.p());
        if (c == '.') {
            return next;
        }
        if (c == '#') {
            return curr;
        }
        throw new RuntimeException("Should not reach here");
    }

    private PointAndDir loopAround(PointAndDir curr) {
        PointAndDir next;
        Point d = curr.d();
        Point p = curr.p();
        if (d.x() != 0) {
            next = new PointAndDir(new Point(lineEdges[p.y()][(1 - d.x()) / 2], p.y()), d);
        } else {
            next = new PointAndDir(new Point(p.x(), colEdges[p.x()][(1 - d.y()) / 2]), d);
        }
        return next;
    }

    private PointAndDir cubeAround(PointAndDir pointAndDir) {
        Point p = pointAndDir.p();
        int x = p.x();
        int y = p.y();
        Point d = pointAndDir.d();
        if (Point.L.equals(d)) {
            if (x == 51 && y >= 1 && y <= 50) return new PointAndDir(new Point(1, 151 - y), Point.R);
            if (x == 51 && y >= 51 && y <= 100) return new PointAndDir(new Point(y - 50, 101), Point.U);
            if (x == 1 && y >= 101 && y <= 150) return new PointAndDir(new Point(51, 151 - y), Point.R);
            if (x == 1 && y >= 151 && y <= 200) return new PointAndDir(new Point(y - 100, 1), Point.U);
        }
        if (Point.R.equals(d)) {
            if (x == 150 && y >= 1 && y <= 50) return new PointAndDir(new Point(100, 151 - y), Point.L);
            if (x == 100 && y >= 51 && y <= 100) return new PointAndDir(new Point(y + 50, 50), Point.D);
            if (x == 100 && y >= 101 && y <= 150) return new PointAndDir(new Point(150, 151 - y), Point.L);
            if (x == 50 && y >= 151 && y <= 200) return new PointAndDir(new Point(y - 100, 150), Point.D);
        }
        if (Point.U.equals(d)) { //actually D on the map
            if (y == 200 && x >= 1 && x <= 50) return new PointAndDir(new Point(100 + x, 1), Point.U);
            if (y == 150 && x >= 51 && x <= 100) return new PointAndDir(new Point(50, 100 + x), Point.L);
            if (y == 50 && x >= 101 && x <= 150) return new PointAndDir(new Point(100, x - 50), Point.L);
        }
        if (Point.D.equals(d)) {
            if (y == 101 && x >= 1 && x <= 50) return new PointAndDir(new Point(51, 50 + x), Point.R);
            if (y == 1 && x >= 51 && x <= 100) return new PointAndDir(new Point(1, 100 + x), Point.R);
            if (y == 1 && x >= 101 && x <= 150) return new PointAndDir(new Point(x - 100, 200), Point.D);
        }
        throw new RuntimeException("Should not reach here");
    }

    private char lookup(Point p) {
        return space[p.y()][p.x()];
    }

    private PointAndDir rotate(PointAndDir curr, String rotDir) {
        return new PointAndDir(curr.p(), curr.d().rotate('M' - rotDir.charAt(0))); //want L to be >0 and R<0 since the Y axis is reverted
    }

    private static int score(PointAndDir curr) {
        return 1000 * curr.p().y() + 4 * curr.p().x() + DIR_SCORE.get(curr.d());
    }
}
