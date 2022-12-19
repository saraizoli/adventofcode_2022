package main.day18;

import main.utils.Day;
import main.utils.Point3;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Day18 extends Day<Long> {

    private final Set<Point3> cubes;

    public Day18() {
//        List<String> text = List.of("2,2,2", "1,2,2", "3,2,2", "2,1,2", "2,3,2", "2,2,1", "2,2,3", "2,2,4", "2,2,6", "1,2,5", "3,2,5", "2,1,5", "2,3,5");
        List<String> text = getReader().readAsStringList(18);

        cubes = text.stream().map(Point3::from).collect(Collectors.toSet());
    }

    @Override
    public Long getSolution1() {
        return cubes.stream()
                .flatMap(Point3::neighbours)
                .filter(s -> !cubes.contains(s))
                .count();
    }

    @Override
    public Long getSolution2() {
        //will do BFS for (0,0,0) (20,20,20) cube, could be determined programmatically
        LinkedList<Point3> que = new LinkedList<>();
        Set<Point3> outside = new HashSet<>();
        outside.add(Point3.O);
        que.addLast(Point3.O);
        Point3 center = new Point3(10, 10, 10);

        //iterate
        long[] cnt = {0}; //meh, need const ref to increment in stream
        while (!que.isEmpty()) {
            Point3 curr = que.removeFirst();
            curr.neighbours()
                    .filter(p -> !outside.contains(p))
                    .filter(p -> p.dist0(center) <= 11)
                    .forEach(p -> {
                        if (cubes.contains(p)) {
                            cnt[0]++;
                        } else {
                            outside.add(p);
                            que.addLast(p);
                        }
                    });
        }
        return cnt[0];
    }
}
