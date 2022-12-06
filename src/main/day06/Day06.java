package main.day06;

import main.utils.Day;

public class Day06 extends Day {

    private final String text;

    public Day06() {
        this.text = getReader().readAsStringList(6).get(0);
    }

    @Override
    public void getSolution1() {
        solve(4);
    }

    private void solve(int length) {
        int i = length;
        while (text.substring(i - length, i).chars().distinct().count() < length) {
            ++i;
        }
        System.out.println(i);
    }

    @Override
    public void getSolution2() {
        solve(14);
    }
}
