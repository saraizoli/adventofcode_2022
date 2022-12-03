package main.day02;

import main.utils.Day;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Day02 extends Day {

    private final List<String> strat;
    static final Map<String, Integer> scoring1 = Map.of(
            "A X", 3 + 1,
            "A Y", 6 + 2,
            "A Z", 0 + 3,
            "B X", 0 + 1,
            "B Y", 3 + 2,
            "B Z", 6 + 3,
            "C X", 6 + 1,
            "C Y", 0 + 2,
            "C Z", 3 + 3
    );

    static final Map<String, Integer> scoring2 = Map.of(
            "A X", 0 + 3,
            "A Y", 3 + 1,
            "A Z", 6 + 2,
            "B X", 0 + 1,
            "B Y", 3 + 2,
            "B Z", 6 + 3,
            "C X", 0 + 2,
            "C Y", 3 + 3,
            "C Z", 6 + 1
    );

    public Day02() {
        this.strat =  //Arrays.asList("A Y", "B X", "C Z");
                getReader().readAsStringList(2);
    }

    @Override
    public void getSolution1() {
        int sum = strat.stream().mapToInt(scoring1::get).sum();
        System.out.println(sum);
    }

    @Override
    public void getSolution2() {
        int sum = strat.stream().mapToInt(scoring2::get).sum();
        System.out.println(sum);
    }
}
