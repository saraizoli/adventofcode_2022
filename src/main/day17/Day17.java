package main.day17;

import main.utils.Day;
import main.utils.Point;

import java.util.*;
import java.util.stream.Collectors;

public class Day17 extends Day<Long> {

    //    public static final int SECTION_Y = 10;
    public final Point[] wind;
    public final int wPer;

    public Set<Point> blocked;

    public static final Set<Point>[] SHAPES = new Set[]{
            Set.of(new Point(2, 0), new Point(3, 0), new Point(4, 0), new Point(5, 0)),
            Set.of(new Point(3, 0), new Point(2, 1), new Point(3, 1), new Point(4, 1), new Point(3, 2)),
            Set.of(new Point(2, 0), new Point(3, 0), new Point(4, 0), new Point(4, 1), new Point(4, 2)),
            Set.of(new Point(2, 0), new Point(2, 1), new Point(2, 2), new Point(2, 3)),
            Set.of(new Point(2, 0), new Point(3, 0), new Point(2, 1), new Point(3, 1))
    };

    public Day17() {
//        List<String> text = List.of(">>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>");
        List<String> text = getReader().readAsStringList(17);

        this.wind = Arrays.stream(text.get(0).split("")).map(Point.DIRS::get).toArray(Point[]::new);
        this.wPer = wind.length;

    }

    @Override
    public Long getSolution1() {
        return solve(2022);
    }

    @Override
    public Long getSolution2() {
        return solve(1000000000000L);
    }

    private long solve(long blockLimit) {
        blocked = new HashSet<>();
        long stepCnt = 0;
        long blockCnt = 1;
        int maxH = -1;

        //for recognizing loops and cutting short the repeating part
        long periodBlockCnt = blockLimit < 10000 ? -1 : 0; //for small limits no need to optimize
        long periodHeight;
        long allSkippedHeight = 0;
        Map<List<Set<Point>>, long[]> cache = new HashMap<>();

        Set<Point> shape = move(SHAPES[0], new Point(0, 3));
        while (blockCnt <= blockLimit) {
            while (true) {
                Set<Point> shapeAfterWind = safeMove(shape, wind[(int) (stepCnt++ % wPer)]);
                Set<Point> shapeAfterFall = safeMove(shapeAfterWind, Point.D);
                shape = shapeAfterFall;

                //everything in this if is just to find the period, could be maybe cleaned up somehow
                if (periodBlockCnt == 0 && stepCnt % wPer == 0) { //period can only be a multiple of the wind input length
                    Point pushDown = new Point(0, -maxH);
                    List<Set<Point>> cacheKey = List.of(move(getTopRows(maxH - 20), pushDown), move(shape, pushDown));
                    if (cache.containsKey(cacheKey)) {
                        long[] oldCounters = cache.get(cacheKey);
                        periodBlockCnt = blockCnt - oldCounters[1];
                        periodHeight = maxH - oldCounters[2];
                        long periodRepeatCount = (blockLimit - blockCnt) / periodBlockCnt;
                        allSkippedHeight = periodRepeatCount * periodHeight;
                        blockLimit -= periodRepeatCount * periodBlockCnt;
                    }
                    cache.put(cacheKey, new long[]{stepCnt, blockCnt, maxH});
                }

                if (shapeAfterWind.equals(shapeAfterFall)) {
                    blocked.addAll(shapeAfterWind);
                    maxH = Math.max(maxH, getMaxH(shapeAfterWind));
                    break;
                }
            }

            //start new shape
            shape = move(SHAPES[(int) (blockCnt++ % 5)], new Point(0, maxH + 4));
//            show(blocked);
        }
        return maxH + allSkippedHeight + 1;
    }

    private void show(Set<Point> points) {
        int maxH = getMaxH(points);
        int minH = getMinH(points);
        for (int i = maxH; i >= minH; i--) {
            for (int j = 0; j < 7; j++) {
                System.out.print(points.contains(new Point(j, i)) ? "#" : ".");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();
    }

    private int getMaxH(Set<Point> points) {
        return points.stream().mapToInt(Point::y).max().orElse(0);
    }

    private int getMinH(Set<Point> points) {
        return points.stream().mapToInt(Point::y).min().orElse(0);
    }

    private Set<Point> getTopRows(int h) {
        return blocked.stream().filter(p -> p.y() > h).collect(Collectors.toSet());
    }

    private Set<Point> safeMove(Set<Point> shape, Point dir) {
        Set<Point> newShape = move(shape, dir);
        boolean canMove = newShape.stream().allMatch(p -> !blocked.contains(p) && p.x() >= 0 && p.x() < 7 && p.y() >= 0);
        return canMove ? newShape : shape;
    }

    private static Set<Point> move(Set<Point> shape, Point dir) {
        return shape.stream().map(dir::add).collect(Collectors.toSet());
    }
}
