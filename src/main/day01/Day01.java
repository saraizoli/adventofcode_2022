package main.day01;

import main.utils.Day;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class Day01 extends Day<Integer> {
    private final List<Integer> elfCalories;

    public Day01() {
        this.elfCalories = getElfCalories();
    }

    private LinkedList<Integer> getElfCalories() {
        List<String> input = getReader().readAsStringList(1);
        LinkedList<Integer> sums = new LinkedList<>();
        int current = 0;
        for (String s : input) {
            if (s.isEmpty()) {
                sums.add(current);
                current = 0;
            } else {
                current += Integer.parseInt(s);
            }
        }
        return sums;
    }

    @Override
    public Integer getSolution1() {
        return Collections.max(elfCalories);
    }

    @Override
    public Integer getSolution2() {
        int top3Sum = elfCalories.stream().sorted(Comparator.reverseOrder()).limit(3).mapToInt(x -> x).sum();
        return top3Sum;
    }
}
