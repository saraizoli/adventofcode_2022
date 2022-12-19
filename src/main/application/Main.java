package main.application;

import main.day18.Day18;
import main.utils.Day;

public class Main {
    public static void main(String[] args) {
        Day<?> day = new Day18();
        day.printSolution1WithTime();
        day.printSolution2WithTime();

        Day.printConstructionTime(Day18::new, 1000);
        day.printSolution1WithTime(1000);
        day.printSolution2WithTime(1000);
    }
}