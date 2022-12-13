package main.day12;

import main.utils.Day;
import main.utils.Point;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

public class Day12 extends Day<Integer> {

    private final int[][] map;
    private final int H;
    private final int W;
    private final Predicate<Point> inZone;

    private int[][] mem;

    //When using Points, x an y axis is swapped, but this way easier to use as index
    public Day12() {
//        map = new int[][]{
//                {'S', 'a', 'b', 'q', 'p', 'o', 'n', 'm'},
//                {'a', 'b', 'c', 'r', 'y', 'x', 'x', 'l'},
//                {'a', 'c', 'c', 's', 'z', 'E', 'x', 'k'},
//                {'a', 'c', 'c', 't', 'u', 'v', 'w', 'j'},
//                {'a', 'b', 'd', 'e', 'f', 'g', 'h', 'i'}
//        };

        List<String> text = getReader().readAsStringList(12);
        map = text.stream().map(s -> s.chars().toArray()).toArray(int[][]::new);
        H = map.length;
        W = map[0].length;
        inZone = p -> p.isInRect(Point.O, new Point(H, W));
    }

    @Override
    public Integer getSolution1() {
        return solve('S', 'E', 1);
    }

    @Override
    public Integer getSolution2() {
        return solve('E', 'a', -1);
    }

    public int solve(char startC, char endC, int dir) {
        //setup
        mem = new int[H][W];
        LinkedList<Point> que = new LinkedList<>();
        que.addLast(getPoint(startC));

        //iterate
        while (!que.isEmpty()) {
            Point curr = que.removeFirst();
            int currVal = distAt(curr);
            if (realHeightAt(curr) == endC) {
                return currVal;
            }
            curr.neighbours()
                    .filter(inZone)
                    .filter(this::notVisited)
                    .filter(p -> canGo(curr, p, dir))
                    .forEach(p -> {
                        saveDist(p, currVal + 1);
                        que.addLast(p);
                    });
        }
        show(); //should be unreachable
        return 0;
    }


    private Point getPoint(char lookup) {
        for (int j = 0; j < W; j++) {
            for (int i = 0; i < H; i++) {
                if (map[i][j] == lookup) {
                    return new Point(i, j);
                }
            }
        }
        return Point.O; //should be unreachable
    }

    private boolean canGo(Point from, Point to, int dir) {
        return dir * (heightAt(to) - heightAt(from)) < 2;
    }

    private int realHeightAt(Point p) {
        return map[p.x()][p.y()];
    }

    private int heightAt(Point p) {
        int real = realHeightAt(p);
        return real == 'S' ? 'a'
                : real == 'E' ? 'z'
                : real;
    }

    private int distAt(Point p) {
        return mem[p.x()][p.y()];
    }

    private boolean notVisited(Point p) {
        return distAt(p) == 0;
    }

    private void saveDist(Point p, int i) {
        mem[p.x()][p.y()] = i;
    }


    private void show() {
        for (int j = 0; j < H; j++) {
            for (int i = 0; i < W; i++) {
                System.out.print(mem[j][i] + ", ");
            }
            System.out.println();

        }

        System.out.println();
        System.out.println();
        System.out.println();
    }
}
