package main.application;

import main.day09.Day09;
import main.utils.Day;

public class Main {
    public static void main(String[] args) {
        Day<?> day = new Day09();
        day.printSolution1WithTime();
        day.printSolution2WithTime();

        Day.printConstructionTime(Day09::new, 1000);
        day.printSolution1WithTime(1000);
        day.printSolution2WithTime(1000);
    }
}