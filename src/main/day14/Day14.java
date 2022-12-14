package main.day14;

import main.utils.Day;
import main.utils.Point;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day14 extends Day<Integer> {

    private final Set<Point> rocks;
    private Set<Point> blocked;
    private final int maxY;

    private final static List<Point> FALL_DIRS = List.of(Point.U, Point.UL, Point.UR);
    private final static Point S = new Point(500, 0);

    //When using Points, y axis is upside down
    public Day14() {
        List<String> text =
                getReader().readAsStringList(14);
//                List.of(
//                        "498,4 -> 498,6 -> 496,6",
//                        "503,4 -> 502,4 -> 502,9 -> 494,9"
//                );

        rocks = text.stream()
                .flatMap(this::parseLine)
                .collect(Collectors.toSet());
        maxY = rocks.stream().mapToInt(Point::y).max().orElse(0);
    }

    private Stream<Point> parseLine(String s) {
        List<Point> points = Arrays.stream(s.split(" -> ")).map(Point::from).toList();
        return IntStream.range(0, points.size() - 1).mapToObj(i -> points.get(i).fromTo(points.get(i + 1))).flatMap(x -> x);
    }

    @Override
    public Integer getSolution1() {
        return solve(p -> p.y() > maxY);
    }

    @Override
    public Integer getSolution2() {
//        return solve(p -> blocked.contains(S));
        return solveWithBFS();
    }


    public int solve(Predicate<Point> stopCond) {
        Predicate<Point> keepCond = stopCond.negate();
        blocked = new HashSet<>(rocks);

//        show();
        Point curr = S;
        while (keepCond.test(curr)) {
            Point next = nextPlace(curr);
            if (next == null || next.y() > maxY + 1) {
                blocked.add(curr);
                curr = S;
            } else {
                curr = next;
            }
        }
//        show();
        return blocked.size() - rocks.size();
    }

    private Point nextPlace(Point p) {
        return getNextOptions(p).findFirst().orElse(null);
    }

    private Stream<Point> getNextOptions(Point p) {
        return FALL_DIRS.stream().map(p::add).filter(n -> !blocked.contains(n));
    }


    //optimalizing:
    public int solveWithBFS() {
        //setup
        LinkedList<Point> que = new LinkedList<>();
        blocked = new HashSet<>(rocks);
        que.addLast(S);

        //iterate
        int[] cnt = {1}; //meh, need const ref to increment in stream
        while (!que.isEmpty()) {
            Point curr = que.removeFirst();
            getNextOptions(curr)
                    .filter(p -> !blocked.contains(p))
                    .filter(p -> p.y() < maxY + 2)
                    .forEach(p -> {
                        blocked.add(p);
                        que.addLast(p);
                        cnt[0]++;
                    });
        }
        return cnt[0];
    }


    private void show() {
        int minX = blocked.stream().mapToInt(Point::x).min().orElse(0);
        int maxX = blocked.stream().mapToInt(Point::x).max().orElse(0);
        for (int j = 0; j < maxY + 5; j++) {
            for (int i = minX - 2; i < maxX + 2; i++) {
                System.out.print(rocks.contains(new Point(i, j)) ? "█" : blocked.contains(new Point(i, j)) ? "░" : " ");
            }
            System.out.println();

        }

        System.out.println();
        System.out.println();
        System.out.println();
    }
}
