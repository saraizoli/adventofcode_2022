package main.day24;

import main.utils.Day;
import main.utils.Point;
import main.utils.PointAndDir;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day24 extends Day<Integer> {

    private final Set<PointAndDir> blizzStart;
    private final List<Set<Point>> blizzs;
    private final int H;
    private final int W;
    private final int cycle;


    private final Point start;
    private final Point end;
    private final Point brCorner;

    private final static Map<Character, Point> DIRS = Map.of('>', Point.R, 'v', Point.U, '<', Point.L, '^', Point.D);

    //When using Points, y axis is upside down
    public Day24() {
        List<String> text = getReader().readAsStringList(24);
//        List<String> text = getReader().readAsStringList("day24_sample.txt");

        H = text.size() - 2;
        W = text.get(0).length() - 2;
        cycle = H * W / 2; //should be LCM but works for current cases

        blizzStart = IntStream.range(0, H)
                .mapToObj(y ->
                        IntStream.range(0, W)
                                .filter(x -> text.get(y + 1).charAt(x + 1) != '.')
                                .mapToObj(x -> new PointAndDir(new Point(x, y), DIRS.get(text.get(y + 1).charAt(x + 1)))))
                .flatMap(x -> x).collect(Collectors.toSet());
        start = new Point(0, -1);
        end = new Point(W - 1, H);
        brCorner = new Point(W, H);

        blizzs = calcBlizzes();
    }

    private List<Set<Point>> calcBlizzes() {
        List<PointAndDir> blizzAll = new ArrayList<>(blizzStart);
        List<Set<Point>> blizzs = new ArrayList<>(cycle);
        blizzs.add(blizzAll.stream().map(PointAndDir::p).collect(Collectors.toSet()));
//        show(blizzs.get(0));
        for (int i = 1; i < cycle; i++) {
            blizzAll = blizzAll.stream().map(this::moveBlizz).collect(Collectors.toList());
            blizzs.add(blizzAll.stream().map(PointAndDir::p).collect(Collectors.toSet()));
//            show(blizzs.get(i));
        }
        return blizzs;
    }

    private PointAndDir moveBlizz(PointAndDir pointAndDir) {
        Point p = pointAndDir.p();
        Point d = pointAndDir.d();
        return new PointAndDir(new Point(Math.floorMod(p.x() + d.x(), W), Math.floorMod(p.y() + d.y(), H)), d);
    }

    @Override
    public Integer getSolution1() {
        return solve(start, end, 0);
    }

    @Override
    public Integer getSolution2() {
        int t1 = solve(start, end, 0);
        int t2 = solve(end, start, t1);
        int t3 = solve(start, end, t2);
        return t3;
    }

    private int solve(Point s, Point e, int startTime) {
        List<Set<Point>> visited = IntStream.range(0, cycle).mapToObj(HashSet<Point>::new).collect(Collectors.toList());
        LinkedList<PointAndTime> que = new LinkedList<>();

        visited.get(startTime % cycle).add(s);
        que.addLast(new PointAndTime(s, startTime));

        PointAndTime curr;
        while (!que.isEmpty()) {
            curr = que.removeFirst();
            int t = curr.t() + 1;
            Set<Point> visAtT = visited.get(t % cycle);
            Point cp = curr.p();
            if (cp.equals(e)) {
                return t - 1;
            }
            Stream.concat(Stream.of(cp), cp.neighbours())
                    .filter(p -> !visAtT.contains(p))
                    .filter(p -> isFree(p, t))
                    .forEach(p -> {
                        visAtT.add(p);
                        que.addLast(new PointAndTime(p, t));
                    });
        }
        throw new RuntimeException("Should not get here");
    }

    private boolean isFree(Point p, int t) {
        return p.equals(start) || p.equals(end) ||
                (!blizzs.get(t % cycle).contains(p) && p.isInRect(Point.O, brCorner));
    }

    private void show(Set<Point> points) {
        for (int j = 0; j < H; j++) {
            for (int i = 0; i < W; i++) {
                System.out.print(points.contains(new Point(i, j)) ? "#" : ".");
            }
            System.out.println();
        }

        System.out.println();
        System.out.println();
    }
}
