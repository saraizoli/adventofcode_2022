package main.day06;

import main.utils.Day;

public class Day06 extends Day<Integer> {

    private final String text;

    public Day06() {
        this.text = getReader().readAsStringList(6).get(0);
    }

    @Override
    public Integer getSolution1() {
        return solve(4);
    }

    private Integer solve(int length) {
        int i = length;
        while (text.substring(i - length, i).chars().distinct().count() < length) {
            ++i;
        }
        return i;
    }

    @Override
    public Integer getSolution2() {
        return solve(14);
    }
}
