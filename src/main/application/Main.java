package main.application;

import main.day14.Day14;
import main.utils.Day;

public class Main {
    public static void main(String[] args) {
        Day<?> day = new Day14();
        day.printSolution1WithTime();
        day.printSolution2WithTime();

//        Day.printConstructionTime(Day14::new, 1000);
//        day.printSolution1WithTime(1000);
//        day.printSolution2WithTime(1000);
    }
}