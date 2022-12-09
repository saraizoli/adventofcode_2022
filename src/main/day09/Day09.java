package main.day09;

import main.utils.Day;
import main.utils.Point;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static main.utils.Point.DIRS;
import static main.utils.Point.O;

public class Day09 extends Day<Integer> {

    private final List<String> moves;
    private Point[] rope;

    public Day09() {
//        moves = List.of("R 4", "U 4", "L 3", "D 1", "R 4", "D 1", "L 5", "R 2");
//        moves = List.of("R 5", "U 8", "L 8", "D 3", "R 17", "D 10", "L 25", "U 20");
        moves = getReader().readAsStringList(9);
    }

    @Override
    public Integer getSolution1() {
        return solve(2);
    }

    @Override
    public Integer getSolution2() {
        return solve(10);
    }

    public int solve(int l) {
        rope = IntStream.range(0, l).mapToObj(i -> O).toArray(Point[]::new);
        Set<Point> visited = new HashSet<>();
        moves.stream().map(s -> s.split(" "))
                .forEach(tok -> {
                    Point dir = DIRS.get(tok[0]);
                    IntStream.range(0, Integer.parseInt(tok[1])).mapToObj(i -> move(dir)).forEach(visited::add);
                });
        return visited.size();
    }

    private Point move(Point dir) {
        int size = rope.length;
        rope[0] = rope[0].add(dir);
        IntStream.range(1, size)
                .forEach(i -> rope[i] = follow(rope[i], rope[i - 1]));
        return rope[size - 1];
    }

    private Point follow(Point p1, Point p2) {
        if (p1.dist0(p2) < 2) {
            return p1;
        }
        Point diff = p2.add(p1.neg());
        return p1.add(new Point(Integer.signum(diff.x()), Integer.signum(diff.y())));
    }

    private void show(int S, int E) {
        for (int j = E - 1; j >= -S; j--) {
            for (int i = -S; i < E; i++) {
                int c = Arrays.asList(rope).indexOf(new Point(i, j));
                System.out.print(c > -1 ? " " + c + " " : " . ");
            }
            System.out.println();

        }

        System.out.println();
        System.out.println();
        System.out.println();
    }

}

// neat solution for part1 with rope = LinkedList<Point> 
//    private Point move(Point dir) {
//        Point newHead = rope.getFirst().add(dir);
//        Point oldTail = rope.getLast();
//        if (newHead.dist0(oldTail) > 1) {
//            rope.removeLast();
//        } else {
//            rope.removeFirst();
//        }
//        rope.addFirst(newHead);
//        return rope.getLast();
//    }

//Integer.signum does the same
//    private int trunc(int i) {
//        int s = i < 0 ? -1 : 1;
//        return s * Math.min(1, Math.abs(i));
//    }