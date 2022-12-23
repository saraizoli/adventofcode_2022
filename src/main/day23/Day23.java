package main.day23;

import main.utils.Day;
import main.utils.Point;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day23 extends Day<Integer> {

    private final Set<Point> start;
    private Set<Point> elves;
    private LinkedList<Point> dirPref;

    private final static List<Point> DIRS = List.of(Point.U, Point.D, Point.L, Point.R);

    public Day23() {
        List<String> text = getReader().readAsStringList(23);
//        List<String> text = List.of(
//                "....#..",
//                "..###.#",
//                "#...#.#",
//                ".#...##",
//                "#.###..",
//                "##.#.##",
//                ".#..#.."
//        );
        int len = text.get(0).length();

        start = IntStream.range(0, text.size())
                .mapToObj(l ->
                        IntStream.range(0, len)
                                .filter(c -> text.get(l).charAt(c) == '#')
                                .mapToObj(c -> new Point(c, -1 * l)))
                .flatMap(x -> x)
                .collect(Collectors.toSet());
    }

    @Override
    public Integer getSolution1() {
        return solve(10)[0];
    }

    @Override
    public Integer getSolution2() {
        return solve(Integer.MAX_VALUE)[1];
    }

    private int[] solve(int maxSteps) {
        //        show(start);
        elves = new HashSet<>(start);
        dirPref = new LinkedList<>(DIRS);
        int i = 1;
        while (i <= maxSteps) {
            Map<Point, List<Point>> considered = getConsideredMapping();
            Set<Point> next = doStep(considered);
            rotatePrefs();
            if (next.equals(elves)) {
                return new int[]{score(elves), i};
            }
            elves = next;
//            show(elves);
            i++;
        }
        return new int[]{score(elves), i};
    }

    private int score(Set<Point> elves) {
        int xMax = elves.stream().mapToInt(Point::x).max().orElse(0);
        int xMin = elves.stream().mapToInt(Point::x).min().orElse(0);
        int yMax = elves.stream().mapToInt(Point::y).max().orElse(0);
        int yMin = elves.stream().mapToInt(Point::y).min().orElse(0);

        return (xMax - xMin + 1) * (yMax - yMin + 1) - elves.size();
    }

    private Map<Point, List<Point>> getConsideredMapping() {
        Map<Point, List<Point>> considered = elves.parallelStream().map(p -> {
            List<Point> options = dirPref.stream().filter(d -> goodDir(p, d)).map(p::add).toList();
            Point next = options.size() == 0 || options.size() == 4 ? p : options.get(0);
            return new Point[]{next, p};
        }).collect(Collectors.groupingBy(p -> p[0], Collectors.mapping(p -> p[1], Collectors.toList())));
        return considered;
    }

    private boolean goodDir(Point p, Point d) {
        Point pd = p.add(d);
        return Stream.of(pd, pd.add(d.rotate(-1)), pd.add(d.rotate(1))).noneMatch(pn -> elves.contains(pn));
    }

    private Set<Point> doStep(Map<Point, List<Point>> considered) {
        Set<Point> next = considered.entrySet().stream()
                .flatMap(e -> e.getValue().size() == 1 ? Stream.of(e.getKey()) : e.getValue().stream())
                .collect(Collectors.toSet());
        return next;
    }

    private void rotatePrefs() {
        Point fdir = dirPref.removeFirst();
        dirPref.addLast(fdir);
    }


    private void show(Set<Point> elves) {
        int xMax = elves.stream().mapToInt(Point::x).max().orElse(0);
        int xMin = elves.stream().mapToInt(Point::x).min().orElse(0);
        int yMax = elves.stream().mapToInt(Point::y).max().orElse(0);
        int yMin = elves.stream().mapToInt(Point::y).min().orElse(0);

        for (int j = yMax; j >= yMin; j--) {
            for (int i = xMin; i <= xMax; i++) {
                System.out.print(elves.contains(new Point(i, j)) ? "#" : ".");
            }
            System.out.println();
        }

        System.out.println();
        System.out.println();
    }
}
