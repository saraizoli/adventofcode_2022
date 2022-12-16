package main.day15;

import main.utils.Day;
import main.utils.Point;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day15 extends Day<Long> {

    //    public static final int SECTION_Y = 10;
    public static final int SECTION_Y = 2000000;
    private final List<SensorBeacon> sbs;
    private final Set<Point> bs;

    public Day15() {
//        List<String> text = getReader().readAsStringList("day15_sample.txt");
        List<String> text = getReader().readAsStringList(15);

        this.sbs = text.stream()
                .map(s -> s.split("[=,:]"))
                .map(t -> new SensorBeacon(new Point(Integer.parseInt(t[1]), Integer.parseInt(t[3])), new Point(Integer.parseInt(t[5]), Integer.parseInt(t[7]))))
                .collect(Collectors.toList());
        this.bs = sbs.stream().map(SensorBeacon::b).collect(Collectors.toSet());
    }


    @Override
    public Long getSolution1() {
        List<int[]> intervals = getCoveredIntervals(SECTION_Y);
        return merge(intervals) - bs.stream().filter(sb -> sb.y() == SECTION_Y).count();
    }

    @Override
    public Long getSolution2() {
        return IntStream.range(0, SECTION_Y * 2).parallel()
                .mapToLong(i -> i + 4000000L * findGap(getCoveredIntervals(i)))
                .filter(l -> l > 0)
                .findAny().orElse(0);
    }

    private List<int[]> getCoveredIntervals(int sectionY) {
        List<int[]> intervals = sbs.stream()
                .filter(sb -> Math.abs(sb.s().y() - sectionY) <= sb.d())
                .map(sb -> new int[]{sb.s().x() - (sb.d() - Math.abs(sb.s().y() - sectionY)), sb.s().x() + (sb.d() - Math.abs(sb.s().y() - sectionY))})
                .sorted(Comparator.comparingInt(i -> i[0]))
                .toList();
        return intervals;
    }

    private int merge(List<int[]> intervals) {
        int currStart = intervals.get(0)[0];
        int currEnd = intervals.get(0)[1];
        int currSum = 0;
        for (int[] i : intervals) {
            if (i[0] > currEnd) {
                currSum += currEnd - currStart + 1;
                currStart = i[0];
            }
            if (i[1] > currEnd) {
                currEnd = i[1];
            }
        }
        currSum += currEnd - currStart + 1;
        return currSum;
    }

    private int findGap(List<int[]> intervals) {
        int currEnd = 0;
        for (int[] i : intervals) {
            if (i[0] > currEnd + 1) {
                return currEnd + 1;
            }
            if (i[1] > currEnd) {
                currEnd = i[1];
            }
        }
        return -1;
    }
}
