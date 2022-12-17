package main.application;

import main.day16.Day16;
import main.utils.Day;

public class Main {
    public static void main(String[] args) {
        Day<?> day = new Day16();
        day.printSolution1WithTime();
        day.printSolution2WithTime();

//        Day.printConstructionTime(Day15::new, 1000);
//        day.printSolution1WithTime(100);
//        day.printSolution2WithTime(100);
    }
}