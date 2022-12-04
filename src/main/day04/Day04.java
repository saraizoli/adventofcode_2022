package main.day04;

import main.utils.Day;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day04 extends Day {

    private final List<List<Integer>> ranges;

    public Day04() {
        List<String> rows =
//                List.of(
//                        "2-4,6-8",
//                        "2-3,4-5",
//                        "5-7,7-9",
//                        "2-8,3-7",
//                        "6-6,4-6",
//                        "2-6,4-8");
                getReader().readAsStringList(4);
        this.ranges = rows.stream()
                .map(s -> s.split("[-,]"))
                .map(arr -> Stream.of(arr).map(Integer::valueOf).collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    @Override
    public void getSolution1() {
        long included = ranges.stream().filter(r -> (r.get(2) - r.get(0)) * (r.get(3) - r.get(1)) <= 0).count();
        System.out.println(included);
    }

    @Override
    public void getSolution2() {
        long overlap = ranges.stream().filter(r -> (r.get(3) - r.get(0)) * (r.get(1) - r.get(2)) >= 0).count();
        System.out.println(overlap);
    }
}
